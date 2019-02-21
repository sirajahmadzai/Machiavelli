package server.reactor;

import server.models.Machiavelli;
import server.models.Player;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;


public class ClientEventHandler implements EventHandler {

    /*******************************************************************
     **************************PRIVATE STATICS********************************
     ******************************************************************/
    private final Machiavelli machiavelli = Machiavelli.getInstance();

    /*******************************************************************
     **************************PRIVATES********************************
     ******************************************************************/


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

            ClientCommandProcessor.processCommand(cmdString, player);
        } catch (IOException e) {
            clientSocket.close();
            machiavelli.playerLeftTheGame(player);
            e.printStackTrace();
        }
    }
}