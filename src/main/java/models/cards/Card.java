package models.cards;


public abstract class Card {

    /***************************************
     ************ PRIVATES *****************
     ***************************************/

    private Suit suit;
    private int pointValue;
    private String name;

    /******************************
     ********** PROTECTEDS ********
     ******************************/
    protected int rank;

    /**
     * CONSTRUCTOR
     *
     * @param suit
     * @param pointValue
     * @param name
     * @param rank
     */
    public Card(Suit suit, int pointValue, String name, int rank) {
        this.suit = suit;
        this.pointValue = pointValue;
        this.name = name;
        this.rank = rank;
    }

    /***************************************
     *************** GETTERS **************
     **************************************/
    /**
     * @return
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * @return
     */
    public int getPointValue() {
        return pointValue;
    }

    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @return
     */
    public int getRank() {
        return rank;
    }


    /**
     * Custom exception handling class
     */
    class InvalidCardRankException extends Exception {
        public InvalidCardRankException() {
            super("Invalid rank");
        }
    }
}
