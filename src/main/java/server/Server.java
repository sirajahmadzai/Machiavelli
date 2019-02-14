package server;

import commands.Command;
import server.models.Machiavelli;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
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
    private ArrayList<ClientHandler> playerClientHandlers = new ArrayList<>();
    private ServerSocket listener;
    private int numPlayers = -1;
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
        System.out.println("Server is created");
        this.barrier = barrier;
        this.numPlayers = numPlayers;
        this.port = port;
        machiavelli = Machiavelli.getInstance();
        machiavelli.initialize(numPlayers);

        log.info("starting server on: localhost at port " + port);
        System.out.println("starting server on: localhost at port " + port);
        listener = new ServerSocket(port);
    }

    /**
     * @return
     */
    public int getPort() {
        return port;
    }

    /**
     * removes the given clientHandler
     *
     * @param clientHandler
     */
    public void removeClientHandler(ClientHandler clientHandler) {
        machiavelli.removePlayer(clientHandler);
        machiavelli.resetGame();
        playerClientHandlers.remove(clientHandler);
    }

    public void run() {
        Selector selector;
        ServerSocketChannel serverSocket;

        try {
            selector = Selector.open();
            serverSocket = ServerSocketChannel.open();
            serverSocket.bind(new InetSocketAddress("localhost", this.port));
            serverSocket.configureBlocking(false);
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);

            // Server is ready to accept clients.
            barrier.await();

            while (true) {
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iter = selectedKeys.iterator();
                while (iter.hasNext()) {

                    SelectionKey key = iter.next();

                    if (key.isAcceptable()) {
                        ClientHandler clientHandler = getClientHandler(serverSocket);
                        if (machiavelli.isTableFull()) {
                            clientHandler.sendCommand(Command.CommandNames.TABLE_IS_FULL);
                        } else {
                            registerClientHandler(selector, clientHandler);

                            machiavelli.addPlayer(clientHandler);
                            key.attach(clientHandler);
                            clientHandler.sendCommand(Command.CommandNames.WHO_ARE_YOU);

                            playerClientHandlers.add(clientHandler);
                            machiavelli.startGame();
                        }
                    }

                    if (key.isReadable()) {
                        processCommand(key);
                    }
                    iter.remove();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void processCommand(SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        ClientHandler clientHandler = (ClientHandler) key.attachment();
        try {
            clientHandler.processCommand();
        } catch (IOException e) {
            client.close();
            removeClientHandler(clientHandler);
            e.printStackTrace();
        }
    }


    /**
     * Registers the clientHandler to the selector
     *
     * @param selector the selector to which the handler will register.
     * @param clientHandler the client handler which will be responsible for handling currently accepted clients requests.
     * @throws ClosedChannelException
     */
    private void registerClientHandler(Selector selector, ClientHandler clientHandler) throws ClosedChannelException {
        SelectionKey key = clientHandler.getClientSocket().register(selector, SelectionKey.OP_READ);
        key.attach(clientHandler);
    }

    /**
     * Accepts the connection request and creates a clientHandler specific to the requesting channel.
     * @param serverSocket
     * @return the clientHandler specific to the requesting channel.
     * @throws Exception
     */
    private ClientHandler getClientHandler(ServerSocketChannel serverSocket) throws IOException {
        SocketChannel clientSocket = serverSocket.accept();
        clientSocket.configureBlocking(false);
        return new ClientHandler(clientSocket);
    }
}
