package Java;

public class Basic extends Card {

    private final int rank;

    public Basic(Suit suit, int pointValue, String name, int rank) {
        super(suit, pointValue, name);
        this.rank = rank;
    }
}
