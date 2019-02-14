package server;

import commands.Command;
import commands.CommandFactory;
import commands.server.PlayerLogin;
import commands.server.PlayerMove;
import server.models.Machiavelli;
import server.models.Player;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;


public class ClientHandler {

    /*******************************************************************
     **************************PRIVATE STATICS********************************
     ******************************************************************/
    private final Machiavelli machiavelli = Machiavelli.getInstance();

    /*******************************************************************
     **************************PRIVATES********************************
     ******************************************************************/
    private SocketChannel clientSocket;
    private Player player;

    /**
     * @param clientSocket
     * @throws Exception
     */
    public ClientHandler(SocketChannel clientSocket){
        this.clientSocket = clientSocket;
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

    public SocketChannel getClientSocket() {
        return clientSocket;
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
    public void processCommand() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(256);
        int readByteCount = clientSocket.read(buffer);
        if(readByteCount == -1){
            throw new IOException("Client disconnected!");
        }
        String cmdString = new String(buffer.array()).trim();
        buffer.clear();

        Command cmd = CommandFactory.buildCommand(cmdString);
        processCommand(cmd);
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
            default:
                cmd.execute();
                break;
        }
    }

    /**
     * @param command
     */
    public void sendCommand(String command) {
        ByteBuffer buffer = ByteBuffer.wrap(command.getBytes());
        System.out.println();
        System.out.println("SERVER: sending cmd("+buffer.remaining()+","+command.length()+ ")"+command);

        try {
            int writtenBytes = 0;
            int totalBytes = buffer.limit();
            while (writtenBytes < totalBytes) {
                writtenBytes += clientSocket.write(buffer);
                System.out.println("SERVER: written"+  writtenBytes+"bytes");
            }
            buffer.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param command
     */
    public void sendCommand(Command.CommandNames command) {
        sendCommand(command.toString());
    }

}