package server.models.cards;


import java.util.Objects;

public class Card implements Comparable<Card> {

    /***************************************
     ************ PRIVATES *****************
     ***************************************/
    private Suit suit;
    private int pointValue;
    private String name;
    private int id;
    private String imgUrl;
    protected boolean hidden = false;
    protected boolean dropTarget = false;
    protected boolean joker = false;
    public static final String BACK_OF_CARD_IMAGE = "images/b.png";
    public static final String DROP_TARGET_IMAGE = "images/dropTarget.png";
    public static final String NO_CARD_IMAGE = "images/nocards.png";

    /******************************
     ********** PROTECTS ********
     ******************************/
    protected int rank;

    /**
     * CONSTRUCTOR
     *
     * @param suit
     * @param rank
     * @param id
     */
    public Card(Suit suit, int rank, int id) throws IllegalArgumentException {
        if (id < 0) throw new IllegalArgumentException("Invalid id");
        if (suit == null) throw new IllegalArgumentException("Suit was null");
        this.suit = suit;
        this.pointValue = getCardValueByInitialRank(rank);
        this.name = getCardNameByInitialRank(rank);
        this.rank = rank;
        this.id = id;
        this.imgUrl = getImageUrl();
    }

    /**
     * @param cardText
     * @return
     * @throws IllegalArgumentException
     */
    public static Card fromString(String cardText) throws IllegalArgumentException {
        Suit suit = Suit.JOKER;
        int rank = 0;

        if (cardText.equals("joker")) {
            return new Joker(Suit.JOKER, 15);
        }
        if (cardText.equals("hidden")) {
            return HiddenCard.getInstance();
        }

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
                return new Ace(suit, 14);
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


    /**
     * Used to create a hidden card.
     */
    protected Card() {
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
     * @throws IllegalArgumentException
     */
    private String getCardNameByInitialRank(int rank) throws IllegalArgumentException {
        if (rank >= 2 && rank <= 10) return String.valueOf(rank);
        if (rank == 11) return "Jack";
        else if (rank == 12) return "Queen";
        else if (rank == 13) return "King";
        else if (rank == 1 || rank == 14) return "Ace";
        else if (rank == 15) return "Joker";
        else throw new IllegalArgumentException("invalid rank");
    }

    /**
     * gets cardValue by rank
     *
     * @param rank
     * @return
     * @throws IllegalArgumentException
     */
    private int getCardValueByInitialRank(int rank) throws IllegalArgumentException {
        if (rank >= 2 && rank <= 10) return rank;
        else if (rank > 10 && rank < 14) return 10;
        else if (rank == 1 || rank == 14) return 15;
        else if (rank == 15) return 20;
        else throw new IllegalArgumentException("invalid rank");
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

    /**
     * SETTERS
     */
    /**
     * @param suit
     */
    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    /**
     * @return
     */
    public boolean isHidden() {
        return hidden;
    }

    /**
     * @return
     */
    public boolean isDropTarget() {
        return dropTarget;
    }

    /**
     * @return
     */
    public boolean isJoker() {
        return joker;
    }

    /**
     * @param otherCard
     * @return
     */
    @Override
    public int compareTo(Card otherCard) {
        int diff = rank - otherCard.rank;

        if (diff != 0) {
            return diff;
        }

        return suit.compareTo(otherCard.suit);
    }


    /**
     * Cards with same rank and suits are considered equal
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;
        return compareTo(card) == 0;
    }

    /**
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(getSuit(), getRank());
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        String rank = "";
        if (this.rank == 11) rank = "j";
        else if (this.rank == 12) rank = "q";
        else if (this.rank == 13) rank = "k";
        else rank = String.valueOf(this.rank);
        return rank + this.getSuit().name().substring(0, 1).toLowerCase();
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
