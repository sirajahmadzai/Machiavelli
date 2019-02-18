package server;

import server.models.Machiavelli;
import server.reactor.AcceptEventHandler;
import server.reactor.Reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Logger;


public class Server implements Runnable {
    /**
     * PRIVATE STATICS
     */
    final static Logger log = Logger.getLogger(String.valueOf(Server.class));
    private final CyclicBarrier barrier;

    /**
     * PRIVATES
     */
    private int port;
    private Machiavelli machiavelli;

    /**
     * CONSTRUCTOR
     *
     * @param port
     * @param numPlayers
     * @param barrier
     * @throws IOException
     */
    public Server(int port, int numPlayers, CyclicBarrier barrier) throws IOException {
        this.barrier = barrier;
        this.port = port;
        machiavelli = Machiavelli.getInstance();
        machiavelli.initialize(numPlayers);

        log.info("starting server on: localhost at port " + port);
        System.out.println("starting server on: localhost at port " + port);
    }

    /**
     * @return
     */
    public int getPort() {
        return port;
    }

    public void run() {
        try {
            ServerSocketChannel serverSocket = ServerSocketChannel.open();
            serverSocket.bind(new InetSocketAddress(this.port));
            serverSocket.configureBlocking(false);

            Reactor reactor = new Reactor();

            reactor.registerChannel(SelectionKey.OP_ACCEPT, serverSocket);
            reactor.registerEventHandler(SelectionKey.OP_ACCEPT, new AcceptEventHandler(reactor.getDemultiplexer()));
            reactor.registerEventHandler(SelectionKey.OP_READ, new ClientHandler());

            // Server is ready to accept clients.
            barrier.await();
            reactor.run();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
