package client;

import client.views.GameView;
import client.views.View;
import client.views.components.CardSetView;
import client.views.components.CardView;
import commands.BasicCommand;
import commands.Command;
import commands.server.PlayerMove;
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

public class ClientManager {
    private static final int MINIMUM_SET_SIZE = 3;
    private final GameView gameView;

    Server server;
    Thread serverThread;
    boolean serverStarted = false;
    boolean joinedGame = false;

    private static ClientManager ourInstance = new ClientManager();
    private Thread clientThread;
    private Stage stage;
    private StackPane root;
    public BufferedReader in;
    public PrintWriter out;
    private App app;
//    private CardSet selectedCards;
    private SelectionManager selectionManager = new SelectionManager();
    private Client client;
    private int currentTurn;

    public static ClientManager getInstance() {
        return ourInstance;
    }

    private ClientManager() {

        this.gameView = GameView.getInstance();
    }

    public void startServer(int port, int numberOfPlayers, String adminName) throws Exception {
        if (serverStarted) {
            throw new UnsupportedOperationException("A game server is already started at port " + server.getPort());
        }
        try {
//          This is the admin of the game. The admin starts the server and waits for other players to join.
            server = new Server(port, numberOfPlayers);
            serverThread = new Thread(server);
            serverThread.setName("Server thread");
            serverThread.start();
            serverStarted = true;

//            Admin joins the game first.
            loginServer(port, adminName);

        } catch (Exception e) {
            throw e;
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        this.root = (StackPane) stage.getScene().getRoot();
    }

    public synchronized void showView(View view) {
        root.getChildren().clear();
        root.getChildren().add(view.getRoot());
    }

    public synchronized void pushView(View view) {
        root.getChildren().add(view.getRoot());
        view.setMainApp(this.app);
    }

    private void loginServer(Client client) {
        clientThread = new Thread(client);
        this.client = client;
        clientThread.start();
        joinedGame = true;
    }

    public void startGame(int numberOfPlayers) {
//        new GameViewControllerTest(this.app, gameView);
        gameView.setPlayerCount(numberOfPlayers);
        showView(gameView);
        gameView.fillDeck();

        //TODO: Check if the table is full.
//        pushView(WaitingForOtherPlayersView.getInstance());
    }

    private void loginServer(int port, String name) {
        try {
            loginServer(new Client(this, port, name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loginServer(String ip, int port, String name) {
        try {
            loginServer(new Client(this, ip, port, name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setApp(App app) {
        this.app = app;
    }

    public App getApp() {
        return app;
    }

    public void dealHand(int seatNumber, CardSet hand) {
        for (Card card : hand.getCards()) {
            // 1 open card to the owner
            gameView.addCardToHand(seatNumber, card);

            // 1 hidden card to each opponent.
            gameView.dealHands();
        }
        startTurn();
    }

    public void introducePlayer(String playerName, int playerId, int seatNumber, boolean owner) {
        if (owner) {
            gameView.setOwnerPlayer(playerId, seatNumber);
        }
        gameView.fillSeat(playerName, playerId, seatNumber);
    }

    //    When user clicks a card target, we move the card from old set to the target set.
    public void droppedToTarget(CardSetView targetSet) {
        if (!isOwnerTurn()) {
            return;
        }

        if(!selectionManager.isEmpty())
        {
            for (CardView cardView : selectionManager.getSelectedCards()) {
                targetSet.addCard(cardView.getCard());
                cardView.removeFromParentSet();
                gameView.setPlayAreaActive(false);
            }
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
//        TODO: Replace this snapshot.
    }

    public boolean endTurn(MouseEvent event) {
        if (!isOwnerTurn()) {
            return false;
        }
        selectionManager.deselectAll();

        CardSet prevHand = gameView.getHand().getSnapshot();
        CardSet lastHand = gameView.getHand().getCardSet();

//      No card played. Just pass the turn.
        if (prevHand.equals(lastHand)) {
            client.sendCommandToServer(new BasicCommand(Command.CommandNames.PASS_TURN));
            return true;
        }

//      Can't make the move, invalid sets on table.
        if (!gameView.getPlayArea().isValid(MINIMUM_SET_SIZE)) {
            gameView.setMessage("Not a valid play!");
            return false;
        }

        List<CardSet> table = gameView.getPlayArea().takeSnapshot();

        CardSet playedCards = prevHand.diff(lastHand);
        PlayerMove move = new PlayerMove(gameView.getOwnerSeat(), playedCards, table);

        client.sendCommandToServer(move);

        return true;
    }

    public void switchTurn(int seatNumber) {
        startTurn();
        currentTurn = seatNumber;
        gameView.switchTurn(seatNumber);
    }

    public void drawCard(int seatNumber, Card card) {
        gameView.addCardToHand(seatNumber, card);
        startTurn();
    }

    public void playMove(PlayerMove move) {
//        List<CardSet> cardsOnTheTable = gameView.getPlayArea().getSnapshot();
        gameView.getPlayArea().setAllSets(move.getTable());
        gameView.removeCardsFrom(move.getSeatNumber(),move.getPlayedCards());
        startTurn();

//        Remove cards from hand!
    }
}
