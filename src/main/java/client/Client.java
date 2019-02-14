package client;

import client.views.StartOptionsView;
import commands.Command;
import commands.CommandFactory;
import commands.server.PlayerLogin;
import commands.server.PlayerMove;
import interfaces.clientInterface;
import javafx.application.Platform;
import javafx.concurrent.Task;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client extends Task<Void> implements clientInterface/* implements Runnable*/ {

    /****************
     ****PRIVATES****
     ****************/
    private SocketChannel socket;
    private ClientManager manager;
    private String playerName;

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

        try {
            if (ip.equals("") && port == 0) {
                socket = SocketChannel.open(new InetSocketAddress("localhost", 9876));
            } else {
                socket = SocketChannel.open(new InetSocketAddress(ip, port));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * CONSTRUCTOR
     *
     * @param manager
     * @param port
     * @param playerName
     * @throws IOException
     */
    public Client(ClientManager manager, int port, String playerName) throws IOException {
        this(manager, "127.0.0.1", port, playerName);
    }

    /**
     * Processes all messages from server, according to the protocol.
     *
     * Reads data from the socket and accumulates the data in a string builder.
     * Because the commands may arrive partially or more then one command can be received in a single read.
     * Then we parse the accumulated string, when a full command is found it's deleted from the builder.
     * For example commands CMD1| and CMD2| may arrive like the following:
     *
     * CMD1| + CMD2|
     * CM + D1| + CMD2|
     * CMD1|CMD2|
     *
     * @return Void
     * @throws Exception
     */
    @Override
    protected Void call() throws Exception {

        try {
            String response;
            StringBuilder builder = new StringBuilder();

            while (true) {
                ByteBuffer buffer = ByteBuffer.allocate(64);
                socket.read(buffer);
                buffer.flip();
                response = new String(buffer.array()).trim();
                builder.append(response);
                buffer.clear();

                String[] commands = builder.toString().split("(?<=\\|)");
                for (String command : commands) {
                    if (command.endsWith(Command.EOC)) {
                        builder.delete(0, command.length());
                        processCommand(command);
                    }
                }
            }
        } catch (Exception e) {
            Platform.runLater(manager::connectionLost);
            e.printStackTrace();
        }
        return null;
    }

    private void processCommand(String commandStr){
        System.out.println("Command received at client: " + commandStr);

        Command command = CommandFactory.buildCommand(commandStr);
        switch (command.getName()) {
            case PLAYER_MOVE:
                Platform.runLater(() -> manager.playMove((PlayerMove) command));
                break;
            case WHO_ARE_YOU:
                sendCommandToServer(new PlayerLogin(this.playerName));
                break;
            case TABLE_IS_FULL:
                StartOptionsView.getInstance().setMessageText("Sorry you can not join the game. The table is full.");
                break;
            default:
                command.execute();
        }
    }

    /**
     * sends the given command to the server
     *
     * @param cmd
     */
    public void sendCommandToServer(Command cmd) {
        ByteBuffer buffer = ByteBuffer.wrap(cmd.serialize().getBytes());
        try {
            socket.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}