package server.models.cards;

import com.sun.javaws.exceptions.InvalidArgumentException;

/**
 *
 */
public class Joker extends Changeable {
    /**
     * CONSTRUCTOR
     *
     * @param suit
     */
    public Joker(Suit suit, int id) throws InvalidArgumentException {
        super(suit, 15, id);
        joker = true;
    }


    /**
     * changes rank of Joker card
     *
     * @param rank
     * @throws InvalidCardRankException
     */
    @Override
    public void changeRank(int rank) throws InvalidCardRankException {
        if (rank < 1 || rank > 14) {
            throw new InvalidCardRankException();
        } else {
            this.rank = rank;
        }
    }

    /**
     * returns String "joker"
     *
     * @return String
     */
    @Override
    public String toString() {
        return "joker";
    }
}
