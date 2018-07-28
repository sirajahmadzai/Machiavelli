package tests.consoleGame;

import models.Machiavelli;

import java.util.Scanner;

public class ConsoleGame {
    public static void main(String[] args) {

        int numOfPlayers = 0;
        Scanner keyboard = new Scanner(System.in);

        System.out.println("Game starts!");
        do {
            System.out.print("Number of players (2-4): ");
            try {
                numOfPlayers = Integer.parseInt(keyboard.nextLine());

                if (numOfPlayers < 2 || numOfPlayers > 4) {
                    System.out.println("Error number out of range!");
                }
            } catch (NumberFormatException e) {

                System.out.println("ERROR! Please enter a number 2-4!");
            }

        } while (numOfPlayers >= 2 && numOfPlayers <= 4);


        try {
            Machiavelli game = new Machiavelli(numOfPlayers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
