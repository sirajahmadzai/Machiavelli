package client;

import client.views.GameView;
import client.views.StartOptionsView;
import client.views.View;
import client.views.components.CardSetView;
import client.views.components.CardView;
import commands.client.ClientMessage;
import commands.server.PassTurn;
import commands.server.PlayerMove;
import interfaces.clientManagerInterface;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import server.Server;
import server.models.CardSet;
import server.models.cards.Card;
import utils.constants;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CyclicBarrier;

public class ClientManager implements clientManagerInterface {
    public void serverMessage(ClientMessage.MessageTypes messageType, String messageText) {
        gameView.setMessage(messageText);
    }

    enum GameState {
        JOIN_GAME,
        WAITING_FOR_PLAYERS,
        PLAYING, GAME_FINISHED,
    }

    /**
     * PRIVATE FINALS
     */
    private final GameView gameView;
    private final StartOptionsView startOptionsView;


    /**
     * PRIVATE STATICS
     */
    private static ClientManager ourInstance = new ClientManager();
    /**
     * CLASS VARIABLES
     */
    private Server server;
    private boolean serverStarted = false;

    private StackPane root;
    private App app;
    private SelectionManager selectionManager = new SelectionManager();
    private Client client;
    private int currentTurn;
    private CardView lastCardDrawn;
    private GameState gameState = GameState.JOIN_GAME;

    /**
     * PUBLICS
     */

    /**
     * gets the instance
     *
     * @return
     */
    public static ClientManager getInstance() {
        return ourInstance;
    }

    /**
     * CONSTRUCTOR
     */
    private ClientManager() {
        this.gameView = GameView.getInstance();
        this.startOptionsView = StartOptionsView.getInstance();
    }

    /**
     * @param port
     * @param numberOfPlayers
     * @param adminName
     * @throws Exception
     */
    public void startServer(int port, int numberOfPlayers, String adminName) throws Exception {
        if (serverStarted) {
            throw new UnsupportedOperationException(constants.PORT_ERROR_MESSAGE + server.getPort());
        }
        try {
            //
            CyclicBarrier barrier = new CyclicBarrier(2);
            //This is the admin of the game. The admin starts the server and waits for other players to join.
            server = new Server(port, numberOfPlayers,barrier);

            Thread serverThread = new Thread(server);
            serverThread.setName("Server thread");
            serverThread.start();
            serverStarted = true;

            barrier.await();

            //Admin joins the game first.
            loginServer(port, adminName);

        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * sets the app
     *
     * @param app
     */
    public void setApp(App app) {
        this.app = app;
    }

    /**
     * sets the stage
     *
     * @param stage
     */
    public void setStage(Stage stage) {
        this.root = (StackPane) stage.getScene().getRoot();

        stage.setOnHidden(event -> {
            Platform.exit();
            System.exit(0);
        });

        stage.setOnCloseRequest(event -> {
            if (gameState != GameState.JOIN_GAME) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Exit game?");
                alert.setHeaderText("You are leaving the game.");
                alert.setContentText("Are sure you want to do this?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() != ButtonType.OK) {
                    event.consume();
                }
            }
        });
    }

    /**
     * @param view
     */
    public synchronized void showView(View view) {
        root.getChildren().clear();
        root.getChildren().add(view.getRoot());
    }

    /**
     * @param client
     */
    private void loginServer(Client client) {
        Thread clientThread = new Thread(client);
        this.client = client;
        clientThread.start();
    }

    /**
     * @param numberOfPlayers
     */
    public void joinTable(int numberOfPlayers) {
        this.gameState = GameState.WAITING_FOR_PLAYERS;
        gameView.setPlayerCount(numberOfPlayers);
        showView(gameView);
    }

    /**
     * @param port
     * @param name
     */
    public void loginServer(int port, String name) {
        try {
            loginServer(new Client(this, port, name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param ip
     * @param port
     * @param name
     */
    public void loginServer(String ip, int port, String name) {
        try {
            loginServer(new Client(this, ip, port, name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param seatNumber
     * @param hand
     */
    public void dealHand(int seatNumber, CardSet hand) {
        for (Card card : hand.getCards()) {
            // 1 open card to the owner
            gameView.addCardToHand(seatNumber, card);

            // 1 hidden card to each opponent.
            gameView.dealHands();
        }
        this.gameState = GameState.PLAYING;
        startTurn();
    }

    /**
     * @param playerName
     * @param playerId
     * @param seatNumber
     * @param owner
     */
    public void introducePlayer(String playerName, int playerId, int seatNumber, boolean owner) {

        if (owner) {
            gameView.setOwnerPlayer(playerId, seatNumber);
        } else {

        }
        gameView.fillSeat(playerName, playerId, seatNumber);


    }

    public void connectionLost() {
        removePlayer(gameView.getOwnerSeat());
    }

    /**
     * @param seatNumber
     */
    public void removePlayer(int seatNumber) {
        if (seatNumber == gameView.getOwnerSeat()) {
            showView(startOptionsView);
            startOptionsView.setMessageText("You've disconnected from the game server.");
        }
        gameView.setMessage("One of the players is disconnected. Waiting for someone to join and game will restart!");
        gameView.removePlayer(seatNumber);
        resetGame();
    }

    private void resetGame() {
        gameView.resetView();
        selectionManager.deselectAll();
        selectionManager.clearSelections();
        lastCardDrawn = null;
        gameState = GameState.WAITING_FOR_PLAYERS;
    }


    /**
     * When user clicks a card target, we move the card from old set to the target set.
     *
     * @param targetSet
     */
    public void moveSelectedCards(CardSetView targetSet) {
        if (!isOwnerTurn()) {
            return;
        }

        if (!selectionManager.isEmpty()) {
            // Move all selected cards to the new set.
            for (CardView cardView : selectionManager.getSelectedCards()) {
                targetSet.addCard(cardView.getCard());
                cardView.removeFromParentSet();
                gameView.setPlayAreaActive(false);
            }
            // Clear selections.
            selectionManager.deselectAll();
        }

        gameView.takeSnapshot();
        gameView.clearMessage();
    }

    //    Keep track of any card selected inside the views.
    //    Activate the play area so that the selected card can be moved to it.
    public void cardSelected(CardView selectedCard) {
        if (!isOwnerTurn()) {
            return;
        }
        selectionManager.addCard(selectedCard);

        if (!selectionManager.isEmpty()) {
            gameView.setPlayAreaActive(selectionManager.getSelectedCardsSet());
        } else {
            gameView.setPlayAreaActive(false);
        }
    }

    private boolean isOwnerTurn() {
        return currentTurn == gameView.getOwnerSeat();
    }

    private void startTurn() {
        gameView.init_snapstate();
        gameView.takeSnapshot();
    }

    public boolean endTurn(MouseEvent event) {
        if (!isOwnerTurn()) {
            return false;
        }

        lastCardDrawn = null;
        selectionManager.deselectAll();

        CardSet prevHand = gameView.getHand().getFirstSnapshot();
        CardSet lastHand = gameView.getHand().getLastSnapshot();

//      No card played. Just pass the turn.
        if (prevHand == null || prevHand.equals(lastHand)) {
            client.sendCommandToServer(new PassTurn());
            return true;
        }

//      Can't make the move, invalid sets on table.
        if (!gameView.getPlayArea().isValid(constants.MINIMUM_SET_SIZE)) {
            gameView.setMessage("Not a valid play!");

            return false;
        }
        List<CardSet> table = gameView.getPlayArea().getLastSnapshot();

        CardSet playedCards = prevHand.diff(lastHand);
        PlayerMove move = new PlayerMove(gameView.getOwnerSeat(), playedCards, table);

        client.sendCommandToServer(move);

        return true;
    }

    public void setWinner(int winnerSeatNumber) {
        if (gameView.getOwnerSeat() == winnerSeatNumber) {
            gameView.setMessage("Hurray!!! You are the winner.");
        } else {
            gameView.setMessage("You've lost the game, better luck next time :)");
        }
        gameState = GameState.GAME_FINISHED;
    }

    public void switchTurn(int seatNumber) {
        startTurn();
        currentTurn = seatNumber;
        gameView.switchTurn(seatNumber);

        if (lastCardDrawn != null) {
            lastCardDrawn.setNewcomer(true);
        }
    }

    public void drawCard(int seatNumber, Card card) {
        CardView drawnCard = gameView.addCardToHand(seatNumber, card);
        if (!drawnCard.getCard().isHidden()) {
            lastCardDrawn = drawnCard;
        }
        startTurn();
    }

    public void playMove(PlayerMove move) {
        gameView.getPlayArea().setAllSets(move.getTable());
        gameView.removeCardsFrom(move.getSeatNumber(), move.getPlayedCards());
        startTurn();
    }


}
