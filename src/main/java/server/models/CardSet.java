package server.models;

import server.models.cards.Ace;
import server.models.cards.Card;
import server.models.cards.Suit;

import java.util.*;

public class CardSet {

    /********************************
     ******** PRIVATES **************
     ********************************/
    private ArrayList<Card> cards;
    private ArrayList<Card> jokers;
    private Map<Suit, ArrayList<Card>> suitCardMap = new HashMap<>();
    private Map<Integer, ArrayList<Card>> rankCardMap = new HashMap<>();
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
        this.jokers = new ArrayList<>();
    }

    public void addCard(Card card) {
        isSorted = false;

        if (card.isJoker()) {
            jokers.add(card);
            return;
        }

        // Update suit map
        ArrayList<Card> suit = suitCardMap.getOrDefault(card.getSuit(), new ArrayList<>());
        suit.add(card);
        suitCardMap.put(card.getSuit(), suit);

        // Update rank map
        ArrayList<Card> rank = rankCardMap.getOrDefault(card.getRank(), new ArrayList<>());
        rank.add(card);
        rankCardMap.put(card.getRank(), rank);

        cards.add(card);
    }


    public void removeCard(Card card) {
        if (jokers.remove(card)) {
            return;
        }

        ArrayList<Card> suit = suitCardMap.get(card.getSuit());
        suit.remove(card);
        if (suit.isEmpty()) {
            suitCardMap.remove(card.getSuit());
        }

        ArrayList<Card> rank = rankCardMap.get(card.getRank());
        rank.remove(card);
        if (rank.isEmpty()) {
            rankCardMap.remove(card.getRank());
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
        if (totalCount() <= 1) {
            return true;
        }

        // a valid meld can have max 14 cards.
        if (totalCount() > 14) {
            return false;
        }

        if (jokerCount() > 1) {
            return false;
        }

        return (isASet() || isAStraight());
    }

    public boolean isAStraight() {
        sort();

        // In a straight all cards should be of same suit.
        if (suitCardMap.size() > 1) {
            return false;
        }

        // In a straight all cards should be of different rank.
        if (rankCardMap.size() != cardCount()) {
            return false;
        }

        int rankDifference = getMinRankDifference();

        int missingCardsForAStraight = rankDifference - (cardCount() - 1);
        if (missingCardsForAStraight > jokerCount()) {
            return false;
        }

        return true;
    }

    public boolean isASet() {
        sort();

        // A suit may contain maximum 4 cards.
        if (totalCount() > 4) {
            return false;
        }

        // In a set all cards should have different suits.
        if (suitCardMap.size() != cardCount()) {
            return false;
        }

        // In a set all cards should be of same rank.
        if (rankCardMap.size() > 1) {
            return false;
        }

        return true;
    }

    public void sort() {
        if (isSorted) {
            return;
        }
        Collections.sort(cards);
        isSorted = true;
    }

    public boolean canAcceptCard(Card card) {
        if (card.isJoker()) {
            if (totalCount() < 4) {
                return true;
            }

            if (totalCount() == 14) {
                return false;
            }

            // This set doesn't have any more room.
            if (isASet()) {
                return false;
            }

            return true;
        }

        ArrayList<Card> allCards = new ArrayList<>();
        allCards.addAll(cards);
        allCards.addAll(jokers);

        CardSet proposedCardSet = new CardSet(allCards);

        proposedCardSet.addCard(card);
        return proposedCardSet.isAValidMeld();
    }

    public int totalCount() {
        return cardCount() + jokerCount();
    }

    public int cardCount() {
        return cards.size();
    }

    public int jokerCount() {
        return jokers.size();
    }

    private int getMinRankDifference(){
        int minRank = cards.get(0).getRank();
        int maxRank = cards.get(cardCount() - 1).getRank();
        int rankDifference = maxRank - minRank;

        if(cards.get(0) instanceof Ace){
            minRank = cards.get(1).getRank();
            maxRank = 14;
            int rankDifference2 = maxRank - minRank;
            if(rankDifference > rankDifference2){
                rankDifference = rankDifference2;
            }
        }

        return rankDifference;
    }
}
