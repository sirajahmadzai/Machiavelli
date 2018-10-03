package client.views.components;

import client.CardEvent;
import client.ClientManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CardSetView extends HBox {
    private CardSet cardSet;
    private CardView selectedCard;
    private EventHandler<CardEvent> cardEventHandler;
    private CardView dropTarget;
    private boolean receiverMode = false;
    private ArrayList<CardView> cardViews;
    private boolean checkForValidity = true;
    private CardSet snapshot;

    public CardSetView() {
        this(new CardSet());
    }

    public CardSetView(CardSet cardSet) {
        initLayout();
        cardViews = new ArrayList<>();
        snapshot = new CardSet();

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
            sort();
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
        if (!checkForValidity){
            return;
        }

        boolean validMeld = cardSet.isAValidMeld(3);

        for (CardView cardView : cardViews) {
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
        return cardSet.totalCount();
    }

    public CardSet getCardSet() {
        return cardSet;
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

    // If the set is still a valid set after adding proposed card, set receiver mode on.
    public void setReceiverMode(Card card) {
        setReceiverMode(canAcceptCard(card));
    }

    public boolean isEmpty() {
        return getCardCount() <= 0;
    }

    private boolean canAcceptCard(Card card) {
        return cardSet.canAcceptCard(card);
    }

    private void sort() {
        ObservableList<Node> workingCollection = FXCollections.observableArrayList(
                this.getChildren()
        );

        Collections.sort(workingCollection, new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                CardView c1 = (CardView) o1;
                CardView c2 = (CardView) o2;
                return c1.getCard().compareTo(c2.getCard());
            }
        });

        this.getChildren().setAll(workingCollection);
    }

    public void setCheckForValidity(boolean checkForValidity) {
        this.checkForValidity = checkForValidity;
    }

    public CardSet takeSnapshot() {
        this.snapshot = cardSet.getSnapshot();
        return this.snapshot;
    }

    public CardSet getSnapshot() {
        return snapshot;
    }
}
