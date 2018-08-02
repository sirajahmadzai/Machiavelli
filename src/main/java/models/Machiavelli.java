package models;

import models.cards.Card;

import java.util.ArrayList;
import java.util.Random;

public class Machiavelli {


    /********************************
     ******** PRIVATES **************
     ********************************/
    private Table table;
    private ArrayList<Player> players;


    /**
     * CONSTRUCTOR
     *
     * @param numOfPlayers
     */
    public Machiavelli(int numOfPlayers) {
        players = new ArrayList<>();
        table = new Table();

        for (int playerCounter = 0; playerCounter < numOfPlayers; playerCounter++) {
            players.add(new Player(numOfPlayers, "Player" + numOfPlayers + 1));
        }

        dealHands();

    }

    /***************************************
     *************** GETTERS **************
     **************************************/
    /**
     * gets table
     *
     * @return
     */
    public Table getTable() {
        return table;
    }

    /**
     * @return
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }


    /***************************************
     *************** HELPERS **************
     **************************************/
    /**
     * @return
     */
    public Card drawCardFromDeck() {
        return table.getDeck().remove(table.getDeck().size() - 1);
    }

    /**
     * remove card from the current player's hand then add it to the play area later on
     */
    public Card playCard(int playerID, int cardID) {
        return players.get(playerID).getHand().remove(cardID);
    }

    //TODO : how should cards be stored in the play area? can we use an ArrayList, that we we won't need to pass a Card parameter type

    /**
     * current player takes a card fro mthe playArea
     *
     * @param playerID
     * @param setID
     * @return
     */
    public boolean playCardFromPlayArea(int playerID, int setID, int cardID) {
        return players.get(playerID).getHand().add(table.getSets().get(setID).getCards().remove(cardID));
    }

    /**
     * merges two sets
     *
     * @param set1
     * @param set2
     */
    public void mergeSet(Set set1, Set set2) {
        set1.getCards().addAll(set2.getCards());
    }

    /**
     * splits a set into two sets
     *
     * @param set
     * @param pos
     */
    public void splitSet(Set set, int pos) {
        set.getCards().remove(pos);
    }

    /**
     * splits a set into two and removes the card at the splitting index
     *
     * @param set
     * @param pos
     * @return
     */
    public Card splitSetRemove(Set set, int pos) {
        return set.getCards().remove(pos);
    }

    /**
     * add a card to the front
     *
     * @param set
     * @param card
     */
    public void prependCard(Set set, Card card) {
        set.getCards().add(0, card);
    }

    /**
     * add a card to the back
     *
     * @param set
     * @param card
     */
    public void appendCard(Set set, Card card) {
        set.getCards().add(card);

    }

    /**
     *
     */
    private void verifyTable() {

    }

    /**
     * deals fifteen random cards per player
     */
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

    /**
     * gets a random player from players ArrayList
     *
     * @return
     */
    public Player getRandomPlayer() {
        int currPlayerID = (new Random()).nextInt(players.size());

        return players.get(currPlayerID);
    }
}