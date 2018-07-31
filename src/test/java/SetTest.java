import junit.framework.TestCase;
import models.Set;
import models.cards.Basic;
import models.cards.Card;
import models.cards.Suit;

import java.util.ArrayList;

public class SetTest extends TestCase {

    /**
     * tests construction of Set object
     */
    public void testSet() {
        Card card1 = new Basic(Suit.CLUBS, 5, "5", 5);
        Card card2 = new Basic(Suit.SPADES, 5, "5", 5);
        Card card3 = new Basic(Suit.HEARTS, 5, "5", 5);
        Card card4 = new Basic(Suit.DIAMONDS, 5, "5", 5);
        ArrayList<Card> INITAL_CARDS = new ArrayList<>();

        INITAL_CARDS.add(card1);
        INITAL_CARDS.add(card2);
        INITAL_CARDS.add(card3);
        INITAL_CARDS.add(card4);

        Set set = new Set(INITAL_CARDS);

        assertEquals(INITAL_CARDS, set.getCards());
    }
}
