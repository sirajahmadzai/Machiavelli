package models.cards;


import models.Suit;

public abstract class Card {

    private Suit suit;
    private int pointValue;
    private String name;
    protected int rank;

    public Card(Suit suit, int pointValue, String name, int rank) {
        this.suit = suit;
        this.pointValue = pointValue;
        this.name = name;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public int getPointValue() {
        return pointValue;
    }

    public String getName() {
        return name;
    }

    public int getRank(){
        return rank;
    };

    class InvalidCardRankException extends Exception {
        public InvalidCardRankException() {
            super("Invalid rank");
        }
    }
}
