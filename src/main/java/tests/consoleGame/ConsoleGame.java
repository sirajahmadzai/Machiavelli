package tests.consoleGame;

import models.CardSet;
import models.Machiavelli;
import models.Player;
import models.Table;
import models.cards.Card;

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
                else {

                    Machiavelli machiavelli = new Machiavelli(numOfPlayers);
                    System.out.println("time to select a random player to start the game!");
                    Player randomPlayer = machiavelli.getRandomPlayer();
                    System.out.println("random player is Player" + randomPlayer.getPlayerID());



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
//                        for (Card card : player.getHand()) {
//                            System.out.println("card" + cardCounter);
//                            System.out.println("card" + cardCounter + "'s ID: " + card.getId());
//                            System.out.println("card" + cardCounter + "'s rank: " + card.getRank());
//                            System.out.println("card" + cardCounter + "'s Suit: " + card.getSuit());
//                            cardCounter++;
//
//                        }
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
            } catch (NumberFormatException e) {

                System.out.println("ERROR! Please enter a number 2-4!");
            }

        } while (numOfPlayers >= 2 && numOfPlayers <= 4);


        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
