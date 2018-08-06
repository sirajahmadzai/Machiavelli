import junit.framework.TestCase;
import models.cards.Joker;
import models.cards.Suit;

public class JokerTest extends TestCase {

    /**
     * tests construction of Joker object
     */
    public void testJoker() {
        final Suit suit = Suit.JOKER;
        final int pointValue = 20;
        final String name = "Joker";
        final int rank = 15;

        final Joker JOKER_CARD = new Joker(suit, 20, name, rank, -1);

        assertEquals(suit, JOKER_CARD.getSuit());
        assertEquals(pointValue, JOKER_CARD.getPointValue());
        assertEquals(name, JOKER_CARD.getName());
        assertEquals(rank, JOKER_CARD.getRank());
    }

    public void testChangeRank() {

    }
}
