package tests.consoleGame;

import commands.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//Simple class to test commands.
public class ConsoleCommands {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("Enter a command as string: ");
            String cmdString = br.readLine();
            Command cmd = new Command(cmdString);

            System.out.println("Serialized command: " + cmd.serialize());
        }

    }

//    public static void main(String[] args) {
//        BufferedReader in;
//        PrintWriter out;
//        Socket socket;
//        try {
//            socket = new Socket("localhost", 9876);
//            in = new BufferedReader(new InputStreamReader(
//                    socket.getInputStream()));
//            out = new PrintWriter(socket.getOutputStream(), true);


//        } catch (IOException e) {
//            e.printStackTrace();
//        }


//    }
}
