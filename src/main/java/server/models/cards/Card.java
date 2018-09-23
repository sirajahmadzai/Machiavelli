package server.models.cards;


import com.sun.javaws.exceptions.InvalidArgumentException;
import commands.StringCommand;

public abstract class Card implements Comparable<Card> {

    /***************************************
     ************ PRIVATES *****************
     ***************************************/
    private Suit suit;
    private int pointValue;
    private String name;
    private int id;
    private String imgUrl;
    protected boolean hidden = false;
    protected boolean dropTarget= false;
    public static final String BACK_OF_CARD_IMAGE = "images/b.png";
    public static final String DROP_TARGET_IMAGE = "images/dropTarget.png";

    /******************************
     ********** PROTECTEDS ********
     ******************************/
    protected int rank;

    /**
     * CONSTRUCTOR
     *
     * @param suit
     * @param rank
     * @param id
     */
    public Card(Suit suit, int rank, int id) throws InvalidArgumentException {
        if (id < 0) throw new InvalidArgumentException(new String[]{"Invalid id"});
        if (suit == null) throw new InvalidArgumentException(new String[]{"Suit was null"});
        this.suit = suit;
        this.pointValue = getCardValueByInitialRank(rank);
        this.name = getCardNameByInitialRank(rank);
        this.rank = rank;
        this.id = id;
        this.imgUrl = getImageUrl();
    }

    public static Card fromString(String cardText) throws InvalidArgumentException {
        Suit suit = Suit.JOKER;
        int rank=0;

        if (cardText.equals("joker")) {
            return new Joker(Suit.JOKER, 15, 15);
        } else {
            if (cardText.endsWith("s")) {
                suit = Suit.SPADES;
            } else if (cardText.endsWith("h")) {
                suit = Suit.HEARTS;
            } else if (cardText.endsWith("d")) {
                suit = Suit.DIAMONDS;
            } else if (cardText.endsWith("c")) {
                suit = Suit.CLUBS;
            }

            String rankText = cardText.substring(0, cardText.length() - 1);
            switch (rankText) {
                case "a":
                    return new Ace(suit,14,14);
                case "j":
                    rank = 11;
                    break;
                case "q":
                    rank = 12;
                    break;
                case "k":
                    rank = 13;
                    break;
                default:
                    rank = Integer.parseInt(rankText);
            }
            return new Basic(suit, rank, rank);
        }
    }

//    Used to create a hidden card.
    protected Card(){
        suit = Suit.HIDDEN;
        rank = 0;
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
    int getPointValue() {
        return pointValue;
    }

    /**
     * @return
     */
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
     * @return
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * gets cardName by rank
     *
     * @param rank
     * @return
     * @throws InvalidArgumentException
     */
    private String getCardNameByInitialRank(int rank) throws InvalidArgumentException {
        if (rank >= 2 && rank <= 10) return String.valueOf(rank);
        if (rank == 11) return "Jack";
        else if (rank == 12) return "Queen";
        else if (rank == 13) return "King";
        else if (rank == 1 || rank == 14) return "Ace";
        else if (rank == 15) return "Joker";
        else throw new InvalidArgumentException(new String[]{"invalid rank"});
    }

    /**
     * gets cardValue by rank
     *
     * @param rank
     * @return
     * @throws InvalidArgumentException
     */
    private int getCardValueByInitialRank(int rank) throws InvalidArgumentException {
        if (rank >= 2 && rank <= 10) return rank;
        else if (rank > 10 && rank < 14) return 10;
        else if (rank == 1 || rank == 14) return 15;
        else if (rank == 15) return 20;
        else throw new InvalidArgumentException(new String[]{"invalid rank"});
    }

    /**
     * gets the imgUrl
     *
     * @return
     */
    private String getImageUrl() {
        String imgUrl = "";

        imgUrl += "images/";
        imgUrl += rank;
        imgUrl += suit.name().substring(0, 1).toUpperCase();
        imgUrl += ".png";
        return imgUrl;
    }

    public boolean isHidden() {
        return hidden;
    }

    public boolean isDropTarget() {
        return dropTarget;
    }

    @Override
    public int compareTo(Card otherCard) {
        return rank - otherCard.rank;
    }

    /**
     * Custom exception handling class
     */
    class InvalidCardRankException extends Exception {
        InvalidCardRankException() {
            super("Invalid rank");
        }
    }
}
