package server;

import server.models.Machiavelli;
import server.proactor.ProactorInitiator;
import server.reactor.ReactorInitiator;

import java.io.IOException;
import java.net.InetSocketAddress;
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

    enum OperationMode {
        REACTIVE,
        PROACTIVE
    }

    private OperationMode operationMode = OperationMode.PROACTIVE;

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

    public void setOperationMode(OperationMode operationMode) {
        this.operationMode = operationMode;
    }

    /**
     * @return
     */
    public int getPort() {
        return port;
    }

    public void run() {
        try {
            ServerModeInitiator serverModeInitiator = null;
            InetSocketAddress address = new InetSocketAddress(port);
            switch (operationMode) {
                case REACTIVE:
                    serverModeInitiator = new ReactorInitiator(address);
                    break;
                case PROACTIVE:
                    serverModeInitiator = new ProactorInitiator(address);
                    break;
            }

            ServerModeRunner serverModeRunner = serverModeInitiator.initiateMode();

            // Server is ready to accept clients.
            barrier.await();
            serverModeRunner.run();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
