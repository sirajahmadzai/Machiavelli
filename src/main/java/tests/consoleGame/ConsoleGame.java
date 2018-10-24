package tests.consoleGame;

import server.models.Machiavelli;
import server.models.Player;
import server.models.cards.Card;

import java.util.Scanner;

public class ConsoleGame {
    /**
     * PRIVATE STATICS
     */
    private static Machiavelli machiavelli;
    public static final int HAND_SIZE = 15;
    public static boolean won;
    private static Player currPlayer = null;
    private static Scanner scanner = null;

    /**
     * main method
     *
     * @param args
     */
    public static void main(String[] args) {

        int numOfPlayers = 0;
        scanner = new Scanner(System.in);

        System.out.println("Game starts!");
        do {
            System.out.print("Number of players (2-4): ");
            try {
                numOfPlayers = Integer.parseInt(scanner.nextLine());

                if (numOfPlayers < 2 || numOfPlayers > 4) {
                    System.out.println("Error number out of range!");
                }
            } catch (NumberFormatException e) {

                System.out.println("ERROR! Please enter a number 2-4!");
            }

        } while (numOfPlayers < 2 || numOfPlayers > 4);


        machiavelli = Machiavelli.getInstance();
        machiavelli.initialize(numOfPlayers);

        System.out.println("time to select a random player to start the game!");
        Player dealer = machiavelli.getRandomPlayer();
        machiavelli.dealHands(dealer);
        currPlayer = dealer;
        playGame();
    }

    /**
     * @param player
     */
    private static void displayHand(Player player) {
        for (Card card : player.getHand().getCards()) {
            System.out.print(card + " ");
        }
        System.out.println("(" + player.getHand().totalCount() + " cards)");
    }

    /**
     *
     */
    private static void playTurn() {
        displayHand(currPlayer);
        if (machiavelli.getTable().getDeck().size() == 0) {
            System.out.println("0. Play\n1. Skip");
            String choice = scanner.nextLine();
            if (choice.equals("1")) {
                //  nothing
            } else {
                //Play
            }
        }
        System.out.println("0. Play\n1. Draw");
        String choice = scanner.nextLine();
        if (choice.equals("1")) {
            try {
                machiavelli.drawCardFromDeck(currPlayer);
            } catch (Machiavelli.EmptyDeckException e) {
                System.out.println("Deck is empty!");
            }
        } else {
//            playCards(currPlayer, listOfCards);
        }
    }

    /**
     *
     */
    private static void playGame() {
        while (currPlayer.getHand().totalCount() != 0) {
            currPlayer = getNextPlayer(currPlayer);
            playTurn();
        }
    }


    /**
     * @param player
     * @return
     */
    private static Player getNextPlayer(Player player) {
        int indexOfNextPlayer = machiavelli.getPlayers().indexOf(player) + 1;

        if (indexOfNextPlayer == machiavelli.getPlayers().size()) {
            indexOfNextPlayer = 0;
        }
        return machiavelli.getPlayers().get(indexOfNextPlayer);
    }
}
