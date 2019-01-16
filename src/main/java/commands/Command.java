package commands;

import commands.client.*;
import commands.server.PassTurn;
import commands.server.PlayerLogin;
import commands.server.PlayerMove;

import java.util.Scanner;
import java.util.Stack;
import java.util.StringJoiner;

import static commands.Command.CommandTypes.CLIENT_COMMAND;
import static commands.Command.CommandTypes.SERVER_COMMAND;

/**
 * Base class for both server and client commands.
 */
public abstract class Command {

    /**
     * PROTECTS
     */
    protected CommandNames name;
    protected Stack<Object> parameters = new Stack<>();
    protected Scanner scanner;

    /**
     * CONSTRUCTOR
     */
    public Command() {
        super();
    }

    /**
     * CONSTRUCTOR
     *
     * @param cmdName
     */
    public Command(CommandNames cmdName) {
        this.name = cmdName;
    }

    /**
     * CONSTRUCTOR
     *
     * @param commandStr
     */
    public Command(String commandStr) {
        this();
        parse(commandStr);
    }

    /**
     * GETTERS
     */
    /**
     * @return
     */
    public Stack<Object> getParameters() {
        return parameters;
    }

    /**
     * @return
     */
    public String getParameter() {
        return parameters.get(0).toString();
    }

    /**
     * @return
     */
    public CommandNames getName() {
        return name;
    }

    /**
     * Adds a new parameter to the command.
     * These parameters are used when serializing the command.
     *
     * @param param parameter to add.
     */
    protected void addParameter(Object param) {
        parameters.add(param);
    }

    protected void removeParameter(Object param) {
        parameters.remove(param);
    }

    /**
     * Creates a scanner for parsing the command and parses just the name of the command.
     * This behaviour is shared in all subclasses.
     *
     * @param commandStr
     */
    private void parseName(String commandStr) {
        scanner = new Scanner(commandStr);
        String name = scanner.next();
        try {
            this.name = CommandNames.valueOf(name);
        } catch (IllegalArgumentException e) {
            System.out.println("Unknown command name: " + name);
        }
    }

    /**
     * Parses command string and adds all parameters to this command.
     *
     * @param commandStr the string to be parsed.
     */
    void parse(String commandStr) {
        parseName(commandStr);

        while (scanner.hasNext()) {
            addParameter(scanner.next());
        }

//      Restart scanning so that subclasses can use the scanner.
        parseName(commandStr);
        doParse(commandStr);
    }

    /**
     * Serializes the command into string. This method is used while exchenging the commands between server and client.
     *
     * @return
     */
    public String serialize() {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(name.toString());

        for (Object parameter : parameters) {
            joiner.add(parameter.toString());
        }
        return joiner.toString();
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return serialize();
    }

    /**
     * Commands can execute themselves.
     * This method implements the general logic and leaves the concrete implementation to subclasses calling doExecute method.
     */
    public abstract void execute();

    /**
     * Parse the given command string and initialize command parameters.
     *
     * @param commandStr
     */
    public abstract void doParse(String commandStr);

    /**
     * Concrete implementation of command execution.
     */
    protected abstract void doExecute();

    /**
     *
     */
    public enum CommandTypes {
        SERVER_COMMAND,
        CLIENT_COMMAND
    }

    /**
     * Enums to be used as command names.
     */
    public enum CommandNames {
        //      SERVER_COMMANDS
        NUMBER_OF_PLAYERS(SERVER_COMMAND),
        SHOW_GAMEVIEW(SERVER_COMMAND),
        DEAL_HANDS(SERVER_COMMAND, DealHands.class),
        PLAYER_MOVE(SERVER_COMMAND, PlayerMove.class),
        PASS_TURN(SERVER_COMMAND, PassTurn.class),
        PLAYER_LOGIN(SERVER_COMMAND, PlayerLogin.class),

        //      CLIENT_COMMANDS
        INTRODUCE_PLAYER(CLIENT_COMMAND, IntroducePlayer.class),
        WELCOME(CLIENT_COMMAND, Welcome.class),
        TABLE_IS_FULL(CLIENT_COMMAND),
        SWITCH_TURN(CLIENT_COMMAND, SwitchTurn.class),
        WHO_ARE_YOU(CLIENT_COMMAND),
        DRAW_CARD(CLIENT_COMMAND, DrawCard.class),
        REMOVE_PLAYER(CLIENT_COMMAND, RemovePlayer.class);


        /**
         * PRIVATES
         */
        private final CommandTypes type;
        private final Class clazz;

        /**
         * CONSTRUCTOR
         * <p>
         * Initialize the command name using command type and command class.
         * The clazz is used to create concrete command instances from command strings.
         *
         * @param type
         * @param clazz
         */
        CommandNames(CommandTypes type, Class<? extends Command> clazz) {
            this.type = type;
            this.clazz = clazz;
        }

        /**
         * CONSTRUCTOR
         * <p>
         * Initialize the command name using only the command type.
         *
         * @param type
         */
        CommandNames(CommandTypes type) {
            this.type = type;
            this.clazz = null;
        }

        /**
         * GETTERS
         */
        /**
         * @return
         */
        public CommandTypes getType() {
            return type;
        }

        public Class getClazz() {
            return clazz;
        }
    }
}
