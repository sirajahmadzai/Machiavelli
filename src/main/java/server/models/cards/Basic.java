package server.models.cards;

public class Basic extends Card {

    /**
     * CONSTRUCTOR
     *
     * @param suit
     * @param rank
     */
    public Basic(Suit suit, int rank, int id) throws IllegalArgumentException {
        super(suit, rank, id);
    }
}
