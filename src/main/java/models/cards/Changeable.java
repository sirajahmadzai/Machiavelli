package models.cards;

public abstract class Changeable extends Card {

    /**
     * CONSTRUCTOR
     *
     * @param suit
     * @param pointValue
     * @param name
     * @param rank
     */
    public Changeable(Suit suit, int pointValue, String name, int rank, int id) {
        super(suit, pointValue, name, rank, id);
    }

    /**
     * abstract method to be defined by child class intended to change card rank
     *
     * @param rank
     * @throws InvalidCardRankException
     */
    public abstract void changeRank(int rank) throws InvalidCardRankException;
}
