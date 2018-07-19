package models;

public class Ace extends Card {

    private int rank;
    private boolean isChangeable;

    public Ace(Suit suit, int pointValue, String name) {
        super(suit, pointValue, name);
    }


}
