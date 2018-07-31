import junit.framework.TestCase;
import models.cards.Joker;
import models.cards.Suit;

public class JokerTest extends TestCase {

    /**
     * tests construction of Joker object
     */
    public void testJoker() {
        Suit suit = Suit.JOKER;
        int pointValue = 20;
        String name = "Joker";
        int rank = 15;

        Joker joker = new Joker(suit, 20, name, rank);

        assertEquals(suit, joker.getSuit());
        assertEquals(pointValue, joker.getPointValue());
        assertEquals(name, joker.getName());
        assertEquals(rank, joker.getRank());
    }

    public void testChangeRank() {

    }
}
