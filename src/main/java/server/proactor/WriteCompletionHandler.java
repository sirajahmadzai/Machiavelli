package server.proactor;

import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class WriteCompletionHandler implements CompletionHandler<Integer, SessionState> {
    private AsynchronousSocketChannel socketChannel;

    public WriteCompletionHandler(AsynchronousSocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    @Override
    public void completed(Integer bytesWritten, SessionState attachment) {
        // Do nothing
    }

    @Override
    public void failed(Throwable exc, SessionState attachment) {
        // Handle write failure.....
    }

}