package client;

import client.views.GameView;
import client.views.View;
import client.views.WaitingForOtherPlayersView;
import com.sun.javaws.exceptions.InvalidArgumentException;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import server.Server;
import server.models.cards.Card;
import tests.testController.GameViewControllerTest;

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
//        root.getChildren().clear();
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

//        startGame();
    }

    public void startGame(int playerCount) {
//        new GameViewControllerTest(this.app, GameView.getInstance());
        GameView.getInstance().setPlayerCount(playerCount);
        showView(GameView.getInstance());

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

    public void dealHand(String playerId, Stack<Object> cards) {

        for(Object cardText : cards){
            try {
                Card card = Card.fromString(cardText.toString());
                GameView.getInstance().addCardToHand(playerId,card,null);
                GameView.getInstance().dealHands();

            } catch (InvalidArgumentException e) {
                e.printStackTrace();
            }
        }
    }
}
