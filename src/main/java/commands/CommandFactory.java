package commands;

import java.util.Scanner;

/**
 * Singleton factory class that creates concrete commands from command strings.
 */
public class CommandFactory {
    /**
     * PRIVATE STATICS
     */
    private static Scanner scanner;
    private static CommandFactory ourInstance = new CommandFactory();

    /**
     * CONSTRUCTOR
     */
    private CommandFactory() {
    }

    /**
     * GETTERS
     */
    /**
     * gets the singleton instance
     *
     * @return
     */
    public static CommandFactory getInstance() {
        return ourInstance;
    }

    /**
     * Builds concrete command instance from command string.
     *
     * @param cmdString the serialized command string.
     * @return created concrete command.
     */
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

    /**
     * Parses the name part of the command string and creates a CommandName enum from that.
     *
     * @return CommandName enum
     */
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
