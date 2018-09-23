package client.views.components;

import client.CardEvent;
import client.ClientManager;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import server.models.CardSet;
import server.models.cards.Card;
import server.models.cards.DropTargetCard;

import java.util.ArrayList;

public class CardSetView extends HBox {
    private CardSet cardSet;
    private CardView selectedCard;
    private EventHandler<CardEvent> cardEventHandler;
    private CardView dropTarget;
    private boolean receiverMode = false;
    private ArrayList<CardView> cardViews;

    public CardSetView() {
        this(new CardSet());
    }

    public CardSetView(CardSet cardSet) {
        initLayout();
        cardViews = new ArrayList<>();

        this.cardSet = cardSet;
        for (Card card : cardSet.getCards()) {
            addCard(card);
        }

        this.dropTarget = new CardView(new DropTargetCard(this));
        this.getChildren().add(dropTarget);
        this.dropTarget.setVisible(false);
        this.dropTarget.setOnMouseClicked(event -> {
            ClientManager.getInstance().droppedToTarget(this);
        });
    }

    public void setCardEventHandler(EventHandler<CardEvent> handler) {
        this.cardEventHandler = handler;
    }

    private void initLayout() {
        setSpacing(-100);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(10, 10, 10, 10));
    }

    public void addCard(Card card) {
        CardView cardView = new CardView(card);
        getChildren().add(cardView);
        cardViews.add(cardView);

        cardView.setParentSet(this);

        cardSet.addCard(card);

        fireEvent(cardView, CardEvent.CARD_ADDED);

        if (!card.isHidden()) {
            assignEvents(cardView);
            markValidity();
        }
    }

    public void removeCard(CardView cardView) {
        cardSet.removeCard(cardView.getCard());
        getChildren().remove(cardView);
        cardViews.remove(cardView);

        fireEvent(cardView, CardEvent.CARD_REMOVED);
        markValidity();
    }

    private void markValidity() {
        boolean validMeld = cardSet.isAValidMeld();

        for (CardView cardView: cardViews) {
            cardView.setValid(validMeld);
        }
    }

    private void fireEvent(CardView cardView, EventType<CardEvent> eventType) {
        if (cardEventHandler != null) {
            cardEventHandler.handle(new CardEvent(cardView, eventType));
        }
    }

    private void assignEvents(CardView cardView) {
        cardView.setOnMouseClicked(event -> {
//            onCardSelected(cardView);
            ClientManager.getInstance().cardSelected(cardView);
        });
    }

    public CardView getSelectedCard() {
        return selectedCard;
    }

    public void setSelectedCard(CardView selectedCard) {
        this.selectedCard = selectedCard;
    }

    public void clearSelectedCards() {
        if (selectedCard != null) {
            this.selectedCard.setSelected(false);
        }
        this.selectedCard = null;
    }

    public int getCardCount() {
        return cardSet.getCards().size();
    }

    public boolean isReceiverMode() {
        return receiverMode;
    }

    public void setReceiverMode(boolean receiverMode) {
        if (this.receiverMode == receiverMode) {
            return;
        }
        this.receiverMode = receiverMode;
        dropTarget.setVisible(receiverMode);
        getChildren().remove(dropTarget);
        getChildren().add(dropTarget);
    }

    public boolean isEmpty() {
        return getCardCount() <= 0;
    }
}
