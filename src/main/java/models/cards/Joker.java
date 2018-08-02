package models.cards;

public class Joker extends Changeable {

    /**
     * CONSTRUCTOR
     *
     * @param suit
     * @param pointValue
     * @param name
     * @param rank
     */
    public Joker(Suit suit, int pointValue, String name, int rank, int id) {
        super(suit, pointValue, name, rank, id);
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
