package server.models;

import server.models.cards.Card;

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
    public Table getTable() {
        return table;
    }

    /**
     * @return
     */
    public ArrayList<Player> getPlayers() {
        return players;
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

    /***************************************
     *************** SETTERS **************
     **************************************/

    /**
     * @param table
     */
    public void setTable(Table table) {
        this.table = table;
    }

    /**
     * @param players
     */
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }


    /***************************************
     *************** PUBLIC MODIFIERS **************
     **************************************/

    /**
     * @param player
     */
    public void drawCardFromDeck(Player player) {
        player.getHand().add(table.getDeck().remove(table.getDeck().size() - 1));
    }

    /**
     * removeCard card from the current player's hand then add it to the play area later on
     *
     * @param indexOfPlayer
     * @param indexOfCard
     * @return
     */
    public Card removeCardFromHand(int indexOfPlayer, int indexOfCard) {
        return players.get(indexOfPlayer).getHand().remove(indexOfCard);
    }

    /**
     * merges two sets
     *
     * @param setToKeep
     * @param setToAppend
     */
    public void mergeSetAppend(CardSet setToKeep, CardSet setToAppend) {
        setToKeep.getCards().addAll(setToAppend.getCards());
        table.getCardSets().remove(setToAppend);
    }

    /**
     * @param cardSet
     * @param i
     * @return
     */
    public void splitSet(CardSet cardSet, int i) {

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
    public void removeCard(CardSet cardSet, int i) {
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
    public void prependCard(CardSet cardSet, Card card) {
        cardSet.getCards().add(0, card);
    }

    /**
     * add a card to the back
     *
     * @param cardSet
     * @param card
     */
    public void appendCard(CardSet cardSet, Card card) {
        cardSet.getCards().add(card);

    }

    /***************************************
     *************** PRIVATE HELPERS **************
     **************************************/
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
}