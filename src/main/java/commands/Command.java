package commands;

import commands.client.*;
import commands.server.PassTurn;
import commands.server.PlayerLogin;
import commands.server.PlayerMove;
import javafx.application.Platform;

import java.util.Scanner;
import java.util.Stack;
import java.util.StringJoiner;

import static commands.Command.CommandTypes.CLIENT_COMMAND;
import static commands.Command.CommandTypes.SERVER_COMMAND;


public abstract class Command {
    public CommandNames getName() {
        return name;
    }

    public void execute() {
        Platform.runLater(this::doExecute);
    }

    public enum CommandTypes {
        SERVER_COMMAND,
        CLIENT_COMMAND
    }

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
        DRAW_CARD(CLIENT_COMMAND, DrawCard.class);

        private final CommandTypes type;
        private final Class clazz;

        CommandNames(CommandTypes type, Class<? extends Command> clazz) {
            this.type = type;
            this.clazz = clazz;
        }

        CommandNames(CommandTypes type) {
            this.type = type;
            this.clazz = null;
        }

        public CommandTypes getType() {
            return type;
        }

        public Class getClazz() {
            return clazz;
        }
    }

    protected CommandNames name;
    protected Stack<Object> parameters = new Stack<>();
    protected Scanner scanner;


    public Command() {
        super();
    }

    public Command(CommandNames cmdName) {
        this.name = cmdName;
    }

    public Command(String commandStr) {
        this();
        parse(commandStr);
    }

    protected void addParameter(Object param) {
        parameters.add(param);
    }

    public Stack<Object> getParameters() {
        return parameters;
    }

    public String getParameter() {
        return parameters.get(0).toString();
    }

    private void parseName(String commandStr) {
        scanner = new Scanner(commandStr);
        String name = scanner.next();
        try {
            this.name = CommandNames.valueOf(name);
        } catch (IllegalArgumentException e) {
            System.out.println("Unknown command name: " + name);
        }
    }

    void parse(String commandStr) {
        parseName(commandStr);

        while (scanner.hasNext()) {
            addParameter(scanner.next());
        }

//      Restart scanning so that subclasses can use the scanner.
        parseName(commandStr);
        doParse(commandStr);
    }

    public String serialize() {
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(name.toString());

        for (Object parameter : parameters) {
            joiner.add(parameter.toString());
        }
        return joiner.toString();
    }

    @Override
    public String toString() {
        return serialize();
    }

    public abstract void doParse(String commandStr);

    protected abstract void doExecute();
}
