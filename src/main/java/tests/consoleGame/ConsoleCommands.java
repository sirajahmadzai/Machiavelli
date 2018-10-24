package tests.consoleGame;

import commands.BasicCommand;
import commands.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Simple class to test commands.
 */
public class ConsoleCommands {

    /**
     * main method
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        //create a new BufferedReader using System.in as input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("Enter a command as string: ");
            String cmdString = br.readLine();
            Command cmd = new BasicCommand(cmdString);

            System.out.println("Serialized command: " + cmd.serialize());
        }

    }
}
