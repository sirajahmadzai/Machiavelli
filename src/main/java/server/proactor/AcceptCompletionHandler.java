package server.proactor;

import commands.Command;
import server.ClientMessageSender;
import server.models.Machiavelli;
import server.models.Player;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AcceptCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, SessionState> {
    private AsynchronousServerSocketChannel listener;

    public AcceptCompletionHandler(AsynchronousServerSocketChannel listener) {
        this.listener = listener;
    }

    @Override
    public void completed(AsynchronousSocketChannel socketChannel, SessionState sessionState) {
        // accept the next connection
        SessionState newSessionState = new SessionState();
        listener.accept(newSessionState, this);

        acceptPlayer(socketChannel, sessionState);

        // handle this connection
        ByteBuffer inputBuffer = ByteBuffer.allocate(512);
        ReadCompletionHandler readCompletionHandler = new ReadCompletionHandler(socketChannel, inputBuffer);
        socketChannel.read(inputBuffer, sessionState, readCompletionHandler);
    }

    @Override
    public void failed(Throwable exc, SessionState sessionState) {
        // Handle connection failure...
    }

    private void acceptPlayer(AsynchronousSocketChannel clientSocket, SessionState sessionState) {
        Machiavelli machiavelli = Machiavelli.getInstance();

        if (machiavelli.isTableFull()) {
            ClientMessageSender.sendCommand(clientSocket, Command.CommandNames.TABLE_IS_FULL);
        } else {
            Player player = machiavelli.addPlayer();
            sessionState.setProperty("player",player);

            ClientMessageSender.getInstance().registerPlayer(player, clientSocket);
            machiavelli.introducePlayer(player);

            machiavelli.startGame();
        }
    }

}