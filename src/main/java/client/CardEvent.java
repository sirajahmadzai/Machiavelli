package client;

import client.views.components.CardView;
import javafx.event.Event;
import javafx.event.EventType;
import server.models.cards.Card;

public class CardEvent extends Event{
    private CardView cardView;

    public static final EventType<CardEvent> CARD_ADDED = new EventType<CardEvent>("CARD_ADDED");
    public static final EventType<CardEvent> CARD_REMOVED = new EventType<CardEvent>("CARD_REMOVED");
    public static final EventType<CardEvent> CARD_SELECTED = new EventType<CardEvent>("CARD_SELECTED");

    public CardEvent(CardView cardView, EventType<CardEvent> eventType) {
        super(eventType);
        this.cardView = cardView;
    }

    public void setCardView(CardView cardView) {
        this.cardView = cardView;
    }

    public CardView getCardView() {
        return cardView;
    }
}
