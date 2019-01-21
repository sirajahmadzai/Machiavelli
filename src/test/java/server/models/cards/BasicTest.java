package server.models.cards;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class BasicTest {

    @Test
    public void testConstructor() {
        final Suit SUIT = Suit.CLUBS;
        final int RANK = 5;
        final int CARD_ID = 1;

        final Basic BASIC_CARD;
        try {
            BASIC_CARD = new Basic(SUIT, RANK, CARD_ID);

            assertEquals(SUIT, BASIC_CARD.getSuit());
            assertEquals(RANK, BASIC_CARD.getRank());
        } catch (IllegalArgumentException e) {
            fail("Unexpected Exception");
        }
    }
}

//    @Test
//    public void testGetCardNameByRank() {
//        final int INITIAL_RANK = 5;
//
//
//        final Card INITIAL_CARD;
//        try {
//            final Suit SUIT = Suit.CLUBS;
//            final int RANK = 5;
//            final int CARD_ID = 1;
//            final String IMG_URL = "5c.gif";
//            INITIAL_CARD = new Basic(SUIT, RANK, CARD_ID);
//
//            try {
//                final String CARD_NAME = INITIAL_CARD.getCardNameByInitialRank(INITIAL_RANK);
//
//                try {
//                    assertEquals(CARD_NAME, INITIAL_CARD.getCardNameByInitialRank(INITIAL_RANK));
//                } catch (IllegalArgumentException e) {
//                    fail("Unexpected Exception");
//                }
//            } catch (IllegalArgumentException e) {
//                fail("Unexpected Exception");
//            }
//        } catch (IllegalArgumentException e) {
//            fail("Unexpected Exception");
//        }
//    }
//
//    @Test
//    public void testGetCardValueByRank() {
//        //cant make this one final
//        int CARD_RANK = 0;
//
//        final Card INITIAL_CARD;
//        try {
//            final Suit SUIT = Suit.CLUBS;
//            final int RANK = 5;
//            final int CARD_ID = 1;
//            final String IMG_URL = "5c.gif";
//            INITIAL_CARD = new Basic(SUIT, RANK, CARD_ID, IMG_URL);
//
//            try {
//                CARD_RANK = INITIAL_CARD.getCardValueByInitialRank(CARD_RANK);
//
//                try {
//                    assertEquals(CARD_RANK, INITIAL_CARD.getCardValueByInitialRank(CARD_RANK));
//                } catch (IllegalArgumentException e) {
//                    fail("Unexpected Exception");
//                }
//            } catch (IllegalArgumentException e) {
//                fail("Unexpected Exception");
//            }
//        } catch (IllegalArgumentException e) {
//            fail("Unexpected Exception");
//        }
//    }
//}