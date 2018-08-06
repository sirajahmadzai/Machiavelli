//import junit.framework.TestCase;
//import models.Set;
//import models.cards.Basic;
//import models.cards.Card;
//import models.cards.Suit;
//
//import java.util.ArrayList;
//
//public class SetTest extends TestCase {
//
//    /**
//     * tests construction of Set object
//     */
//    public void testSet() {
//        final Card CARD1 = new Basic(Suit.CLUBS, 5, "5", 5, -1);
//        final Card CARD2 = new Basic(Suit.SPADES, 5, "5", 5, -1);
//        final Card CARD3 = new Basic(Suit.HEARTS, 5, "5", 5, -1);
//        final Card CARD4 = new Basic(Suit.DIAMONDS, 5, "5", 5, -1);
//        final ArrayList<Card> INITIAL_CARDS = new ArrayList<>();
//
//        INITIAL_CARDS.add(CARD1);
//        INITIAL_CARDS.add(CARD2);
//        INITIAL_CARDS.add(CARD3);
//        INITIAL_CARDS.add(CARD4);
//
//        final Set INITIAL_SET = new Set(INITIAL_CARDS);
//
//        assertEquals(INITIAL_CARDS, INITIAL_SET.getCards());
//    }
//}
