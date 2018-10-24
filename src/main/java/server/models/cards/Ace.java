package server.models.cards;

import com.sun.javaws.exceptions.InvalidArgumentException;

public class Ace extends Changeable {
    /**
     * CONSTRUCTOR
     *
     * @param suit
     */
    public Ace(Suit suit, int id) throws InvalidArgumentException {
        super(suit, 1, id);
    }


    /**
     * changes the rank of an Ace card to a one of the two valid Ace card rank values (1 and 14)
     *
     * @param rank
     * @throws InvalidCardRankException
     */
    @Override
    public void changeRank(int rank) throws InvalidCardRankException {
        if (rank == 1) {
            this.rank = rank;
        }
        if (rank == 14) {
            this.rank = rank;
        } else {
            throw new InvalidCardRankException();
        }
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return "a" + this.getSuit().name().substring(0, 1).toLowerCase();
    }
}
