package server.models;

import server.models.cards.Card;
import server.models.cards.Suit;

import java.util.*;

public class CardSet {

    /********************************
     ******** PRIVATES **************
     ********************************/
    private ArrayList<Card> cards;
    private Map<Suit, ArrayList<Card>> suitCardMap = new HashMap<>();
    private boolean isSorted = true;

    /**
     * CONSTRUCTOR
     *
     * @param cards
     */
    public CardSet(Collection<Card> cards) {
        this();
        for (Card card : cards) {
            this.addCard(card);
        }
    }

    public CardSet(Card card) {
        this();
        this.addCard(card);
    }

    public CardSet() {
        this.cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);

        ArrayList<Card> suit;
        if (!suitCardMap.containsKey(card.getSuit())) {
            suit = new ArrayList<>();
            suitCardMap.put(card.getSuit(), suit);
        } else {
            suit = suitCardMap.get(card.getSuit());
        }

        suit.add(card);
        isSorted = false;
    }

    public void removeCard(Card card) {
        ArrayList<Card> suit = suitCardMap.get(card.getSuit());
        suit.remove(card);
        if (suit.isEmpty()) {
            suitCardMap.remove(card.getSuit());
        }
        cards.remove(card);
    }


    /**
     * gets list of cards
     *
     * @return
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    public boolean isAValidMeld() {
        return (isASet() || isAStraight());
    }

    public boolean isAStraight() {
        Card prevCard = null;
        boolean isStraight = true;
        // is a straight
        for (Card card : cards) {
            if (prevCard != null) {
                if (1 != card.getRank() - prevCard.getRank()) {
                    isStraight = false;
                    break;
                }
                if (card.getSuit() != prevCard.getSuit()) {
                    isStraight = false;
                    break;
                }
            }
            prevCard = card;
        }

        return isStraight;
    }

    public boolean isASet() {
        sort();
        // In a set all cards should have different suits.
        if (suitCardMap.size() != cards.size()) {
            return false;
        }

        // In a set all cards should be of same rank.
        Card prevCard = null;
        boolean isSet = true;
        for (Card card : cards) {
            if (prevCard != null) {
                if (card.getRank() != prevCard.getRank()) {
                    isSet = false;
                    break;
                }
            }
            prevCard = card;
        }
        return isSet;
    }

    private void sort() {
        if (isSorted) {
            return;
        }
        Collections.sort(cards);
        isSorted = true;
    }

}
