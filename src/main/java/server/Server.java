package server;

import server.gameLogic.GameState;
import server.gameLogic.WaitingForPlayersState;
import server.models.Machiavelli;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Server implements Runnable {
    final static Logger log = Logger.getLogger(String.valueOf(Server.class));
    private ArrayList<ClientHandler> playerClientHandlers = new ArrayList<>();
    private ServerSocket listener;
    private int numPlayers = -1;
    private GameState currentState;
    private boolean gameStarted = false;
    private int port;
    private Machiavelli machiavelli;

    public Server() throws IOException {
        log.info("starting server on: localhost at port 9876");
        System.out.println("starting server on: localhost at port 9876");
        listener = new ServerSocket(9876);
        currentState = WaitingForPlayersState.getInstance();
    }

    public Server(int port, int numPlayers) throws IOException {
        this.numPlayers = numPlayers;
        this.port = port;
        this.machiavelli = new Machiavelli(numPlayers);

        log.info("starting server on: localhost at port " + port);
        System.out.println("starting server on: localhost at port " + port);
        listener = new ServerSocket(port);
        currentState = WaitingForPlayersState.getInstance();
    }

    public Server(String ip, int port) throws Exception {
        log.info("starting server on: " + ip + " at port " + port);
        System.out.println("starting server on: " + ip + " at port " + port);
        listener = new ServerSocket(port, 100, InetAddress.getByName(ip));
        listener = new ServerSocket(port);
    }

    public void removeClientHandler(ClientHandler clientHandler) {
        playerClientHandlers.remove(clientHandler);
    }


    private void startClientThread(ClientHandler clientHandler) {
        Thread handlerThread = new Thread(clientHandler);
        handlerThread.setName("ClientHandlerThread " + clientHandler.getId());

        log.info("Client connected!");
//        clientHandler.showGameView(numPlayers);
        handlerThread.start();
    }

//    private void startGame() {
//        for (ClientHandler cH : playerClientHandlers) {
//            cH.showGameView(numPlayers);
//        }
//        gameStarted = true;
//    }

    @Override
    public void run() {
        try {
            System.out.println("server started");
            while (true) {
                System.out.println("Accepting next Client..");
                ClientHandler clientHandler = new ClientHandler(listener.accept());

                if (machiavelli.isTableFull()) {
                    clientHandler.sendCommand("TABLE_IS_FULL");
                } else {
                    machiavelli.addPlayer(clientHandler);

                    playerClientHandlers.add(clientHandler);
                    startClientThread(clientHandler);

                    machiavelli.startGame();
                }
//                TODO: implement a way to stop server.
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            for (ClientHandler cH : playerClientHandlers) {
                try {
                    cH.closeSocket();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                listener.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int getPort() {
        return port;
    }
}
