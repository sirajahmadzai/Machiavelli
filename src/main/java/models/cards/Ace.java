package models.cards;

public class Ace extends Changeable {

    /**
     * CONSTRUCTOR
     *
     * @param suit
     * @param pointValue
     * @param name
     * @param rank
     */
    public Ace(Suit suit, int pointValue, String name, int rank, int id) {
        super(suit, pointValue, name, rank, id);
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

}
