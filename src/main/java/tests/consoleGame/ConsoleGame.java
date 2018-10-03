package tests.consoleGame;

import server.models.Machiavelli;
import server.models.Player;
import server.models.cards.Card;

import java.util.Scanner;

public class ConsoleGame {
    private static Machiavelli machiavelli;
    public static final int HAND_SIZE = 15;
    public static boolean won;
    private static Player currPlayer = null;
    private static Scanner scanner = null;

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


//                    System.out.println("random player is Player" + dealer.getPlayerID());
//                    for (Card card : dealer.getHand()) {
//                        System.out.println(card.getSuit());
//                        System.out.println(card.getRank());
//                    }


        /**********************
         * COMMENTED OUT RANDOM PRINTS OF GAME PARTS
         */

//                    Machiavelli game = new Machiavelli(numOfPlayers);
//                    System.out.println("Print players with specifications");
//                    int playerCounter = 1;
//                    for (Player player : game.getPlayers()) {
//                        System.out.println("player" + playerCounter + "'s playerID: " + player.getPlayerID());
//                        System.out.println("player" + playerCounter + "'s name: " + player.getName());
//                        System.out.println("player" + playerCounter + "'s pointValue: " + player.getPointValue());
//                        System.out.println("player" + playerCounter + "'s hand: ");
//                        playerCounter++;
//                        int cardCounter = 1;
//
//                    }
//
//
//                    Player currPlayer = game.getRandomPlayer();
//                    System.out.println("current player is: " + currPlayer.getName());
//                    System.out.println("currPlayer hand is:");
//                    int currPlayerCardCounter = 1;
//                    for (Card card : currPlayer.getHand()) {
//                        System.out.println("card" + currPlayerCardCounter);
//                        System.out.println(currPlayer.getHand().get(currPlayer.getHand().indexOf(card)).getSuit());
//                        System.out.println(currPlayer.getHand().get(currPlayer.getHand().indexOf(card)).getRank());
//                        System.out.println(currPlayer.getHand().get(currPlayer.getHand().indexOf(card)).getId());
//                        currPlayerCardCounter++;
//                    }
//
//                    Table table = game.getTable();
//                    int cardSetCounter = 1;
//                    int cardSetCardCounter = 1;
//                    for (CardSet cardSet : table.getCardSets()) {
//                        System.out.println("cardSet" + cardSetCounter + "'s list of cards");
//                        for (Card card : cardSet.getCards()) {
//                            System.out.println("card" + cardSetCardCounter + "'s Suit" + card.getSuit());
//                            System.out.println("card" + cardSetCardCounter + "'s rank" + card.getRank());
//                            System.out.println("card" + cardSetCardCounter + "'s ID" + card.getId());
//                            cardSetCardCounter++;
//                        }
//                        cardSetCounter++;
//                    }
//                    int deckCardCounter = 1;
//                    for (Card card : table.getDeck()) {
//                        System.out.println("List of cards in table's deck");
//                        System.out.println("card" + deckCardCounter + "'s Suit" + card.getSuit());
//                        System.out.println("card" + deckCardCounter + "'s rank" + card.getRank());
//                        System.out.println("card" + deckCardCounter + "'s ID" + card.getId());
//                        deckCardCounter++;
//                    }
//
//                    int cardsInPlayCardCounter = 1;
//                    for (Card card : table.getCardsInPlay()) {
//                        System.out.println("List of cards in play");
//                        System.out.println("card" + cardsInPlayCardCounter + "'s Suit" + card.getSuit());
//                        System.out.println("card" + cardsInPlayCardCounter + "'s rank" + card.getRank());
//                        System.out.println("card" + cardsInPlayCardCounter + "'s ID" + card.getId());
//                        currPlayerCardCounter++;
//                    }

    }

    private static void displayHand(Player player) {
        for (Card card : player.getHand().getCards()) {
            System.out.print(card + " ");
        }
        System.out.println("(" + player.getHand().totalCount() + " cards)");
    }

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

    private static void playGame() {
        while (currPlayer.getHand().totalCount() != 0) {
            currPlayer = getNextPlayer(currPlayer);
            playTurn();
        }
    }


    private static Player getNextPlayer(Player player) {
        int indexOfNextPlayer = machiavelli.getPlayers().indexOf(player) + 1;

        if (indexOfNextPlayer == machiavelli.getPlayers().size()) {
            indexOfNextPlayer = 0;
        }
        return machiavelli.getPlayers().get(indexOfNextPlayer);
    }
}
