package models;

import models.cards.Card;

import java.util.ArrayList;
import java.util.Random;

public class Machiavelli {


    private Table table;
    private ArrayList<Player> players;


    public Machiavelli(int numOfPlayers) {
        players = new ArrayList<>();
        table = new Table();

        for (int playerCounter = 0; playerCounter < numOfPlayers; playerCounter++) {
            players.add(new Player(numOfPlayers, "Player" + numOfPlayers + 1));
        }

        dealHands();

    }


    public void drawCardFromDeck(int playerID) {
        players.get(playerID).getHand().add(table.getDeck().remove(table.getDeck().size() - 1));
    }

    /**
     * remove card from the current player's hand then add it to the play area later on
     */
    public Card playCard(int playerID, int cardID) {
        return players.get(playerID).getHand().remove(cardID);
    }

    /**
     * how should cards be stored in the play area? can we use an ArrayList, that we we won't need to pass a Card parameter type
     *
     * @param playerID
     * @param setID
     * @return
     */
    public boolean playCardFromPlayArea(int playerID, int setID, int cardID) {
        return players.get(playerID).getHand().add(table.getSets().get(setID).getCards().remove(cardID));
    }

    public void mergeSet(Set set1, Set set2) {
        set1.getCards().addAll(set2.getCards());
    }

    public void splitSet(int pos) {

    }

    public Card splitSetRemove(int pos) {
        return null;
    }

    public void prependCard(Set set, Card card) {
        set.getCards().add(0, card);
    }

    public void appendCard(Set set, Card card) {
        set.getCards().add(card);

    }

    private void verifyTable() {

    }

    private void dealHands() {
        int currPlayerID = (new Random()).nextInt(players.size());

        for (int cardCounter = 0; cardCounter < 15; cardCounter++) {

            int playerCounter = 0;

            while (playerCounter < players.size()) {

                if (currPlayerID >= players.size()) {

                    currPlayerID = 0;
                }
                players.get(currPlayerID).getHand().add(table.getDeck().remove(table.getDeck().size() - 1));

                currPlayerID++;
                playerCounter++;
            }
        }
    }

    private void pickPlayerTurn() {
        int currPlayerID = (new Random()).nextInt(players.size());
    }


}