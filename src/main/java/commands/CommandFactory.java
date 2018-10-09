package commands;

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

        if (cmdName.getClazz() != null) {
            try {
                Command cmd = (Command) cmdName.getClazz().newInstance();
                cmd.parse(cmdString);
                return cmd;
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return new BasicCommand(cmdString);
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
