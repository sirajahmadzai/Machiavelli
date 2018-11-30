package interfaces;

import client.views.components.CardView;
import server.models.CardSet;

import java.util.ArrayList;

public interface selectionManagerInterface {

    public void addCard(CardView cardView);
    public void selectCard(CardView cardView);
    public void deselectCard(CardView cardView);
    public void deselectAll();
    public void clearSelections();
    public boolean isEmpty();
    public CardSet getSelectedCardsSet();
    public ArrayList<CardView> getSelectedCards();
}
