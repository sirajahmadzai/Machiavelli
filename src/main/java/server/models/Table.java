package server.models;

import com.sun.javaws.exceptions.InvalidArgumentException;
import server.models.cards.*;

import java.util.ArrayList;
import java.util.Collections;

public class Table {

    /********************************
     ******** PRIVATES **************
     ********************************/
    private ArrayList<Card> deck;
    private ArrayList<CardSet> cardSets;

    /**
     * cards that have been taken from a set that have to be played
     */
    private ArrayList<Card> cardsInPlay;

    /**
     * CONSTRUCTOR
     */
    public Table() {
        deck = new ArrayList<>();
        cardSets = new ArrayList<>();
        cardsInPlay = new ArrayList<>();
        initMachiavelliDeck();
    }


    /*******************************************
     **************GETTERS*********************
     ******************************************/
    /**
     * gets deck
     *
     * @return
     */
    public ArrayList<Card> getDeck() {
        return deck;
    }

    /**
     * gets cardSets
     *
     * @return
     */
    public ArrayList<CardSet> getCardSets() {
        return cardSets;
    }

    /**
     * gets cardsInPlay
     *
     * @return
     */
    public ArrayList<Card> getCardsInPlay() {
        return cardsInPlay;
    }

    /*******************************************
     ******************SETTERS******************
     ******************************************/

    /**
     * @param deck
     */
    public void setDeck(ArrayList<Card> deck) {
        this.deck = deck;
    }

    /**
     * @param cardSets
     */
    public void setCardSets(ArrayList<CardSet> cardSets) {
        this.cardSets = cardSets;
    }

    /**
     * @param cardsInPlay
     */
    public void setCardsInPlay(ArrayList<Card> cardsInPlay) {
        this.cardsInPlay = cardsInPlay;
    }

    /***********************************************
     ****************PUBLIC MODIFIERS***************
     ***********************************************/
    /**
     * Shuffles the deck
     */
    public void shuffleDeck() {
        Collections.shuffle(deck);
    }


    /***************************************************
     * *************PRIVATE HELPERS*********************
     ***************************************************/
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
        int cardID = 0;

        try {
            int rank = 1;
            standardDeck.add(new Ace(Suit.CLUBS, rank, cardID));
            cardID++;
            standardDeck.add(new Ace(Suit.DIAMONDS, rank, cardID));
            cardID++;
            standardDeck.add(new Ace(Suit.HEARTS, rank, cardID));
            cardID++;
            standardDeck.add(new Ace(Suit.SPADES, rank, cardID));
            cardID++;
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
        for (int rank = 2; rank <= 14; rank++) {
            try {
                standardDeck.add(new Basic(Suit.CLUBS, rank, cardID));
                cardID++;
                standardDeck.add(new Basic(Suit.DIAMONDS, rank, cardID));
                cardID++;
                standardDeck.add(new Basic(Suit.HEARTS, rank, cardID));
                cardID++;
                standardDeck.add(new Basic(Suit.SPADES, rank, cardID));
                cardID++;
            } catch (InvalidArgumentException e) {
                e.printStackTrace();
            }
        }


        try {
            int rank = 15;
            standardDeck.add(new Joker(Suit.JOKER, rank, cardID));
            cardID++;
            standardDeck.add(new Joker(Suit.JOKER, rank, cardID));
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
        return standardDeck;
    }

}
