package client.views.components;

import client.CardEvent;
import client.ClientManager;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import server.models.CardSet;
import server.models.cards.Card;
import server.models.cards.DropTargetCard;

public class CardSetView extends HBox {
    private CardSet cardSet;
    private CardView selectedCard;
    private EventHandler<CardEvent> cardEventHandler;
    private CardView dropTarget;
    private boolean receiverMode = false;

    public CardSetView() {
        this(new CardSet());
    }

    public CardSetView(CardSet cardSet) {
        initLayout();


        this.cardSet = cardSet;
        for (Card card : cardSet.getCards()) {
            addCard(card);
        }

        this.dropTarget = new CardView(new DropTargetCard(this));
        this.getChildren().add(dropTarget);
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
        cardView.setParentSet(this);

        cardSet.getCards().add(card);

        if (cardEventHandler != null) {
            cardEventHandler.handle(new CardEvent(cardView, CardEvent.CARD_ADDED));
        }

        if (!card.isHidden()) {
            assignEvents(cardView);
        }
    }

    public void removeCard(CardView cardView) {
        cardSet.getCards().remove(cardView.getCard());
        getChildren().remove(cardView);
    }

    private void assignEvents(CardView cardView) {
        cardView.setOnMouseClicked(event -> {
            setSelectedCard(cardView);
        });
    }

    private void setSelectedCard(CardView cardView) {
//        Card card = null;
        // Deselect previously selected card
        if (selectedCard != null) {
            selectedCard.setSelected(false);
        }

        // Deselect card if it's already selected.
        if (selectedCard == cardView) {
            selectedCard = null;
        } else {
            // Select card
            selectedCard = cardView;
            selectedCard.setSelected(true);
        }

        ClientManager.getInstance().cardSelected(selectedCard);
    }

    public CardView getSelectedCard() {
        return selectedCard;
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

    public void addChangeListener(ListChangeListener<Node> listener) {
        this.getChildren().addListener(listener);
    }

    public void removeChangeListener(ListChangeListener<Node> listener) {
        this.getChildren().removeListener(listener);
    }
}
