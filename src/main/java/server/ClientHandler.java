package server;

import commands.ClientCommands;
import commands.Command;
import commands.ServerCommands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;


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
    private Server server;

    public ClientHandler(Socket socket) throws Exception {
        this.socket = socket;
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
                Command cmd = new Command(cmdString);
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
        switch (cmd.getName()) {
            case NUMBER_OF_PLAYERS:
                break;
//            case DECK_CLICKED:
//                break;
//            case PROMPT_PLAYER:
//                break;
            default:
                System.out.println("Command received: " + cmd.serialize());
                cmd.execute();
                break;
        }
    }

    public int checkForMessageInt() {
        int result = -1;
        try {
            if (in.ready()) {
                Scanner scanner = new Scanner(in.readLine());

                String cmd = scanner.next();

                if (cmd.equals((ClientCommands.NUMBER_OF_PLAYERS.toString()))) {
                    int numOfPlayers = scanner.nextInt();
                    result = numOfPlayers;
//                } else if (cmd.equals(ClientCommands.PROMPT_BIDS_REPLY.toString())) {
                    int bids = scanner.nextInt();
                    result = bids;
                }

                scanner.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean checkForMessageBool() {
        try {
            if (in.ready()) {
                Scanner scanner = new Scanner(in.readLine());

                String cmd = scanner.next();

                if (cmd.equals((ClientCommands.DECK_CLICKED.toString()))) {
                    scanner.close();
                    return true;
                }

                scanner.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public String checkForMessageString() {
        String res = "";
        try {
            if (in.ready()) {
                Scanner scanner = new Scanner(in.readLine());
                String cmd = scanner.next();

                if (cmd.equals((ClientCommands.PROMPT_PLAYER.toString()))) {
                    boolean result = scanner.nextBoolean();
                    if (result) {
                        res = "true";
                    } else {
                        res = "false";
                    }
                } else if (cmd.equals((ClientCommands.PROMPT_PLAY_CARD.toString()))) {
                    String cards = scanner.nextLine();
                    res = cards;
                } else if (cmd.equals((ClientCommands.PROMPT_PLAY_CARD.toString()))) {
                    String cards = scanner.nextLine();
                    res = cards;
                } else if (cmd.equals((ClientCommands.PROMPT_PLAY_CARD.toString()))) {
                    String cards = scanner.nextLine();
                    res = cards;
                } else if (cmd.equals((ClientCommands.PROMPT_END_TURN.toString()))) {
                    boolean result = scanner.nextBoolean();
                    if (result) {
                        res = "true";
                    } else {
                        res = "false";
                    }
                }

                scanner.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * @param numOfPlayers
     */
    public void showGameView(int numOfPlayers) {
        out.println(ServerCommands.SHOW_GAMEVIEW + " " + numOfPlayers);
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
}