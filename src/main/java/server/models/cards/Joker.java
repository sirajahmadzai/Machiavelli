package server.models.cards;

import com.sun.javaws.exceptions.InvalidArgumentException;

public class Joker extends Changeable {
    /**
     * CONSTRUCTOR
     *
     * @param suit
     * @param rank
     */
    public Joker(Suit suit, int rank, int id) throws InvalidArgumentException {
        super(suit, rank, id);
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
}
