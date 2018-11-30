package client;

import client.views.components.CardView;
import interfaces.selectionManagerInterface;
import server.models.CardSet;
import server.models.cards.Card;

import java.util.ArrayList;


public class SelectionManager implements selectionManagerInterface {
    /**
     * PRIVATES
     */
    private ArrayList<CardView> selectedCards;
    private CardSet selectedCardsSet;

    /**
     * CONSTRUCTOR
     */
    public SelectionManager() {
        selectedCards = new ArrayList<>();
        selectedCardsSet = new CardSet();
    }

    /**
     * @param cardView
     */
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

    /**
     * @param cardView
     */
    public void selectCard(CardView cardView) {
        selectedCards.add(cardView);
        selectedCardsSet.addCard(cardView.getCard());
        cardView.setSelected(true);
    }

    /**
     * @param cardView
     */
    public void deselectCard(CardView cardView) {
        selectedCards.remove(cardView);
        selectedCardsSet.removeCard(cardView.getCard());
        cardView.setSelected(false);
    }

    /**
     *
     */
    public void deselectAll() {
        while (!selectedCards.isEmpty()) {
            deselectCard(selectedCards.get(0));
        }
    }


    /**
     *
     */
    public void clearSelections() {
        for (CardView cardView : selectedCards) {
            cardView.setSelected(false);
        }
        selectedCards.clear();
        selectedCardsSet.removeAllCards();
    }

    /**
     * @return
     */
    public boolean isEmpty() {
        return selectedCards.isEmpty();
    }

    /**
     * @return
     */
    public CardSet getSelectedCardsSet() {
        return selectedCardsSet;
    }

    /**
     * @return
     */
    public ArrayList<CardView> getSelectedCards() {
        return selectedCards;
    }
}
