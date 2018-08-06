package models.cards;


import com.sun.javaws.exceptions.InvalidArgumentException;

public abstract class Card {

    /***************************************
     ************ PRIVATES *****************
     ***************************************/

    private Suit suit;
    private int pointValue;
    private String name;
    private int id;

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
    public Card(Suit suit, int pointValue, String name, int rank, int id) {
        this.suit = suit;
        this.pointValue = pointValue;
        this.name = name;
        this.rank = rank;
        this.id = id;
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

    public int getId() {
        return id;
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
     * gets cardName by rank
     *
     * @param rank
     * @return
     * @throws InvalidArgumentException
     */
    public String getCardNameByRank(int rank) throws InvalidArgumentException {
        if (rank >= 2 && rank <= 10) return String.valueOf(rank);
        if (rank == 11) return "Jack";
        else if (rank == 12) return "Queen";
        else if (rank == 13) return "King";
        else if (rank == 1 || rank == 14) return "Ace";
        else throw new InvalidArgumentException(new String[]{"invalid rank"});
    }

    /**
     * gets cardValue by rank
     *
     * @param rank
     * @return
     * @throws InvalidArgumentException
     */
    public int getCardValueByRank(int rank) throws InvalidArgumentException {
        if (rank >= 2 && rank <= 10) return rank;
        else if (rank > 10 && rank < 14) return 10;
        else if (rank == 1 || rank == 14) return 15;
        else if (rank == 15) return 20;
        else throw new InvalidArgumentException(new String[]{"invalid rank"});
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
