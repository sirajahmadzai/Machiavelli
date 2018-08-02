
import junit.framework.TestCase;
import models.cards.Ace;
import models.cards.Suit;

public class AceTest extends TestCase {

    /**
     * tests construction of Ace object
     */
    public void testAce() {
        Suit INITIAL_SUIT = Suit.SPADES;
        int INITIAL_POINT_VALUE = 14;
        String INITIAL_NAME = "Ace";
        int INITIAL_RANK = 14;

        Ace ace = new Ace(INITIAL_SUIT, INITIAL_POINT_VALUE, INITIAL_NAME, INITIAL_RANK, -1);

        assertEquals(INITIAL_SUIT, ace.getSuit());
        assertEquals(INITIAL_POINT_VALUE, ace.getPointValue());
        assertEquals(INITIAL_NAME, ace.getName());
        assertEquals(INITIAL_RANK, ace.getRank());
    }


    public void testChangeRank() {

    }

}
