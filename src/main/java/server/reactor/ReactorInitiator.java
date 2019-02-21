package server.reactor;

import server.ServerModeInitiator;
import server.ServerModeRunner;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;

public class ReactorInitiator implements ServerModeInitiator {
    private ServerSocketChannel serverSocket;

    public ReactorInitiator(InetSocketAddress address) throws IOException {
        serverSocket = ServerSocketChannel.open();
        serverSocket.bind(address);
        serverSocket.configureBlocking(false);
    }

    @Override
    public ServerModeRunner initiateMode() throws Exception {
        Reactor reactor = new Reactor();
        reactor.registerChannel(SelectionKey.OP_ACCEPT, serverSocket);
        reactor.registerEventHandler(SelectionKey.OP_ACCEPT, new AcceptEventHandler(reactor.getDemultiplexer()));
        reactor.registerEventHandler(SelectionKey.OP_READ, new ClientEventHandler());
        return reactor;
    }
}
