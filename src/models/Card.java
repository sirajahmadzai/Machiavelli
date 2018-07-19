package models;

import com.sun.org.apache.xpath.internal.operations.Bool;

public class Card {

private Suit suit;
private int pointValue;
private String name;

    public Card(Suit suit, int pointValue, String name) {
        this.suit = suit;
        this.pointValue = pointValue;
        this.name = name;
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

    public boolean isMovable(){
        return false;
    }
}
