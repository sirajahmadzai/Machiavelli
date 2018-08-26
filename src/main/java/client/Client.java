package client;

import client.views.GameView;
import commands.ServerCommands;
import javafx.application.Platform;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Task<Void>/* implements Runnable*/ {

    /*******************************************************************
     **************************PRIVATES********************************
     ******************************************************************/
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private App app;

    /**
     * CONSTRUCTOR
     *
     * @param app
     * @param ip
     * @param port
     * @throws IOException
     */
    public Client(App app, String ip, int port) throws IOException {
        this.app = app;

        if (ip.equals("") && port == 0) {
            socket = new Socket("localhost", 9876);
        } else {
            socket = new Socket(ip, port);
        }
        in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        app.in = in;
        out = new PrintWriter(socket.getOutputStream(), true);
        app.out = out;

    }


    /**
     * @return
     * @throws Exception
     */
    @Override
    protected Void call() throws Exception {
        // Process all messages from server, according to the protocol.
        String response;
        while ((response = in.readLine()) != null) {
            Scanner scanner = new Scanner(response);
            String cmd = scanner.next();
            if (cmd.equals(ServerCommands.SHOW_LOGIN_VIEW.toString())) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        app.showLoginView();
                    }
                });

            } else if (cmd.equals(ServerCommands.WAIT_FOR_SETUP.toString())) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        app.showWaitingView();
                    }
                });


            } else if (cmd.equals(ServerCommands.SHOW_GAMEVIEW.toString())) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        //javaFX doesn't let you rerun a view that is already running
                        if (app.getActiveView() instanceof GameView) {
                            //do nothing
                        } else {
                            int numOfPlayers = scanner.nextInt();
                            app.showGameView(numOfPlayers);

                        }

                    }
                });
            } else if (cmd.equals(ServerCommands.SET_MESSAGE_BOX.toString())) {
                String message = scanner.nextLine();
//                UICon.setMessageBoxText(message);
            } else if (cmd.equals(ServerCommands.REMOVE_CARD_FROM_HAND.toString())) {
                int playerId = scanner.nextInt();
                int cardId = scanner.nextInt();
//                UICon.removeSelectedCardFromPlayer(playerId, cardId);
            } else if (cmd.equals(ServerCommands.ADD_CARD_TO_HAND.toString())) {
                int playerId = scanner.nextInt();
                int currId = scanner.nextInt();
                int cardId = scanner.nextInt();
                String url = scanner.nextLine();
//                UICon.addCardToPlayer(playerId, currId, cardId, url);
            } else if (cmd.equals(ServerCommands.FILL_DECK.toString())) {
//                UICon.fillDecks();
            }
        }
        return null;
    }

}