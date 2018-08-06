import junit.framework.TestCase;
import models.cards.Basic;
import models.cards.Suit;

public class BasicTest extends TestCase {

    /**
     * tests construction of Basic card object
     */
    public void testBasic() {
        final Suit INITIAL_SUIT = Suit.CLUBS;
        final int INITIAL_POINT_VALUE = 5;
        final String INITIAL_NAME = "5";
        final int INITIAL_RANK = 5;

        final Basic BASIC_CARD = new Basic(INITIAL_SUIT, INITIAL_POINT_VALUE, INITIAL_NAME, INITIAL_RANK, -1);

        assertEquals(INITIAL_SUIT, BASIC_CARD.getSuit());
        assertEquals(INITIAL_POINT_VALUE, BASIC_CARD.getPointValue());
        assertEquals(INITIAL_NAME, BASIC_CARD.getName());
        assertEquals(INITIAL_RANK, BASIC_CARD.getRank());
    }

}
