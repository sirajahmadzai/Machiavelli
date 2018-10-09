package client.views.components;

import client.CardEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.FlowPane;
import server.models.CardSet;
import server.models.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class PlayArea implements EventHandler<CardEvent> {
    private FlowPane setsArea;
    private CardSetView placeholderSet;
    private ArrayList<CardSetView> setViews;
    private List<CardSet> snapshot;

    public PlayArea(FlowPane setsArea) {
        this.setsArea = setsArea;
        setViews = new ArrayList<>();
        snapshot = new ArrayList<>();
        createPlaceholderSet();
    }

    public void setActive(boolean active) {
        for (CardSetView setView : setViews) {
            setView.setReceiverMode(active);
        }
    }

    public void setActive(Card card) {
        for (CardSetView setView : setViews) {
            setView.setReceiverMode(card);
        }
    }

    private void addSet(CardSet cardSet) {
        CardSetView cardSetView = new CardSetView(cardSet);
        addSet(cardSetView);
    }

    private void addSet(CardSetView cardSetView) {
        setsArea.getChildren().add(cardSetView);
        cardSetView.setCardEventHandler(this);
        setViews.add(cardSetView);
    }

    private void removeSet(CardSetView cardSetView) {
        setsArea.getChildren().remove(cardSetView);
        cardSetView.setCardEventHandler(null);
        setViews.remove(cardSetView);
    }

    private void createPlaceholderSet() {
        this.placeholderSet = new CardSetView();
        addSet(placeholderSet);
    }

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

    public List<CardSet> getSnapshot() {
        return snapshot;
    }

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

    public boolean isValid(int setSize) {
        for (CardSetView setView : setViews) {
            if (!setView.getCardSet().isAValidMeld(setSize)) {
                return false;
            }
        }

        return true;
    }

    public void setAllSets(List<CardSet> table) {
        while (!setViews.isEmpty()){
            removeSet(setViews.remove(0));
        }

        setViews.clear();
        snapshot.clear();

        for (CardSet cardSet : table) {
            addSet(cardSet);
        }
        createPlaceholderSet();
    }
}
