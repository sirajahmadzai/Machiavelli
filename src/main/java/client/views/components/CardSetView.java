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
import java.util.Stack;

public class CardSetView extends HBox {
    /**
     * PRIVATES
     */
    //cardSet
    private CardSet cardSet;
    private EventHandler<CardEvent> cardEventHandler;
    private CardView dropTarget;
    private boolean receiverMode = false;

    //list of cardViews
    private ArrayList<CardView> cardViews;

    //meld validity checker
    private boolean checkForValidity = true;

    private boolean interactive = true;

    public Stack<CardSet> snapshots;

    /**
     * CONSTRUCTOR
     */
    public CardSetView() {
        this(new CardSet());
    }


    /**
     * CONSTRUCTOR
     *
     * @param cardSet
     */
    public CardSetView(CardSet cardSet) {
        initLayout();
        cardViews = new ArrayList<>();
        snapshots = new Stack<>();
        this.cardSet = new CardSet();

        for (Card card : cardSet.getCards()) {
            addCard(card);
        }

        this.dropTarget = new CardView(new DropTargetCard(this));
        this.getChildren().add(dropTarget);
        this.dropTarget.setVisible(false);
        /*  MF */
        this.dropTarget.setOnMouseClicked(event -> {
            ClientManager.getInstance().moveSelectedCards(this);
        });
        /* End */
    }

    /**
     * sets the cardEventHandler for this cardSetView
     *
     * @param handler
     */
    public void setCardEventHandler(EventHandler<CardEvent> handler) {
        this.cardEventHandler = handler;
    }

    /**
     * sets up layout
     */
    private void initLayout() {
        setSpacing(-100);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(10, 10, 10, 10));
    }

    /**
     * GETTERS
     */
    /**
     * @return
     */
    public CardSet getLastSnapshot() {
        return snapshots.peek();
    }

    public CardSet getFirstSnapshot() {
        if (!snapshots.empty()) {
            return snapshots.firstElement();
        }
        return null;
    }

    /**
     * gets the number of cards in cardSet
     *
     * @return
     */
    public int getCardCount() {
        return cardSet.totalCount();
    }

    /**
     * gets this cardView's cardSet
     *
     * @return
     */
    public CardSet getCardSet() {
        return cardSet;
    }
    /**
     * SETTERS
     */
    /**
     * @param checkForValidity
     */
    public void setCheckForValidity(boolean checkForValidity) {
        this.checkForValidity = checkForValidity;
    }

    /**
     * @param interactive
     */
    public void setInteractive(boolean interactive) {
        this.interactive = interactive;
    }


    /**
     * add the given card to this CardSetView
     *
     * @param card
     * @return
     */
    public CardView addCard(Card card) {
        return addCard(card, false);

    }

    /**
     * creates a cardView from the given card,
     * then adds the newly created cardView to this CardSetView,
     * also adds the newly created cardView to the list of cardViews,
     * sets this CardSetView as the parent of the newly created cardView
     * then adds the given card to this CardSetView's cardSet
     * if silent the fired a CARD_ADDED event
     * if interactive, assign events to the cardView
     * as long as it's not silent, mark the validity of the cardView, set the cardView as a newcomer and sort
     * return the newly created cardView
     *
     * @param card
     * @param silent
     * @return
     */
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

    /**
     * removes the given card from the given cardView
     *
     * @param cardView
     */
    public void removeCard(CardView cardView) {
        removeCard(cardView, false);
    }

    public void removeAllCards() {
        removeAllCards(false);
    }

    private void removeAllCards(boolean silent) {
        while (!cardViews.isEmpty()) {
            removeCard(cardViews.get(0), silent);
        }
    }

    /**
     * removes the given cardView from this CardSetView's children, cardSet and list of cardViews
     * if silent fire a CARD_REMOVED event
     * mark validity
     *
     * @param cardView
     * @param silent
     */
    private void removeCard(CardView cardView, boolean silent) {
        cardSet.removeCard(cardView.getCard());
        getChildren().remove(cardView);
        cardViews.remove(cardView);

        if (!silent) {
            fireEvent(cardView, CardEvent.CARD_REMOVED);
        }

        markValidity();
    }

    /**
     * marks a meld as valid if valid
     */
    private void markValidity() {
        if (!interactive) {
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

    /**
     * executes the given event
     *
     * @param cardView
     * @param eventType
     */
    private void fireEvent(CardView cardView, EventType<CardEvent> eventType) {
        if (cardEventHandler != null) {
            cardEventHandler.handle(new CardEvent(cardView, eventType));
        }
    }

    /**
     * assigns events to cards in the given cardView
     *
     * @param cardView
     */
    private void assignEvents(CardView cardView) {
        cardView.setOnMouseClicked(event -> {
            ClientManager.getInstance().cardSelected(cardView);
        });
    }


    /**
     * sets this CardSetView's receive mode
     *
     * @param receiverMode
     */
    public void setReceiverMode(boolean receiverMode) {
        if (this.receiverMode == receiverMode) {
            return;
        }
        this.receiverMode = receiverMode;
        dropTarget.setVisible(receiverMode);
        getChildren().remove(dropTarget);
        getChildren().add(dropTarget);
    }

    /**
     * If the set is still a valid set after adding proposed cards, set receiver mode on.
     *
     * @param cardSetToAppend
     */
    public void setReceiverMode(CardSet cardSetToAppend) {
        setReceiverMode(this.cardSet.canAcceptCards(cardSetToAppend));
    }

    /**
     * checks if this CardSetView's cardSet is empty
     *
     * @return
     */
    public boolean isEmpty() {
        return getCardCount() <= 0;
    }

    /**
     * checks if the given card can be added to this CardSetView's cardSet
     *
     * @param card
     * @return
     */
    private boolean canAcceptCard(Card card) {
        return cardSet.canAcceptCard(card);
    }

    /**
     * sorts this CardSetView's children
     */
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


    /**
     * undo any cards moved
     */
    private void resetCardStates() {
        // Clear newcomers
        for (CardView cardView : cardViews) {
            cardView.setNewcomer(false);
        }
    }

    /**
     * takes a snapshot of the current state of all cards in this CardSetView's cardSet
     *
     * @return
     */
    public CardSet takeSnapshot() {
        resetCardStates();

        CardSet snapshot = cardSet.getSnapshot();
        snapshots.push(snapshot);
        return snapshot;
    }

    public void init_snapshots() {
        snapshots.clear();
    }

    /**
     * reverse any cards taken from this CardSetView's cardSet
     */
    public void rollbackMoves() {
        if (snapshots.size() < 2) return;

        removeAllCards(true);
        snapshots.pop();
        CardSet lastSnapshot = snapshots.peek();

        for (Card card : lastSnapshot.getCards()) {
            addCard(card, true);
        }
        sort();
    }

    /**
     * This method is used only for hidden hands. (ie the opponents).
     * After the opponent plays her turn, we remove as many cards as she played to the table.
     *
     * @param i
     */
    public void removeCards(int i) {
        while (i > 0) {
            removeCard(cardViews.get(0));
            i--;
        }
    }
}
