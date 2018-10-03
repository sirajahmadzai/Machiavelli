package commands;

import commands.client.DealHands;
import commands.client.IntroducePlayer;
import commands.client.Welcome;
import commands.server.PlayerMove;

import java.util.Scanner;

public class CommandFactory {
    private static Scanner scanner;
    private static CommandFactory ourInstance = new CommandFactory();

    public static CommandFactory getInstance() {
        return ourInstance;
    }

    private CommandFactory() {
    }

    public static Command buildCommand(String cmdString) {
        scanner = new Scanner(cmdString);
        Command.CommandNames cmdName = parseName();

        switch (cmdName) {
            case INTRODUCE_PLAYER:
                return new IntroducePlayer(scanner.next(), scanner.nextInt(), scanner.nextInt());
            case WELCOME:
                return new Welcome(scanner.next(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
            case PLAYER_MOVE:
                return new PlayerMove(cmdString);
            case DEAL_HANDS:
                return new DealHands(cmdString);
            default:
                return new Command(cmdString);
        }
    }

    private static Command.CommandNames parseName() {
        String nameStr = scanner.next();

        try {
            Command.CommandNames cmdName = Command.CommandNames.valueOf(nameStr);
            return cmdName;
        } catch (IllegalArgumentException e) {
            System.out.println("Unknown command name: " + nameStr);
            return null;
        }
    }
}
