package models;

import models.cards.Card;

import java.util.ArrayList;

public class CardSet {

    /********************************
     ******** PRIVATES **************
     ********************************/
    private ArrayList<Card> cards;


    /**
     * CONSTRUCTOR
     *
     * @param cards
     */
    CardSet(ArrayList<Card> cards) {

        this.cards = cards;
    }


    /**
     * gets list of cards
     *
     * @return
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

}
