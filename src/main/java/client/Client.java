package client;

import client.views.GameView;
import commands.Command;
import commands.CommandFactory;
import commands.ServerCommands;
import javafx.application.Platform;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.Stack;

public class Client extends Task<Void>/* implements Runnable*/ {

    /*******************************************************************
     **************************PRIVATES********************************
     ******************************************************************/
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private ClientManager manager;

    /**
     * CONSTRUCTOR
     *
     * @param manager
     * @param ip
     * @param port
     * @throws IOException
     */
    public Client(ClientManager manager, String ip, int port) throws IOException {
        this.manager = manager;

        if (ip.equals("") && port == 0) {
            socket = new Socket("localhost", 9876);
        } else {
            socket = new Socket(ip, port);
        }
        in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        manager.in = in;
        out = new PrintWriter(socket.getOutputStream(), true);
        manager.out = out;

    }

    public Client(ClientManager manager, int port) throws IOException {
        this(manager, "127.0.0.1", port);
    }


    /**
     * @return
     * @throws Exception
     */
    @Override
    protected Void call() throws Exception {
        // Process all messages from server, according to the protocol.
        try {
            String response;
            while ((response = in.readLine()) != null) {
                Command command = CommandFactory.buildCommand(response);
                System.out.println("Client received command: " + command);
                switch (command.getName()) {
                    case SHOW_GAMEVIEW:
                        Platform.runLater(() -> manager.startGame(Integer.parseInt(command.getParameter())));
                        break;
                    case DEAL_HANDS:
                        Stack<Object> params = command.getParameters();
                        Platform.runLater(() -> manager.dealHand(Integer.parseInt(params.pop().toString()), params));
                        break;
                    default:
                        command.execute();
                }

                System.out.println("Command processed " + command);

//            Scanner scanner = new Scanner(response);
//            String cmd = scanner.next();
//            System.out.println("Client received command: " + cmd);
//            if (cmd.equals(ServerCommands.SHOW_LOGIN_VIEW.toString())) {
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
////                        manager.showLoginView();
//                    }
//                });
//
//            } else if (cmd.equals(ServerCommands.WAIT_FOR_SETUP.toString())) {
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
////                        manager.showWaitingView();
//                    }
//                });
//
//
//            } else if (cmd.equals(ServerCommands.SHOW_GAMEVIEW.toString())) {
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        //javaFX doesn't let you rerun a view that is already running
////                        if (manager.getActiveView() instanceof GameView) {
////                            //do nothing
////                        } else {
////                            int numOfPlayers = scanner.nextInt();
//////                            manager.showGameView(numOfPlayers);
////
////                        }
//
//                    }
//                });
//            } else if (cmd.equals(ServerCommands.SET_MESSAGE_BOX.toString())) {
//                String message = scanner.nextLine();
////                UICon.setMessageBoxText(message);
//            } else if (cmd.equals(ServerCommands.REMOVE_CARD_FROM_HAND.toString())) {
//                int playerId = scanner.nextInt();
//                int cardId = scanner.nextInt();
////                UICon.removeSelectedCardFromPlayer(playerId, cardId);
//            } else if (cmd.equals(ServerCommands.ADD_CARD_TO_HAND.toString())) {
//                int playerId = scanner.nextInt();
//                int currId = scanner.nextInt();
//                int cardId = scanner.nextInt();
//                String url = scanner.nextLine();
////                UICon.addCardToPlayer(playerId, currId, cardId, url);
//            } else if (cmd.equals(ServerCommands.FILL_DECK.toString())) {
////                UICon.fillDecks();
//            }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}