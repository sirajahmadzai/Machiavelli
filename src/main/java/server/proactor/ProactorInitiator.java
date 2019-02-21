package server.proactor;

import server.ServerModeInitiator;
import server.ServerModeRunner;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;

public class ProactorInitiator implements ServerModeInitiator {
    Proactor proactor = null;

    public ProactorInitiator(InetSocketAddress address) throws IOException {
        final AsynchronousServerSocketChannel listener = AsynchronousServerSocketChannel.open().bind(address);
        AcceptCompletionHandler acceptCompletionHandler = new AcceptCompletionHandler(listener);

        SessionState state = new SessionState();
        listener.accept(state, acceptCompletionHandler);
    }

    @Override
    public ServerModeRunner initiateMode() throws Exception {
        proactor = new Proactor();
        return proactor;
    }
}
