package server;

import commands.Command;
import commands.CommandFactory;
import commands.server.PlayerLogin;
import commands.server.PlayerMove;
import server.models.Machiavelli;
import server.models.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;


public class ClientHandler implements Runnable {

    /*******************************************************************
     **************************PRIVATE STATICS********************************
     ******************************************************************/
    private static int nextId = 0;
    private final Machiavelli machiavelli = Machiavelli.getInstance();

    /*******************************************************************
     **************************PRIVATES********************************
     ******************************************************************/
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Server server;
    private Player player;

    /**
     * @param socket
     * @param server
     * @throws Exception
     */
    public ClientHandler(Socket socket, Server server) throws Exception {
        this.socket = socket;
        this.server = server;

        in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    /**
     * GETTERS
     */
    /**
     * @return
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * SETTERS
     */
    /**
     * @param player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     *
     */
    @Override
    public void run() {
        sendCommand(Command.CommandNames.WHO_ARE_YOU);

        try {
            while (true) {
                String cmdString = in.readLine();
                Command cmd = CommandFactory.buildCommand(cmdString);
                processCommand(cmd);
            }

        } catch (SocketException e) {
            System.out.println("a Client disconnected!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // This client is going down!  Remove its name and its print
            // writer from the sets, and close its socket.
            if (out != null) {
                server.removeClientHandler(this);
            }
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param cmd
     */
    private void processCommand(Command cmd) {
        System.out.println("Command received: " + cmd.serialize());
        switch (cmd.getName()) {
            case PLAYER_MOVE:
                machiavelli.processMove((PlayerMove) cmd);
                break;
            case PLAYER_LOGIN:
                machiavelli.playerLogin(((PlayerLogin) cmd).getPlayerName(), player);
                break;
            case SET_WINNER:
                machiavelli.setWinner();
                break;

            default:
                cmd.execute();
                break;
        }
    }

    /**
     * @throws IOException
     */
    public void closeSocket() throws IOException {
        socket.close();
    }

    /**
     * @param command
     */
    public void sendCommand(String command) {
        out.println(command);
    }

    /**
     * @param command
     */
    public void sendCommand(Command.CommandNames command) {
        out.println(command);
    }
}