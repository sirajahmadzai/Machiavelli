package client;

import commands.Command;
import commands.CommandFactory;
import commands.server.PlayerLogin;
import commands.server.PlayerMove;
import javafx.application.Platform;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Task<Void>/* implements Runnable*/ {

    /*******************************************************************
     **************************PRIVATES********************************
     ******************************************************************/
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private ClientManager manager;
    private String playerName;
    private int seatNumber;

    /**
     * CONSTRUCTOR
     *
     * @param manager
     * @param ip
     * @param port
     * @throws IOException
     */
    public Client(ClientManager manager, String ip, int port, String playerName) throws IOException {
        this.manager = manager;
        this.playerName = playerName;

        if (ip.equals("") && port == 0) {
            socket = new Socket("localhost", 9876);
        } else {
            socket = new Socket(ip, port);
        }

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        manager.in = in;
        manager.out = out;
    }

    public Client(ClientManager manager, int port, String playerName) throws IOException {
        this(manager, "127.0.0.1", port, playerName);
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
                    case PLAYER_MOVE:
                        Platform.runLater(() -> manager.playMove((PlayerMove) command));
                        break;
                    case WHO_ARE_YOU:
                        sendCommandToServer(new PlayerLogin(this.playerName));
                        break;
                    default:
                        command.execute();
                }

//                System.out.println("Command processed " + command);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void sendCommandToServer(Command cmd) {
        this.out.println(cmd.serialize());
    }
}