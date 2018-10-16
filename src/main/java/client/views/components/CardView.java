package client.views.components;

import client.ViewHelper;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import server.models.cards.Card;

public class CardView extends ImageView implements Comparable<CardView>{
    private static final int CARD_PREF_WIDTH = 120;
    private static final int CARD_PREF_HEIGHT = 140;

    private CardSetView parentSet;
    private Card card;
    private boolean selected;
    private boolean valid;
    private boolean newcomer;

    public CardView(Card card) {
        super();
        Image image = ViewHelper.getImage(card.getImgUrl());
        setImage(image);

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
        updateEffect();
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
        updateEffect();
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

    private void updateEffect(){
        if(selected){
            this.setEffect(ViewEffects.selectedCardEffect);
        }else {
            if(valid){
                this.setEffect(ViewEffects.defaultEffect);
            }else{
                this.setEffect(ViewEffects.invalidEffect);
            }
        }
    }

    @Override
    public int compareTo(CardView otherView) {
        return card.compareTo(otherView.card);
    }

    public void setNewcomer(boolean newcomer) {
        this.newcomer = newcomer;
        this.setEffect(ViewEffects.newCardInSetEffect);
    }
}
