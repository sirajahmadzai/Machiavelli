package server.models.cards;

import client.views.components.CardSetView;

public class DropTargetCard extends Card {
    CardSetView view;
    public DropTargetCard(CardSetView view){
        super();
        this.hidden = false;
        this.dropTarget = true;
        this.view = view;
    }

    public CardSetView getView() {
        return view;
    }

    public void setView(CardSetView view) {
        this.view = view;
    }

    @Override
    public String toString() {
        return "droptarget";
    }

    @Override
    public String getImgUrl() {
        return DROP_TARGET_IMAGE;
    }
}
