package commands;

import javafx.application.Platform;
import javafx.util.Builder;

import java.util.Scanner;
import java.util.Stack;
import java.util.StringJoiner;

import static commands.Command.CommandTypes.CLIENT_COMMAND;
import static commands.Command.CommandTypes.SERVER_COMMAND;


public class Command {
    public CommandNames getName() {
        return name;
    }

    public void execute() {
        Platform.runLater( () -> doExecute() );
    }

    protected void doExecute(){
        System.out.println("Command executing itself.");
    }

    public enum CommandTypes{
        SERVER_COMMAND,
        CLIENT_COMMAND
    }
    public enum CommandNames{
//      SERVER_COMMANDS
        NUMBER_OF_PLAYERS(SERVER_COMMAND),
        SHOW_GAMEVIEW(SERVER_COMMAND),
        DEAL_HANDS(SERVER_COMMAND),
        PLAYER_MOVE(SERVER_COMMAND),
        PASS_TURN(SERVER_COMMAND),

//      CLIENT_COMMANDS
        INTRODUCE_PLAYER(CLIENT_COMMAND),
        WELCOME(CLIENT_COMMAND),
        TABLE_IS_FULL(CLIENT_COMMAND),
        SWITCH_TURN(CLIENT_COMMAND)
        ;

        private final CommandTypes type;

        CommandNames(CommandTypes type){
            this.type = type;
        }

        public CommandTypes getType() {
            return type;
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

    public Command addParameter(Object param) {
        parameters.add(param);
        return this;
    }
    public Stack<Object> getParameters(){
        return parameters;
    }
    public String getParameter(){
        return parameters.get(0).toString();
    }

    protected void parseName(String commandStr) {
        scanner = new Scanner(commandStr);
        String name = scanner.next();
        try {
            this.name = CommandNames.valueOf(name);
        } catch (IllegalArgumentException e) {
            System.out.println("Unknown command name: " + name);
        }
    }

    public void parse(String commandStr) {
        parseName(commandStr);

        while (scanner.hasNext()) {
            addParameter(scanner.next());
        }

//        for (CommandParameter parameter : parameters) {
//            parameter.read(scanner);
//                if(parameter instanceof String){
//                    parameter = scanner.next();
//                }else if(parameter instanceof Integer){
//                    parameter = scanner.nextInt();
//                }
//        }

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
}
