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

    @Override
    public String toString() {
        String rank = "";
        if (this.rank == 11) rank = "j";
        else if (this.rank == 12) rank = "q";
        else if (this.rank == 13) rank = "k";
        else rank = String.valueOf(this.rank);
        return rank + this.getSuit().name().substring(0, 1).toLowerCase();
    }
}
