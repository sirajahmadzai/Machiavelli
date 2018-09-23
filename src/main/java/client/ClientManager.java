package client;

import client.views.GameView;
import client.views.View;
import client.views.components.CardSetView;
import client.views.components.CardView;
import com.sun.javaws.exceptions.InvalidArgumentException;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import server.Server;
import server.models.cards.Card;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Stack;

public class ClientManager {
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
    private CardView selectedCard;

    public static ClientManager getInstance() {
        return ourInstance;
    }

    private ClientManager() {
    }

    public void startServer(int port, int numberOfPlayers) throws Exception {
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
            loginServer(port);

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

        clientThread.start();
        joinedGame = true;
    }

    public void startGame(int numberOfPlayers) {
//        new GameViewControllerTest(this.app, GameView.getInstance());
        GameView.getInstance().setPlayerCount(numberOfPlayers);
        showView(GameView.getInstance());
        GameView.getInstance().fillDeck();

        //TODO: Check if the table is full.
//        pushView(WaitingForOtherPlayersView.getInstance());
    }

    public void loginServer(int port) {
        try {
            loginServer(new Client(this, port));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loginServer(String ip, int port) {
        try {
            loginServer(new Client(this, ip, port));
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

    public void dealHand(int seatNumber, Stack<Object> cards) {
        for (Object cardText : cards) {
            try {
                Card card = Card.fromString(cardText.toString());
                GameView.getInstance().addCardToHand(seatNumber, card, null);
                GameView.getInstance().dealHands();

            } catch (InvalidArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    public void introducePlayer(String playerName, int playerId, int seatNumber, boolean owner) {
        if (owner) {
            GameView.getInstance().setOwnerPlayer(playerId, seatNumber);
        }
        GameView.getInstance().fillSeat(playerName, playerId, seatNumber);
    }

    //    When user clicks a card target, we move the card from old set to the target set.
    public void droppedToTarget(CardSetView targetSet) {
        if (selectedCard != null) {
            Card s = (Card) this.selectedCard.getCard();
            targetSet.addCard(s);

            this.selectedCard.setSelected(false);
            this.selectedCard.removeFromParentSet();
            selectedCard = null;

            GameView.getInstance().setPlayAreaActive(false);
        }
    }

    //    Keep track of any card selected inside the views.
    //    Activate the play area so that the selected card can be moved to it.
    public void cardSelected(CardView selectedCard) {
        // Clear previous selection.
        if (this.selectedCard != null) {
            this.selectedCard.getParentSet().clearSelectedCards();
        }

        // Deselect a card
        if (selectedCard == this.selectedCard) {
            this.selectedCard = null;

        } else if (selectedCard != null) {

            this.selectedCard = selectedCard;
            selectedCard.setSelected(true);
            // TODO: this line smells!
            selectedCard.getParentSet().setSelectedCard(selectedCard);
        }

        GameView.getInstance().setPlayAreaActive(this.selectedCard != null);
    }
}
