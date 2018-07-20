package models.cards;

import models.Suit;

public abstract class Changeable extends Card {

    public Changeable(Suit suit, int pointValue, String name, int rank) {
        super(suit, pointValue, name, rank);
    }

    public abstract void changeRank(int rank) throws InvalidCardRankException;
}
