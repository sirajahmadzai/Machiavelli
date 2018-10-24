package client;

import client.views.components.CardSetView;
import client.views.components.CardView;
import javafx.event.Event;
import javafx.event.EventType;

public class CardEvent extends Event {
    /**
     * PRIVATES
     */
    private CardView cardView;

    /**
     * PRIVATE STATIC FINALS
     */
    public static final EventType<CardEvent> CARD_ADDED = new EventType<CardEvent>("CARD_ADDED");
    public static final EventType<CardEvent> CARD_REMOVED = new EventType<CardEvent>("CARD_REMOVED");
    public static final EventType<CardEvent> CARD_SELECTED = new EventType<CardEvent>("CARD_SELECTED");

    /**
     * CONSTRUCTOR
     *
     * @param cardView
     * @param eventType
     */
    public CardEvent(CardView cardView, EventType<CardEvent> eventType) {
        super(eventType);
        this.cardView = cardView;
    }

    /**
     * GETTERS
     */
    /**
     * gets the cardView
     *
     * @return
     */
    public CardView getCardView() {
        return cardView;
    }

    /**
     * gets the parent cardSet view
     *
     * @return
     */
    public CardSetView getParentCardSetView() {
        if (cardView == null) {
            return null;
        } else {
            return cardView.getParentSet();
        }
    }

    /**
     * SETTERS
     */
    /**
     * sets the card view
     *
     * @param cardView
     */
    public void setCardView(CardView cardView) {
        this.cardView = cardView;
    }


}
