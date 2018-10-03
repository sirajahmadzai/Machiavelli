package server;

import commands.Command;
import commands.CommandFactory;
import commands.server.PlayerMove;
import server.models.Machiavelli;

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

    /*******************************************************************
     **************************PRIVATES********************************
     ******************************************************************/
    private Socket socket;
    private BufferedReader in;
    private int id;
    private PrintWriter out;
    private Machiavelli machiavelli;
    private Server server;

    public ClientHandler(Socket socket, Machiavelli machiavelli) throws Exception {
        this.socket = socket;
        this.machiavelli = machiavelli;

        in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        id = ++nextId;
    }

    /**
     *
     */
    @Override
    public void run() {
        try {
            while (true) {
                String cmdString = in.readLine();
                Command cmd  = CommandFactory.buildCommand(cmdString);
                processCommand(cmd);
            }

        } catch (SocketException e) {
            System.out.println("a Client disconnected!");
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
            }
        }
    }

    private void processCommand(Command cmd) {
        System.out.println("Command received: " + cmd.serialize());
        switch (cmd.getName()) {
            case NUMBER_OF_PLAYERS:
                break;
            case PASS_TURN:
                break;
            case PLAYER_MOVE:
                machiavelli.processMove((PlayerMove) cmd);
                break;

            default:
                cmd.execute();
                break;
        }
    }

    /**
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * @throws IOException
     */
    public void closeSocket() throws IOException {
        socket.close();
    }

    public void sendCommand(String command){
        out.println(command);
    }

    public void sendCommand(Command.CommandNames command){
        out.println(command);
    }
}