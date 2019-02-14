package commands;

/**
 * Singleton factory class that creates concrete commands from command strings.
 */
public class CommandFactory {
    /**
     * PRIVATE STATICS
     */
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
        Command.CommandNames cmdName = Command.parseName(cmdString);

        if (cmdName.getClazz() != null) {
            try {
                Command cmd = (Command) cmdName.getClazz().newInstance();
                cmd.parse(cmdString);
                return cmd;
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                return new BasicCommand(cmdString);
            }
        } else {
            return new BasicCommand(cmdString);
        }
    }
}
