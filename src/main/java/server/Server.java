package server;

import commands.Command;
import server.gameLogic.GameState;
import server.gameLogic.WaitingForPlayersState;
import server.models.Machiavelli;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Server implements Runnable {
    final static Logger log = Logger.getLogger(String.valueOf(Server.class));
    private ArrayList<ClientHandler> playerClientHandlers = new ArrayList<>();
    private ServerSocket listener;
    private int numPlayers = -1;
    private GameState currentState;
    private int port;
    private Machiavelli machiavelli;

    public Server(int port, int numPlayers) throws IOException {
        this.numPlayers = numPlayers;
        this.port = port;
        machiavelli = Machiavelli.getInstance();
        machiavelli.initialize(numPlayers);

        log.info("starting server on: localhost at port " + port);
        System.out.println("starting server on: localhost at port " + port);
        listener = new ServerSocket(port);
        currentState = WaitingForPlayersState.getInstance();
    }

    public void removeClientHandler(ClientHandler clientHandler) {
        machiavelli.removePlayer(clientHandler);
        playerClientHandlers.remove(clientHandler);
    }

    private void startClientThread(ClientHandler clientHandler) {
        Thread handlerThread = new Thread(clientHandler);
        handlerThread.setName("ClientHandlerThread " + clientHandler.getPlayer().getName());

        log.info("Client connected!");
        handlerThread.start();
    }


    @Override
    public void run() {
        try {
            System.out.println("server started");
            while (true) {
                System.out.println("Accepting next Client..");
                ClientHandler clientHandler = new ClientHandler(listener.accept(), this);

                if (machiavelli.isTableFull()) {
                    clientHandler.sendCommand(Command.CommandNames.TABLE_IS_FULL);
                } else {
                    machiavelli.addPlayer(clientHandler);

                    playerClientHandlers.add(clientHandler);
                    startClientThread(clientHandler);

                    machiavelli.startGame();
                }
//              TODO: implement a way to stop server.
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
