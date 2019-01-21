package client;

import client.views.GameView;
import client.views.View;
import client.views.components.CardSetView;
import client.views.components.CardView;
import commands.Command;
import commands.server.PassTurn;
import commands.server.PlayerMove;
import commands.server.WinnerCommand;
import interfaces.clientManagerInterface;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import server.Server;
import server.models.CardSet;
import server.models.cards.Card;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static utils.constants.PORT_ERROR_MESSAGE;

public class ClientManager implements clientManagerInterface {
    /**
     * PRIVATE STATIC FINALS
     */
    private static final int MINIMUM_SET_SIZE = 3;

    /**
     * PRIVATE FINALS
     */
    private final GameView gameView;

    /**
     * PRIVATE STATICS
     */
    private static ClientManager ourInstance = new ClientManager();
    /**
     * CLASS VARIABLES
     */
    Server server;
    Thread serverThread;
    boolean serverStarted = false;
    boolean joinedGame = false;

    /**
     * PRIVATES
     */
    private Thread clientThread;
    private Stage stage;
    private StackPane root;
    private App app;
    //  private CardSet selectedCards;
    private SelectionManager selectionManager = new SelectionManager();
    private Client client;
    private int currentTurn;
    private CardView lastCardDrawn;

    /**
     * PUBLICS
     */
    public BufferedReader in;
    public PrintWriter out;

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
    }

    /**
     * @param port
     * @param numberOfPlayers
     * @param adminName
     * @throws Exception
     */
    public void startServer(int port, int numberOfPlayers, String adminName) throws Exception {
        if (serverStarted) {
            throw new UnsupportedOperationException(PORT_ERROR_MESSAGE + server.getPort());
        }
        try {
            //This is the admin of the game. The admin starts the server and waits for other players to join.
            server = new Server(port, numberOfPlayers);
            serverThread = new Thread(server);
            serverThread.setName("Server thread");
            serverThread.start();
            serverStarted = true;

            //Admin joins the game first.
            loginServer(port, adminName);

        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * gets the app
     *
     * @return
     */
    public App getApp() {
        return app;
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
        this.stage = stage;
        this.root = (StackPane) stage.getScene().getRoot();
    }

    /**
     * @param view
     */
    public synchronized void showView(View view) {
        root.getChildren().clear();
        root.getChildren().add(view.getRoot());
    }

    /**
     * @param view
     */
    public synchronized void pushView(View view) {
        root.getChildren().add(view.getRoot());
        view.setMainApp(this.app);
    }

    /**
     * @param client
     */
    private void loginServer(Client client) {
        clientThread = new Thread(client);
        this.client = client;
        clientThread.start();
        joinedGame = true;

        //gameView.fillDeck();

    }

    /**
     * @param numberOfPlayers
     */
    public void startGame(int numberOfPlayers) {
//        new GameViewControllerTest(this.app, gameView);
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

    /**
     * @param playerName
     * @param playerId
     * @param seatNumber
     * @param owner
     */
    public void removePlayer(String playerName, int playerId, int seatNumber, boolean owner) {
        if (owner) {
            gameView.setOwnerPlayer(playerId, seatNumber);
        }
        gameView.removeSeat(playerName, playerId, seatNumber);


    }

    /**
     * When user clicks a card target, we move the card from old set to the target set.
     *
     * @param targetSet
     */
    public void droppedToTarget(CardSetView targetSet) {
        if (!isOwnerTurn()) {
            return;
        }
        gameView.takeSnapshot();

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
        return currentTurn == GameView.getInstance().getOwnerSeat();
    }

    private void startTurn() {
        gameView.takeSnapshot();
    }

    public void resetMove() {

    }

    public boolean endTurn(MouseEvent event) {


        if (!isOwnerTurn()) {
            return false;
        }
        lastCardDrawn = null;
        selectionManager.deselectAll();

        CardSet prevHand = gameView.getHand().getSnapshot();
        CardSet lastHand = gameView.getHand().getCardSet();

//      No card played. Just pass the turn.
        if (prevHand.equals(lastHand)) {
            /*My Code */
            gameView.init_snapstate();
            /*End */
            client.sendCommandToServer(new PassTurn());
            return true;
        }

//      Can't make the move, invalid sets on table.
        if (!gameView.getPlayArea().isValid(MINIMUM_SET_SIZE)) {
            gameView.setMessage("Not a valid play!");

            return true;
        }

        // User is the winner
        if (lastHand.totalCount() <= 0) {
            gameView.setMessage("Hurray!!! You are the winner.");
            WinnerCommand win = new WinnerCommand(Command.CommandNames.SET_WINNER);
            client.sendCommandToServer(win);
            return false;
        }

        List<CardSet> table = gameView.getPlayArea().takeSnapshot();

        CardSet playedCards = prevHand.diff(lastHand);
        PlayerMove move = new PlayerMove(gameView.getOwnerSeat(), playedCards, table);


        client.sendCommandToServer(move);

        return true;
    }

    public void setWinner() {
        gameView.setMessage("You've lost the game, better luck next time :)");
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
//        List<CardSet> cardsOnTheTable = gameView.getPlayArea().getSnapshot();
        gameView.getPlayArea().setAllSets(move.getTable());
        gameView.removeCardsFrom(move.getSeatNumber(), move.getPlayedCards());
        /*  My Code 2019-01-20-12-22*   At turn starting recorde the table */
        gameView.init_snapstate();
        gameView.takeSnapshot();
        /*                */
        startTurn();

//        Remove cards from hand!
    }
}
