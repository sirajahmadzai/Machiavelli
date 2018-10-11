package client;

import client.views.components.CardView;
import server.models.CardSet;
import server.models.cards.Card;

import java.util.ArrayList;


public class SelectionManager {
    private ArrayList<CardView> selectedCards;
    private CardSet selectedCardsSet;

    /**
     */
    public SelectionManager() {
        selectedCards = new ArrayList<>();
        selectedCardsSet = new CardSet();
    }

    public void addCard(CardView cardView) {
        Card card = cardView.getCard();

        if (selectedCards.contains(cardView)) {
            deselectCard(cardView);
            if (!selectedCardsSet.isAValidMeld()) {
                deselectAll();
            }
            return;
        }

        if (!selectedCardsSet.canAcceptCard(card)) {
            clearSelections();
        }
        selectCard(cardView);
    }

    private void selectCard(CardView cardView) {
        selectedCards.add(cardView);
        selectedCardsSet.addCard(cardView.getCard());
        cardView.setSelected(true);
    }

    private void deselectCard(CardView cardView) {
        selectedCards.remove(cardView);
        selectedCardsSet.removeCard(cardView.getCard());
        cardView.setSelected(false);
    }

    public void deselectAll() {
        while (!selectedCards.isEmpty()) {
            deselectCard(selectedCards.get(0));
        }
    }


    private void clearSelections() {
        for (CardView cardView : selectedCards) {
            cardView.setSelected(false);
        }
        selectedCards.clear();
        selectedCardsSet.removeAllCards();
    }

    public boolean isEmpty() {
        return selectedCards.isEmpty();
    }

    public CardSet getSelectedCardsSet() {
        return selectedCardsSet;
    }

    public ArrayList<CardView> getSelectedCards() {
        return selectedCards;
    }
}
