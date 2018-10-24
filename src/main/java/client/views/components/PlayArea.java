package client.views.components;

import client.CardEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.FlowPane;
import server.models.CardSet;

import java.util.ArrayList;
import java.util.List;

public class PlayArea implements EventHandler<CardEvent> {
    /**
     * PRIVATES
     */
    private FlowPane setsArea;
    private CardSetView placeholderSet;
    private ArrayList<CardSetView> setViews;
    private List<CardSet> snapshot;

    /**
     * CONSTRUCTOR
     *
     * @param setsArea
     */
    public PlayArea(FlowPane setsArea) {
        this.setsArea = setsArea;

        setViews = new ArrayList<>();
        snapshot = new ArrayList<>();
        createPlaceholderSet();
    }

    /**
     * GETTERS
     */
    /**
     * @return
     */
    public List<CardSet> getSnapshot() {
        return snapshot;
    }

    /**
     * SETTERS
     */
    /**
     * set's this playArea's active value
     *
     * @param active
     */
    public void setActive(boolean active) {
        for (CardSetView setView : setViews) {
            setView.setReceiverMode(active);
        }
    }

    /**
     * set's this playArea's active value
     *
     * @param cardSet
     */
    public void setActive(CardSet cardSet) {
        for (CardSetView setView : setViews) {
            setView.setReceiverMode(cardSet);
        }

    }

    /**
     * @param table
     */
    public void setAllSets(List<CardSet> table) {
        while (!setViews.isEmpty()) {
            removeSet(setViews.remove(0));
        }

        setViews.clear();

        CardSetView newSet = null;
        for (CardSet cardSet : table) {
            newSet = addSet(cardSet);
            newSet.takeSnapshot();
        }
        createPlaceholderSet();
    }

    /**
     * creates a new cardSetView to add the given cardSet to, then adds the newly created cardSetView to the ArrayList of setViews
     *
     * @param cardSet
     * @return the newly created cardSetView
     */
    private CardSetView addSet(CardSet cardSet) {
        CardSetView cardSetView = new CardSetView(cardSet);
        addSet(cardSetView);
        return cardSetView;
    }

    /**
     * adds the given cardSetView to setsArea, sets the CardEventHandler for the cardSetView then adds the cardSetView to the ArrayList of setViews
     *
     * @param cardSetView
     */
    private void addSet(CardSetView cardSetView) {
        setsArea.getChildren().add(cardSetView);
        cardSetView.setCardEventHandler(this);
        setViews.add(cardSetView);
    }

    /**
     * removes the given cardSetView from setsArea, sets the CardEventHandler to null for the cardSetView then removes the cardSetView from the ArrayList of setViews
     *
     * @param cardSetView
     */
    private void removeSet(CardSetView cardSetView) {
        setsArea.getChildren().remove(cardSetView);
        cardSetView.setCardEventHandler(null);
        setViews.remove(cardSetView);
    }

    /**
     * creates a new placeholderSet and adds it to
     */
    private void createPlaceholderSet() {
        this.placeholderSet = new CardSetView();
        addSet(placeholderSet);
    }

    /**
     * @return
     */
    public List<CardSet> takeSnapshot() {
        List<CardSet> snapshot = new ArrayList<>();
        for (CardSetView view : setViews) {
            if (view != placeholderSet) {
                snapshot.add(view.takeSnapshot());
            }
        }

        this.snapshot = snapshot;
        return this.snapshot;
    }

    /**
     * rolls back any recent moves
     */
    public void rollbackMoves() {
        setAllSets(snapshot);
    }

    /**
     * checks the event type and handles it accordingly
     *
     * @param event
     */
    @Override
    public void handle(CardEvent event) {

        CardSetView changedSetView = event.getParentCardSetView();
        if (event.getEventType() == CardEvent.CARD_ADDED) {
            // If user places a card on the placeholder set, we create a new placeholder and leave the old on the table.
            if (changedSetView == placeholderSet) {
                createPlaceholderSet();
            }
        } else if (event.getEventType() == CardEvent.CARD_REMOVED) {
            // Remove empty set from play area.
            if (changedSetView.isEmpty()) {
                removeSet(changedSetView);
            }
        } else if (event.getEventType() == CardEvent.CARD_SELECTED) {

        }
    }

    /**
     * check if the set is valid
     *
     * @param setSize
     * @return
     */
    public boolean isValid(int setSize) {
        for (CardSetView setView : setViews) {
            if (!setView.getCardSet().isAValidMeld(setSize)) {
                return false;
            }
        }

        return true;
    }
}