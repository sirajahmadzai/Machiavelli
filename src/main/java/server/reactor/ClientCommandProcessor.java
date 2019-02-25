package server.reactor;

import commands.Command;
import commands.CommandFactory;
import commands.server.PlayerLogin;
import commands.server.PlayerMove;
import server.models.Machiavelli;
import server.models.Player;

public class ClientCommandProcessor {
    /**
     * Parses the command string and passes it to the next function.
     *
     * @param commandString Raw command string
     * @param player        player who sent the command.
     */
    public static void processCommand(String commandString, Player player) {
        Command command = CommandFactory.buildCommand(commandString);
        processCommand(command, player);
    }

    /**
     * Executes the client command.
     *
     * @param command the command that client has sent.
     * @param player  player who sent the command.
     */
    public static void processCommand(Command command, Player player) {
        System.out.println("Command received: " + command.serialize());
        Machiavelli machiavelli = Machiavelli.getInstance();
        switch (command.getName()) {
            case PLAYER_MOVE:
                machiavelli.processMove((PlayerMove) command);
                break;
            case PLAYER_LOGIN:
                machiavelli.playerLogin(((PlayerLogin) command).getPlayerName(), player);
                break;
            default:
                command.setPlayer(player);
                command.execute();
                break;
        }
    }
}
