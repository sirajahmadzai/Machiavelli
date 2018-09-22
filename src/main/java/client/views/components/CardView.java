package client.views.components;

import javafx.scene.image.ImageView;
import server.models.cards.Card;

public class CardView extends ImageView{
    private static final int CARD_PREF_WIDTH = 120;
    private static final int CARD_PREF_HEIGHT = 140;

    private CardSetView parentSet;
    private Card card;
    private boolean selected;

    public CardView(Card card) {
        super(card.getImgUrl());
        setCard(card);
        setFitWidth(CARD_PREF_WIDTH);
        setFitHeight(CARD_PREF_HEIGHT);
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Card getCard() {
        return card;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        if(selected){
            this.setEffect(ViewEffects.selectedCardEffect);
        }else {
            this.setEffect(ViewEffects.defaultEffect);
        }
    }

    public CardSetView getParentSet() {
        return parentSet;
    }

    public void setParentSet(CardSetView parentSet) {
        this.parentSet = parentSet;
    }

    public void removeFromParentSet() {
        getParentSet().removeCard(this);
    }
}
