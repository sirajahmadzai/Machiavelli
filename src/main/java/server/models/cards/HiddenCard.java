package server.models.cards;

public class HiddenCard extends Card {
    /**
     * CONSTRUCTOR
     */
    private HiddenCard() {
        super();
        this.hidden = true;
    }

    /**
     * creating an instance of HiddenCard() object
     */
    private static HiddenCard ourInstance = new HiddenCard();

    /**
     * @return HiddenCard
     */
    public static HiddenCard getInstance() {
        return ourInstance;
    }

    /**
     * gets String "hidden"
     *
     * @return String
     */
    public static String getAsString() {
        return "hidden";
    }

    /**
     * returns String "hidden"
     *
     * @return String
     */
    @Override
    public String toString() {
        return "hidden";
    }

    /**
     * returns the url of the BACK_OF_CARD_IMAGE
     *
     * @return
     */
    @Override
    public String getImgUrl() {
        return BACK_OF_CARD_IMAGE;
    }
}
