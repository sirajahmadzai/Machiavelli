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

public class CardSetView extends HBox {
    private CardSet cardSet;
    private EventHandler<CardEvent> cardEventHandler;
    private CardView dropTarget;
    private boolean receiverMode = false;
    private ArrayList<CardView> cardViews;
    private boolean checkForValidity = true;
    private boolean interactive = true;
    private CardSet snapshot;

    public CardSetView() {
        this(new CardSet());
    }

    public CardSetView(CardSet cardSet) {
        initLayout();
        cardViews = new ArrayList<>();
        snapshot = new CardSet();
        this.cardSet = new CardSet();

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

    public CardView addCard(Card card) {
        return addCard(card, false);

    }

    private CardView addCard(Card card, boolean silent) {
        CardView cardView = new CardView(card);
        getChildren().add(cardView);

        cardViews.add(cardView);

        cardView.setParentSet(this);

        cardSet.addCard(card);

        if (!silent) {
            fireEvent(cardView, CardEvent.CARD_ADDED);
        }

        if (interactive) {
            assignEvents(cardView);
            if (!silent) {
                markValidity();
                cardView.setNewcomer(true);
                sort();
            }
        }
        return cardView;
    }

    public void removeCard(CardView cardView) {
        removeCard(cardView, false);
    }

    private void removeCard(CardView cardView, boolean silent) {
        cardSet.removeCard(cardView.getCard());
        getChildren().remove(cardView);
        cardViews.remove(cardView);

        if (!silent) {
            fireEvent(cardView, CardEvent.CARD_REMOVED);
        }

        markValidity();
    }

    private void markValidity() {
        if(!interactive){
            return;
        }

        boolean validMeld = true;
        if (checkForValidity) {
            validMeld = cardSet.isAValidMeld(3);
        }

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
            ClientManager.getInstance().cardSelected(cardView);
        });
    }

    public int getCardCount() {
        return cardSet.totalCount();
    }

    public CardSet getCardSet() {
        return cardSet;
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

    // If the set is still a valid set after adding proposed cards, set receiver mode on.
    public void setReceiverMode(CardSet cardSetToAppend) {
        setReceiverMode(this.cardSet.canAcceptCards(cardSetToAppend));
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

    public void setInteractive(boolean interactive) {
        this.interactive = interactive;
    }

    private void resetCardStates() {
        // Clear newcomers
        for (CardView cardView : cardViews) {
            cardView.setNewcomer(false);
        }
    }

    public CardSet takeSnapshot() {
        resetCardStates();
        this.snapshot = cardSet.getSnapshot();
        return this.snapshot;
    }

    public CardSet getSnapshot() {
        return snapshot;
    }

    public void rollbackMoves() {
        while (!cardViews.isEmpty()) {
            removeCard(cardViews.get(0), true);
        }

        for (Card card : snapshot.getCards()) {
            addCard(card, true);
        }

        sort();
    }

    // This method is used only for hidden hands. (ie the opponents).
    // After the opponent plays her turn, we remove as many cards as she played to the table.
    public void removeCards(int i) {
        while (i > 0) {
            removeCard(cardViews.get(0));
            i--;
        }
    }
}
