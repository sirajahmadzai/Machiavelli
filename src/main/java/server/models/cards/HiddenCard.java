package server.models.cards;

public class HiddenCard extends Card {
    private HiddenCard() {
        super();
        this.hidden = true;
    }

    private static HiddenCard ourInstance = new HiddenCard();

    public static HiddenCard getInstance() {
        return ourInstance;
    }

    @Override
    public String toString() {
        return "hidden";
    }

    @Override
    public String getImgUrl() {
        return BACK_OF_CARD_IMAGE;
    }
}
