package commands;

import com.sun.javaws.exceptions.InvalidArgumentException;
import commands.client.*;
import commands.server.PassTurn;
import commands.server.PlayerLogin;
import commands.server.PlayerMove;
import commands.server.WinnerCommand;
import server.models.Player;

import java.util.Scanner;
import java.util.Stack;
import java.util.StringJoiner;

import static commands.Command.CommandTypes.CLIENT_COMMAND;
import static commands.Command.CommandTypes.SERVER_COMMAND;

/**
 * Base class for both server and client commands.
 */
public abstract class Command {
    // End Of Command
    public static final String EOC = "|";
    public static final String PARAMETER_SEPERATOR = "&";

    /**
     * PROTECTS
     */
    protected CommandNames name;
    protected Stack<Object> parameters = new Stack<>();
    protected Scanner scanner;
    private String commandString;
    private Player player;

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
        this.commandString = commandStr;
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
     */
    CommandNames parseName() {
        scanner = getScanner(this.commandString);
        return parseName(scanner);
    }

    // For external use
    static CommandNames parseName(String commandStr) {
        return parseName(getScanner(commandStr));
    }

    private static CommandNames parseName(Scanner scanner) {
        String name = scanner.next();
        return CommandNames.valueOf(name);
    }

    private static Scanner getScanner(String cmdString) {
        try {
            Scanner s = new Scanner(unwrapCommandString(cmdString));
            s.useDelimiter(PARAMETER_SEPERATOR);
            return s;
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Parses command string and adds all parameters to this command.
     *
     * @param commandStr the string to be parsed.
     */
    void parse(String commandStr) {
        this.commandString = commandStr;
        this.name = parseName();

        while (scanner.hasNext()) {
            addParameter(scanner.next());
        }

        // Restart scanning so that subclasses can use the scanner.
        parseName();
        doParse(commandStr);
    }

    /**
     * Serializes the command into string. This method is used while exchenging the commands between server and client.
     *
     * @return
     */
    public String serialize() {
        StringJoiner joiner = new StringJoiner(PARAMETER_SEPERATOR);
        joiner.add(name.toPlainString());

        for (Object parameter : parameters) {
            joiner.add(parameter.toString());
        }
        return wrapCommandString(joiner.toString());
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return serialize();
    }

    public static String wrapCommandString(String str) {
        return str + EOC;
    }

    public static String unwrapCommandString(String str) throws InvalidArgumentException {
        if (str.endsWith(EOC)) {
            return str.substring(0, str.length() - EOC.length());
        } else {
            String[] args = new String[1];
            args[0] = "Command format is invalid: "+ str;
            throw new InvalidArgumentException(args);
        }
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

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

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
        SET_WINNER(SERVER_COMMAND, WinnerCommand.class),

        //      CLIENT_COMMANDS
        INTRODUCE_PLAYER(CLIENT_COMMAND, IntroducePlayer.class),
        WELCOME(CLIENT_COMMAND, Welcome.class),
        TABLE_IS_FULL(CLIENT_COMMAND),
        SWITCH_TURN(CLIENT_COMMAND, SwitchTurn.class),
        WHO_ARE_YOU(CLIENT_COMMAND),
        DRAW_CARD(CLIENT_COMMAND, DrawCard.class),

        REMOVE_PLAYER(CLIENT_COMMAND, RemovePlayer.class),
        CLIENT_MESSAGE(CLIENT_COMMAND, ClientMessage.class);


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

        @Override
        public String toString() {
            String str = super.toString();
            return Command.wrapCommandString(str);
        }

        protected String toPlainString() {
            return super.toString();
        }

    }
}
