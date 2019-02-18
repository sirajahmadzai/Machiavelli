package server;

import commands.Command;
import commands.CommandFactory;
import commands.server.PlayerLogin;
import commands.server.PlayerMove;
import server.models.Machiavelli;
import server.models.Player;
import server.reactor.EventHandler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;


public class ClientHandler implements EventHandler {

    /*******************************************************************
     **************************PRIVATE STATICS********************************
     ******************************************************************/
    private final Machiavelli machiavelli = Machiavelli.getInstance();

    /*******************************************************************
     **************************PRIVATES********************************
     ******************************************************************/

    /**
     * @param cmd
     * @param player
     */
    private void processCommand(Command cmd, Player player) {
        System.out.println("Command received: " + cmd.serialize());
        switch (cmd.getName()) {
            case PLAYER_MOVE:
                machiavelli.processMove((PlayerMove) cmd);
                break;
            case PLAYER_LOGIN:
                machiavelli.playerLogin(((PlayerLogin) cmd).getPlayerName(), player);
                break;
            default:
                cmd.execute();
                break;
        }
    }

    @Override
    public void handleEvent(SelectionKey key) throws IOException {
        SocketChannel clientSocket = (SocketChannel) key.channel();
        Player player = (Player) key.attachment();

        try {
            ByteBuffer buffer = ByteBuffer.allocate(256);
            int readByteCount = clientSocket.read(buffer);
            if (readByteCount == -1) {
                throw new IOException("Client disconnected!");
            }
            String cmdString = new String(buffer.array()).trim();
            buffer.clear();

            Command cmd = CommandFactory.buildCommand(cmdString);
            processCommand(cmd, player);

        } catch (IOException e) {
            clientSocket.close();
            machiavelli.playerLeftTheGame(player);
            e.printStackTrace();
        }
    }
}