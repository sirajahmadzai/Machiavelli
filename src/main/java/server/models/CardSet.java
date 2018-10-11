package server.models;

import com.sun.javaws.exceptions.InvalidArgumentException;
import server.models.cards.Ace;
import server.models.cards.Card;
import server.models.cards.Suit;

import java.util.*;

public class CardSet {

    private ArrayList<Card> allCards;
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
        addCards(cards);
    }

    public CardSet(Card card) {
        this();
        this.addCard(card);
    }

    public CardSet(String setString) {
        this();
        String[] cards = setString.split(",");

        try {
            for (String c : cards) {
                this.addCard(Card.fromString(c));
            }
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
    }

    public CardSet() {
        this.cards = new ArrayList<>();
        this.allCards = new ArrayList<>();
        this.jokers = new ArrayList<>();
    }

    public void addCard(Card card) {
        isSorted = false;
        allCards.add(card);
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

    public void join(CardSet cardSet) {
        addCards(cardSet.allCards);
    }

    public void addCards(Collection<Card> cards) {
        for (Card card : cards) {
            this.addCard(card);
        }
    }

    public boolean removeCard(Card card) {
        allCards.remove(card);

        if (jokers.remove(card)) {
            return true;
        }

        if (!cards.remove(card)) {
            return false;
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
        return true;
    }

    /**
     * gets list of cards
     *
     * @return
     */
    public ArrayList<Card> getCards() {
        return allCards;
    }

    public boolean isAValidMeld() {
        return isAValidMeld(1);
    }

    public boolean isAValidMeld(int minSetSize) {
        // An empty set is always a valid meld regardless of minimum set size.
        if (totalCount() == 0) {
            return true;
        }

        //Don't bother if set is not big enough.
        if (totalCount() < minSetSize) {
            return false;
        }

        //A single card is always a valid meld.
        if (totalCount() == 1) {
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
        Collections.sort(allCards);
        Collections.sort(cards);
        isSorted = true;
    }

    public boolean canAcceptCards(CardSet cardSet) {
        CardSet proposedCardSet = new CardSet(allCards);

        proposedCardSet.join(cardSet);
        return proposedCardSet.isAValidMeld();
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

        CardSet proposedCardSet = new CardSet(allCards);

        proposedCardSet.addCard(card);
        return proposedCardSet.isAValidMeld();
    }

    public int totalCount() {
        return allCards.size();
    }

    private int cardCount() {
        return cards.size();
    }

    private int jokerCount() {
        return jokers.size();
    }

    private int getMinRankDifference() {
        int minRank = cards.get(0).getRank();
        int maxRank = cards.get(cardCount() - 1).getRank();
        int rankDifference = maxRank - minRank;

        if (cards.get(0) instanceof Ace) {
            minRank = cards.get(1).getRank();
            maxRank = 14;
            int rankDifference2 = maxRank - minRank;
            if (rankDifference > rankDifference2) {
                rankDifference = rankDifference2;
            }
        }

        return rankDifference;
    }

    public CardSet getSnapshot() {
        return new CardSet(allCards);
    }

    // 2 Sets consisting of the same cards (rank,suit) considered equal regardless of the order of the cards.
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof CardSet))
            return false;

        CardSet set = (CardSet) obj;
        if (set.totalCount() != this.totalCount()) {
            return false;
        }

        if (set.jokerCount() != this.jokerCount()) {
            return false;
        }

        set.sort();
        sort();
        return set.allCards.equals(allCards);
    }

    @Override
    public int hashCode() {
        sort();
        return allCards.hashCode();
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(",");
        for (Card card : allCards) {
            joiner.add(card.toString());
        }
        return joiner.toString();
    }


    public boolean superSetOf(CardSet set) {
        CardSet subSet = new CardSet(set.allCards);
        CardSet superSet = new CardSet(allCards);

        for (Card card : subSet.allCards) {
            if (!superSet.removeCard(card)) {
                return false;
            }
        }
        return true;
    }

    public CardSet diff(CardSet set) {
        CardSet diff = new CardSet(allCards);

        for (Card card : set.allCards) {
            diff.removeCard(card);
        }

        return diff;
    }

    public void removeCards(ArrayList<Card> cards) {
        for (Card card : cards) {
            this.removeCard(card);
        }
    }

    public void removeCards(CardSet cardSet) {
        removeCards(cardSet.allCards);
    }

    public void removeAllCards() {
        while (!allCards.isEmpty()) {
            removeCard(allCards.get(0));
        }
    }
}
