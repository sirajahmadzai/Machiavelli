package server.models;

import server.models.cards.Card;

import java.util.ArrayList;
import java.util.Collection;

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
    public CardSet(Collection<Card> cards) {
        this.cards = new ArrayList<>(cards);
    }

    public CardSet(Card card) {
        this();
        cards.add(card);
    }

    public CardSet() {
        this.cards = new ArrayList<>();
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
