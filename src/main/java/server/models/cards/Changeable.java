package server.models.cards;

import com.sun.javaws.exceptions.InvalidArgumentException;

/**
 *
 */
public abstract class Changeable extends Card {

    /**
     * PRIVATES
     */
    private int initialRank;

    /**
     * CONSTRUCTOR
     *
     * @param suit
     * @param rank
     */
    public Changeable(Suit suit, int rank, int id) throws InvalidArgumentException {
        super(suit, rank, id);
        initialRank = rank;
    }

    /**
     * @return
     */
    public int getInitialRank() {
        return initialRank;
    }

    /**
     * abstract method to be defined by child class intended to change card rank
     *
     * @param rank
     * @throws InvalidCardRankException
     */
    public abstract void changeRank(int rank) throws InvalidCardRankException;

}
