package server.models.cards;

import com.sun.javaws.exceptions.InvalidArgumentException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class JokerTest {

    @Test
    public void testConstructor() {
        final Suit SUIT = Suit.JOKER;
        final int RANK = 15;
        final int CARD_ID = 1;


        try {
            final Joker JOKER_CARD = new Joker(SUIT, -1);
            final int EXPECTED_POINT_VALUE = 20;
            final String EXPECTED_NAME = "Joker";


            assertEquals("getSuit() != SUIT", SUIT, JOKER_CARD.getSuit());
            assertEquals("getPointValue() != EXPECTED_POINT_VALUE", EXPECTED_POINT_VALUE, JOKER_CARD.getPointValue());
            assertEquals("getName() != EXPECTED_NAME", EXPECTED_NAME, JOKER_CARD.getName());
            assertEquals("getRank() != RANK", RANK, JOKER_CARD.getRank());
            assertEquals("getId() != CARD_ID", CARD_ID, JOKER_CARD.getId());
        } catch (InvalidArgumentException e) {
            fail("Unexpected Exception");
        }

    }

    @Test
    public void changeRank() {
        final Suit SUIT = Suit.JOKER;
        final int RANK = 15;
        final int CARD_ID = 1;

        try {
            final Joker JOKER_CARD = new Joker(SUIT, CARD_ID);

            //VALID rank
            try {
                final int VALID_RANK = 2;
                JOKER_CARD.changeRank(VALID_RANK);

                assertEquals("getRank != VALID_RANK", VALID_RANK, JOKER_CARD.getRank());
            } catch (Card.InvalidCardRankException e) {
                fail("Unexpected Exception");
            }

            //INVALID RANK

            try {
                final int INVALID_RANK = -2;
                JOKER_CARD.changeRank(INVALID_RANK);

                fail("Invalid rank did not throw an exception");
            } catch (Card.InvalidCardRankException e) {
                //passed
            }
        } catch (InvalidArgumentException e) {
            fail("Unexpected Exception");
        }
    }
}