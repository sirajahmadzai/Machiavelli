package models;

import models.cards.Card;

import java.util.ArrayList;
import java.util.List;
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
    Table getTable() {
        return table;
    }

    /**
     * @return
     */
    ArrayList<Player> getPlayers() {
        return players;
    }

    void setTable(Table table) {
        this.table = table;
    }

    void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }


    /***************************************
     *************** HELPERS **************
     **************************************/
    /**
     * @return
     */
    Card drawCardFromDeck() {
        return table.getDeck().remove(table.getDeck().size() - 1);
    }

    /**
     * remove card from the current player's hand then add it to the play area later on
     */
    Card playCard(int indexOfPlayer, int indexOfCard) {
        return players.get(indexOfPlayer).getHand().remove(indexOfCard);
    }

    //TODO : how should cards be stored in the play area? can we use an ArrayList, that we we won't need to pass a Card parameter type

    /**
     * current player takes a card fro mthe playArea
     *
     * @param indexOfPlayer
     * @param indexOfSet
     * @return
     */
    public boolean playCardFromPlayArea(int indexOfPlayer, int indexOfSet, int indexOfCard) {
        return players.get(indexOfPlayer).getHand().add(table.getSets().get(indexOfSet).getCards().remove(indexOfCard));
    }

    /**
     * merges two sets
     *
     * @param set1
     * @param set2
     */
    public boolean mergeSet(Set set1, Set set2) {
        return set1.getCards().addAll(set2.getCards());
    }

    /**
     * @param set
     * @param from
     * @param to
     */
    Set splitSet(Set set, int from, int to) {
        List<Card> subList = set.getCards().subList(from, to);

        return new Set(new ArrayList<>(subList));
    }

    /**
     * @param set
     * @param from
     * @param to
     * @param cardToRemove
     * @return
     */
    Set splitSetRemove(Set set, int from, int to, int cardToRemove) {
        set.getCards().remove(cardToRemove);
        List<Card> subList = set.getCards().subList(from, to);

        return new Set(new ArrayList<>(subList));
    }

    /**
     * add a card to the front
     *
     * @param set
     * @param card
     */
    void prependCard(Set set, Card card) {
        set.getCards().add(0, card);
    }

    /**
     * add a card to the back
     *
     * @param set
     * @param card
     */
    void appendCard(Set set, Card card) {
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