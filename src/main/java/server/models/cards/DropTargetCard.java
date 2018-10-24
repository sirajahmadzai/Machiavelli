package server.models.cards;

import client.views.components.CardSetView;

public class DropTargetCard extends Card {
    /**
     * CLASS VARIABLES
     */
    CardSetView view;

    /**
     * CONSTRUCTOR
     *
     * @param view
     */
    public DropTargetCard(CardSetView view) {
        super();
        this.hidden = false;
        this.dropTarget = true;
        this.view = view;
    }

    /**
     * GETTERS
     */
    /**
     * @return
     */
    public CardSetView getView() {
        return view;
    }

    /**
     * SETTERS
     */
    /**
     * @param view
     */
    public void setView(CardSetView view) {
        this.view = view;
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return "droptarget";
    }

    /**
     * @return
     */
    @Override
    public String getImgUrl() {
        return DROP_TARGET_IMAGE;
    }
}
