package server;

import commands.Command;
import server.models.Player;
import server.proactor.SessionState;
import server.proactor.WriteCompletionHandler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

public class ClientMessageSender {
    private Map<Player, SocketChannel> playerChannels = new HashMap<>();
    private Map<Player, AsynchronousSocketChannel> playerAsyncChannels = new HashMap<>();

    private static ClientMessageSender ourInstance = new ClientMessageSender();

    public static ClientMessageSender getInstance() {
        return ourInstance;
    }

    private ClientMessageSender() {
    }

    public void registerPlayer(Player player, SocketChannel clientSocket) {
        playerChannels.put(player, clientSocket);
    }

    public void registerPlayer(Player player, AsynchronousSocketChannel clientSocket) {
        playerAsyncChannels.put(player, clientSocket);
    }

    public void removePlayer(Player player) {
        playerChannels.remove(player);
        playerAsyncChannels.remove(player);
    }

    public void sendCommand(Player player, String command) {
        if (playerChannels.containsKey(player)) {
            SocketChannel clientSocket = playerChannels.get(player);
            sendCommand(clientSocket, command);
        }

        if (playerAsyncChannels.containsKey(player)) {
            AsynchronousSocketChannel clientSocket = playerAsyncChannels.get(player);
            sendCommand(clientSocket, command);
        }
    }

    public static void sendCommand(SocketChannel clientSocket, String command) {
        ByteBuffer buffer = ByteBuffer.wrap(command.getBytes());
        System.out.println();
        System.out.println("SERVER: sending cmd(" + buffer.remaining() + "," + command.length() + ")" + command);

        try {
            while (buffer.hasRemaining()) {
                int len = clientSocket.write(buffer);
                if (len < 0) {
                    throw new IOException("Client connection lost!");
                }
                System.out.println("SERVER: written" + len + "bytes");
            }
            buffer.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param command
     */
    public static void sendCommand(SocketChannel clientSocket, Command.CommandNames command) {
        sendCommand(clientSocket, command.toString());
    }

    public static void sendCommand(AsynchronousSocketChannel clientSocket, Command.CommandNames command) {
        sendCommand(clientSocket, command.toString());
    }

    public static void sendCommand(AsynchronousSocketChannel clientSocket, String command) {
        // Echo the message back to client
        WriteCompletionHandler writeCompletionHandler = new WriteCompletionHandler(clientSocket);
        ByteBuffer outputBuffer = ByteBuffer.wrap(command.toString().getBytes());
        clientSocket.write(outputBuffer, new SessionState(), writeCompletionHandler);
    }
}
