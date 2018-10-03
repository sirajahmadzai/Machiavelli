package server.models.cards;

import com.sun.javaws.exceptions.InvalidArgumentException;

public class Basic extends Card {

    /**
     * CONSTRUCTOR
     *
     * @param suit
     * @param rank
     */
    public Basic(Suit suit, int rank, int id) throws InvalidArgumentException {
        super(suit, rank, id);
    }
}
