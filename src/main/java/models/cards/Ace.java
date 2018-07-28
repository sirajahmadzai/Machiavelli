package models.cards;

public class Ace extends Changeable {

    public Ace(Suit suit, int pointValue, String name, int rank) {
        super(suit, pointValue, name, rank);
    }


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
