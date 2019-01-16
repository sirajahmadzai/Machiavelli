package interfaces;

import client.views.components.CardSetView;
import client.views.components.CardView;

public interface cardEventInterface {
    public CardView getCardView();

    public CardSetView getParentCardSetView();

    public void setCardView(CardView cardView);
}
