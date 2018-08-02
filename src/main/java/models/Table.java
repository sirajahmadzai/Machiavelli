package models;

import com.sun.javaws.exceptions.InvalidArgumentException;
import models.cards.Basic;
import models.cards.Card;
import models.cards.Joker;
import models.cards.Suit;

import java.util.ArrayList;
import java.util.Collections;

public class Table {

    /********************************
     ******** PRIVATES **************
     ********************************/
    private ArrayList<Card> deck;
    private ArrayList<Set> sets;

    /**
     * cards that have been taken from a set that have to be played
     */
    private ArrayList<Card> cardsInPlay;

    /**
     * CONSTRUCTOR
     */
    public Table() {
        deck = new ArrayList<>();
        sets = new ArrayList<>();
        cardsInPlay = new ArrayList<>();
        initMachiavelliDeck();
    }


    /**
     * gets deck
     *
     * @return
     */
    public ArrayList<Card> getDeck() {
        return deck;
    }

    /**
     * gets sets
     *
     * @return
     */
    public ArrayList<Set> getSets() {
        return sets;
    }

    /**
     * gets cardsInPlay
     *
     * @return
     */
    public ArrayList<Card> getCardsInPlay() {
        return cardsInPlay;
    }

    public void setDeck(ArrayList<Card> deck) {
        this.deck = deck;
    }

    public void setSets(ArrayList<Set> sets) {
        this.sets = sets;
    }

    public void setCardsInPlay(ArrayList<Card> cardsInPlay) {
        this.cardsInPlay = cardsInPlay;
    }

    /**
     * creates a deck from two standard decks
     */
    private void initMachiavelliDeck() {
        //2 heart of 2s
        //2 spade of 2s
        //2 diamon of 2s
        //and so on
        deck = new ArrayList<>();
        deck.addAll(generateStandardDeck());
        deck.addAll(generateStandardDeck());
        shuffleDeck();
    }

    /**
     * generates a standard deck
     *
     * @return
     */
    private ArrayList<Card> generateStandardDeck() {
        ArrayList<Card> standardDeck = new ArrayList<>();
        for (int rank = 1; rank <= 14; rank++) {
            try {
                standardDeck.add(new Basic(Suit.CLUBS, getCardValueByRank(rank), getCardNameByRank(rank), rank));
                standardDeck.add(new Basic(Suit.DIAMONDS, getCardValueByRank(rank), getCardNameByRank(rank), rank));
                standardDeck.add(new Basic(Suit.HEARTS, getCardValueByRank(rank), getCardNameByRank(rank), rank));
                standardDeck.add(new Basic(Suit.SPADES, getCardValueByRank(rank), getCardNameByRank(rank), rank));
            } catch (InvalidArgumentException e) {
                e.printStackTrace();
            }
        }

        try {
            standardDeck.add(new Joker(Suit.JOKER, getCardValueByRank(15), "Joker", 15));
            standardDeck.add(new Joker(Suit.JOKER, getCardValueByRank(15), "Joker", 15));
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
        return standardDeck;
    }

    /**
     * Shuffles the deck
     */
    public void shuffleDeck() {
        Collections.shuffle(deck);
    }

    /**
     * gets cardName by rank
     *
     * @param rank
     * @return
     * @throws InvalidArgumentException
     */
    public String getCardNameByRank(int rank) throws InvalidArgumentException {
        if (rank >= 2 && rank <= 10) return String.valueOf(rank);
        if (rank == 11) return "Jack";
        else if (rank == 12) return "Queen";
        else if (rank == 13) return "King";
        else if (rank == 1 || rank == 14) return "Ace";
        else throw new InvalidArgumentException(new String[]{"invalid rank"});
    }

    /**
     * gets cardValue by rank
     *
     * @param rank
     * @return
     * @throws InvalidArgumentException
     */
    public int getCardValueByRank(int rank) throws InvalidArgumentException {
        if (rank >= 2 && rank <= 10) return rank;
        else if (rank > 10 && rank < 14) return 10;
        else if (rank == 1 || rank == 14) return 15;
        else if (rank == 15) return 20;
        else throw new InvalidArgumentException(new String[]{"Invalid rank"});
    }

    public boolean decksDifferent(Table table1, Table tabl2) {
        for (int j = 0; j < 104; j++) {

            if (!table1.getCard(j).equals(tabl2.getCard(j))) return true;

        }

        return false;
    }

    public Card getCard(int j) {
        return deck.get(j);
    }


}
