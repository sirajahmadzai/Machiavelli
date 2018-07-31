
import junit.framework.TestCase;
import models.cards.Ace;
import models.cards.Suit;

public class AceTest extends TestCase {

    public void testAce(){
        Suit INITIAL_SUIT = Suit.SPADES;
        int INITIAL_POINT_VALUE = 14;
        String INITIAL_NAME = "Ace";
        int INITIAL_RANK = 14;

        Ace ace = new Ace(INITIAL_SUIT, INITIAL_POINT_VALUE, INITIAL_NAME, INITIAL_RANK);

        assertEquals(INITIAL_SUIT, ace.getSuit());
        assertEquals(INITIAL_POINT_VALUE, ace.getPointValue());
        assertEquals(INITIAL_NAME, ace.getName());
        assertEquals(INITIAL_RANK, ace.getRank());
    }

    public void testGetSuit(){
        Suit suit = Suit.HEARTS;
        Ace ace = new Ace(suit, 15, "Ace", 14);

        assertEquals(suit, ace.getSuit());
    }



    public void testChangeRank(){

    }

}
