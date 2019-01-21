package server.models.cards;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class AceTest {

    /**
     * tests construction of Ace object
     */
    @Test
    public void testConstructor() {
        final Suit VALID_SUIT = Suit.SPADES;
        final int VALID_RANK = 14;
        final int VALID_CARD_ID = 1;

        //testing Valid
        try {
            final Ace ACE_CARD = new Ace(VALID_SUIT, VALID_CARD_ID);
            final int EXPECTED_POINT_VALUE = 15;
            final String EXPECTED_NAME = "Ace";
            assertEquals("getSuit() != INITIAL", VALID_SUIT, ACE_CARD.getSuit());
            assertEquals("getPointValue != EXPECTED", EXPECTED_POINT_VALUE, ACE_CARD.getPointValue());
            assertEquals("getPointValue != EXPECTED", EXPECTED_NAME, ACE_CARD.getName());
            assertEquals("getRank() != INITIAL", VALID_RANK, ACE_CARD.getRank());
            assertEquals("getId() != INITIAL", VALID_CARD_ID, ACE_CARD.getId());

        } catch (IllegalArgumentException e) {
            fail("Invalid Argument");
        }

        //testing INVALID Suit
        try {
            final Ace ACE_CARD = new Ace(null, VALID_CARD_ID);
            fail("null suit did not throw an exception");

        } catch (IllegalArgumentException e) {
            //passed
        }

        //testing INVALID rank
        try {
            final int INVALID_RANK = 2;
            final Ace ACE_CARD = new Ace(VALID_SUIT, VALID_CARD_ID);
            fail("Invalid rank did not throw an exception");

        } catch (IllegalArgumentException e) {
            //passed
        }

        //testing INVALID Id
        try {
            final int INVALID_ID = 2;
            final Ace ACE_CARD = new Ace(VALID_SUIT, INVALID_ID);
            fail("Invalid Id did not throw an exception");
        } catch (IllegalArgumentException e) {
            //passed
        }
    }

    @Test
    public void changeRank() {
        final Suit INITIAL_SUIT = Suit.DIAMONDS;
        final int INITIAL_RANK = 2;
        final int INITIAL_ID = 1;

        final Ace ACE_CARD;

        try {
            ACE_CARD = new Ace(INITIAL_SUIT, INITIAL_ID);

            final int INVALID_BOUND_0 = 0;
            try {
                ACE_CARD.changeRank(INVALID_BOUND_0);
                fail("Invalid rank did not throw an exception");
            } catch (Card.InvalidCardRankException e) {
                //passed
            }
            final int INVALID_BOUND_2 = 2;

            try {
                ACE_CARD.changeRank(INVALID_BOUND_2);

                fail("Invalid rank did not throw an exception");
            } catch (Card.InvalidCardRankException e) {
                //passed
            }

            final int INVALID_BOUND_13 = 13;

            try {
                ACE_CARD.changeRank(INVALID_BOUND_13);

                fail("Invalid rank did not throw an exception");
            } catch (Card.InvalidCardRankException e) {
                //passed
            }

            final int INVALID_BOUND_15 = 15;

            try {
                ACE_CARD.changeRank(INVALID_BOUND_15);

                fail("Invalid rank did not throw an exception");
            } catch (Card.InvalidCardRankException e) {
                //passed
            }

            final int VALID_BOUND_1 = 1;

            try {
                ACE_CARD.changeRank(VALID_BOUND_1);

                assertEquals("getRank != VALID_BOUND_1", VALID_BOUND_1, ACE_CARD.getRank());
            } catch (Card.InvalidCardRankException e) {
                fail("Unexpected exception");
            }

            final int VALID_BOUND_14 = 14;

            try {
                ACE_CARD.changeRank(VALID_BOUND_14);

                assertEquals("getRank != VALID_BOUND_14", VALID_BOUND_14, ACE_CARD.getRank());
            } catch (Card.InvalidCardRankException e) {
                fail("Unexpected exception");
            }

            final int INVALID_VALUE_8 = 8;

            try {
                ACE_CARD.changeRank(INVALID_VALUE_8);

                fail("Invalid ran did not throw an exception");
            } catch (Card.InvalidCardRankException e) {
                //passed
            }

        } catch (IllegalArgumentException e) {
            fail("the constructor screwed up");
        }


    }
}