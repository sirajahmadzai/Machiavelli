package server.models.cards;

import com.sun.javaws.exceptions.InvalidArgumentException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class BasicTest {

    @Test
    public void testConstructor() {
        final Suit SUIT = Suit.CLUBS;
        final int POINT_VALUE = 5;
        final String CARD_NAME = "5";
        final int RANK = 5;

        final Basic BASIC_CARD;
        try {
            BASIC_CARD = new Basic(SUIT, RANK, -1);

            assertEquals(SUIT, BASIC_CARD.getSuit());
            assertEquals(POINT_VALUE, BASIC_CARD.getPointValue());
            assertEquals(CARD_NAME, BASIC_CARD.getName());
            assertEquals(RANK, BASIC_CARD.getRank());
        } catch (InvalidArgumentException e) {
            fail("Unexpected Exception");
        }
    }


    @Test
    public void testGetCardNameByRank() {
        final int INITIAL_RANK = 5;


        final Card INITIAL_CARD;
        try {
            INITIAL_CARD = new Basic(Suit.CLUBS, 5, 0);

            try {
                final String INITIAL_CARD_NAME = INITIAL_CARD.getCardNameByInitialRank(INITIAL_RANK);

                try {
                    assertEquals(INITIAL_CARD_NAME, INITIAL_CARD.getCardNameByInitialRank(INITIAL_RANK));
                } catch (InvalidArgumentException e) {
                    fail("Unexpected Exception");
                }
            } catch (InvalidArgumentException e) {
                fail("Unexpected Exception");
            }
        } catch (InvalidArgumentException e) {
            fail("Unexpected Exception");
        }
    }

    @Test
    public void testGetCardValueByRank() {
        //cant make this one final
        int INITIAL_RANK = 0;

        final Card INITIAL_CARD;
        try {
            INITIAL_CARD = new Basic(Suit.SPADES, 5, 1);

            try {
                INITIAL_RANK = INITIAL_CARD.getCardValueByInitialRank(INITIAL_RANK);

                try {
                    assertEquals(INITIAL_RANK, INITIAL_CARD.getCardValueByInitialRank(INITIAL_RANK));
                } catch (InvalidArgumentException e) {
                    fail("Unexpected Exception");
                }
            } catch (InvalidArgumentException e) {
                fail("Unexpected Exception");
            }
        } catch (InvalidArgumentException e) {
            fail("Unexpected Exception");
        }
    }
}