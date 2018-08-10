package models;

import models.cards.Card;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

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
     * removeCard card from the current player's hand then add it to the play area later on
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
    boolean playCardFromPlayArea(int indexOfPlayer, int indexOfSet, int indexOfCard) {
        return players.get(indexOfPlayer).getHand().add(table.getCardSets().get(indexOfSet).getCards().remove(indexOfCard));
    }

    /**
     * merges two sets
     *
     * @param setToKeep
     * @param setToAppend
     */
    void mergeSetAppend(CardSet setToKeep, CardSet setToAppend) {
        setToKeep.getCards().addAll(setToAppend.getCards());
        table.getCardSets().remove(setToAppend);
    }

    /**
     * @param cardSet
     * @param i
     * @return
     */
    void splitSet(CardSet cardSet, int i) {

        ArrayList<Card> list1 = new ArrayList<>();
        ArrayList<Card> list2 = new ArrayList<>();
        CardSet result1 = new CardSet(list1);
        CardSet result2 = new CardSet(list2);

        AtomicInteger count = new AtomicInteger();
        for (Card next : cardSet.getCards()) {
            int index = count.getAndIncrement();
            if (index < i) {
                result1.getCards().add(next);
            } else {
                result2.getCards().add(next);
            }
        }
        ArrayList<CardSet> listOfCardSets = new ArrayList<>();
        listOfCardSets.add(result1);
        listOfCardSets.add(result2);
        table.setCardSets(listOfCardSets);
    }

    /**
     * @param cardSet
     * @param i
     */
    void removeCard(CardSet cardSet, int i) {
        ArrayList<Card> list1 = new ArrayList<>();
        ArrayList<Card> list2 = new ArrayList<>();
        CardSet result1 = new CardSet(list1);
        CardSet result2 = new CardSet(list2);

        AtomicInteger count = new AtomicInteger();
        for (Card next : cardSet.getCards()) {
            int index = count.getAndIncrement();
            if (index < i) {
                result1.getCards().add(next);
            } else {
                result2.getCards().add(next);
            }
        }
        ArrayList<CardSet> listOfCardSets = new ArrayList<>();
        listOfCardSets.add(result1);
        listOfCardSets.add(result2);
        table.setCardSets(listOfCardSets);

        table.getCardsInPlay().add(cardSet.getCards().remove(i));
    }

    /**
     * add a card to the front
     *
     * @param cardSet
     * @param card
     */
    void prependCard(CardSet cardSet, Card card) {
        cardSet.getCards().add(0, card);
    }

    /**
     * add a card to the back
     *
     * @param cardSet
     * @param card
     */
    void appendCard(CardSet cardSet, Card card) {
        cardSet.getCards().add(card);

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