package models;

import models.cards.Card;

import java.util.ArrayList;

public class Set {

    /********************************
     ******** PRIVATES **************
     ********************************/
    private ArrayList<Card> cards;


    /**
     * CONSTRUCTOR
     *
     * @param cards
     */
    public Set(ArrayList<Card> cards) {

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
