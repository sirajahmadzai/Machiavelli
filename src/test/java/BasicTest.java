import junit.framework.TestCase;
import models.cards.Basic;
import models.cards.Suit;

public class BasicTest extends TestCase {

    /**
     * tests construction of Basic card object
     */
    public void testBasic() {
        Suit INITIAL_SUIT = Suit.CLUBS;
        int INITIAL_POINT_VALUE = 5;
        String INITIAL_NAME = "5";
        int INITIAL_RANK = 5;

        Basic basicCard = new Basic(INITIAL_SUIT, INITIAL_POINT_VALUE, INITIAL_NAME, INITIAL_RANK);

        assertEquals(INITIAL_SUIT, basicCard.getSuit());
        assertEquals(INITIAL_POINT_VALUE, basicCard.getPointValue());
        assertEquals(INITIAL_NAME, basicCard.getName());
        assertEquals(INITIAL_RANK, basicCard.getRank());
    }

}
