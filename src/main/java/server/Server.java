package server;

import server.models.Machiavelli;
import server.proactor.ProactorInitiator;
import server.reactor.ReactorInitiator;
import utils.constants;

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

    private constants.GameMode gameMode;

    /**
     * CONSTRUCTOR
     *
     * @param port
     * @param numPlayers
     * @param serverMode
     * @param barrier
     * @throws IOException
     */
    public Server(int port, int numPlayers, constants.GameMode serverMode, CyclicBarrier barrier) throws IOException {
        this.barrier = barrier;
        this.port = port;
        machiavelli = Machiavelli.getInstance();
        machiavelli.initialize(numPlayers);
        gameMode = serverMode;
        String logText = "starting server on: localhost at port " + port + " in "+ gameMode + " mode";
        log.info(logText);
        System.out.println(logText);
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
            switch (gameMode) {
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
