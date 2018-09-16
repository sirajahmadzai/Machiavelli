package commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.util.StringJoiner;


public class Command {
    public CommandNames getName() {
        return name;
    }

    public void execute() {
        System.out.println("Command executing itself.");
    }

    public enum CommandNames {
        NUMBER_OF_PLAYERS,
        SHOW_GAMEVIEW,
        DEAL_HANDS,
        INTRODUCE_PLAYER,
        TABLE_IS_FULL,
        ADD_CARD_TO_HAND,
        DRAW_CARD_FROM_DECK,
        ADD_CARD_TO_SET,
        REMOVE_CARD_FROM_HAND,
        REMOVE_CARD_FROM_SET,
        DECK_CLICKED,
        PROMPT_PLAYER,
        PROMPT_PLAY_CARD,
        PROMPT_END_TURN,
    }

//    private BufferedReader in;
//    private PrintWriter out;

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
