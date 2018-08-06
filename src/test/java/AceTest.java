
import junit.framework.TestCase;
import models.cards.Ace;
import models.cards.Suit;

public class AceTest extends TestCase {

    /**
     * tests construction of Ace object
     */
    public void testAce() {
        final Suit INITIAL_SUIT = Suit.SPADES;
        final int INITIAL_POINT_VALUE = 14;
        final String INITIAL_NAME = "Ace";
        final int INITIAL_RANK = 14;

        final Ace ACE_CARD = new Ace(INITIAL_SUIT, INITIAL_POINT_VALUE, INITIAL_NAME, INITIAL_RANK, -1);

        assertEquals(INITIAL_SUIT, ACE_CARD.getSuit());
        assertEquals(INITIAL_POINT_VALUE, ACE_CARD.getPointValue());
        assertEquals(INITIAL_NAME, ACE_CARD.getName());
        assertEquals(INITIAL_RANK, ACE_CARD.getRank());
    }


    public void testChangeRank() {

    }

}
