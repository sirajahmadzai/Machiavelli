package server.proactor;

import server.models.Machiavelli;
import server.models.Player;
import server.reactor.ClientCommandProcessor;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class ReadCompletionHandler implements CompletionHandler<Integer, SessionState> {

    private AsynchronousSocketChannel socketChannel;
    private ByteBuffer inputBuffer;

    public ReadCompletionHandler(AsynchronousSocketChannel socketChannel, ByteBuffer inputBuffer) {
        this.socketChannel = socketChannel;
        this.inputBuffer = inputBuffer;
    }

    @Override
    public void completed(Integer bytesRead, SessionState sessionState) {
        // Get the player who sends this message, from the session.
        Player player = (Player) sessionState.getProperty("player");

        if(bytesRead == -1){
            //TODO: Use another ServerCommand to let the server know this client is disconnected. And don't use Machiavelli here.
            Machiavelli.getInstance().playerLeftTheGame(player);
            return;
        }

        byte[] buffer = new byte[bytesRead];

        // Rewind the input buffer to read from the beginning
        inputBuffer.rewind();

        // Get the message from the buffer to string
        inputBuffer.get(buffer);
        String message = new String(buffer);

        ClientCommandProcessor.processCommand(message, player);

        inputBuffer.clear();
        // Read the next command when it arrives
        socketChannel.read(inputBuffer, sessionState, this);
    }

    @Override
    public void failed(Throwable exc, SessionState attachment) {
        //Handle read failure.....
    }
}