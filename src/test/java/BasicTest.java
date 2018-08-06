//import com.sun.javaws.exceptions.InvalidArgumentException;
//import junit.framework.TestCase;
//import models.cards.Basic;
//import models.cards.Card;
//import models.cards.Suit;
//
//public class BasicTest extends TestCase {
//
//    /**
//     * tests construction of Basic card object
//     */
//    public void testBasic() {
//        final Suit INITIAL_SUIT = Suit.CLUBS;
//        final int INITIAL_POINT_VALUE = 5;
//        final String INITIAL_NAME = "5";
//        final int INITIAL_RANK = 5;
//
//        final Basic BASIC_CARD = new Basic(INITIAL_SUIT, INITIAL_POINT_VALUE, INITIAL_NAME, INITIAL_RANK, -1);
//
//        assertEquals(INITIAL_SUIT, BASIC_CARD.getSuit());
//        assertEquals(INITIAL_POINT_VALUE, BASIC_CARD.getPointValue());
//        assertEquals(INITIAL_NAME, BASIC_CARD.getName());
//        assertEquals(INITIAL_RANK, BASIC_CARD.getRank());
//    }
//
//    public void testGetCardNameByRank() {
//
//        final int INITIAL_RANK = 5;
//
//
//        final Card INITIAL_CARD = new Basic(Suit.CLUBS, 5, "5", 5, 0);
//        //cant make this one a constant
//        String INITIAL_CARD_NAME = null;
//
//        try {
//            INITIAL_CARD_NAME = INITIAL_CARD.getCardNameByRank(INITIAL_RANK);
//        } catch (InvalidArgumentException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            assertEquals(INITIAL_CARD_NAME, INITIAL_CARD.getCardNameByRank(INITIAL_RANK));
//        } catch (InvalidArgumentException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public void testGetCardValueByRank() {
//        //cant make this one final
//        int INITIAL_RANK = 0;
//
//        final Card INITIAL_CARD = new Basic(Suit.SPADES, 5, "5", 5, 1);
//
//        try {
//            INITIAL_RANK = INITIAL_CARD.getCardValueByRank(INITIAL_RANK);
//        } catch (InvalidArgumentException e) {
//            e.printStackTrace();
//        }
//        try {
////            assertEquals(INITIAL_RANK, INITIAL_CARD.getCardValueByRank(INITIAL_RANK));
//        } catch (InvalidArgumentException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//}
