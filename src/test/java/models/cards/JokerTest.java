package models.cards;

import com.sun.javaws.exceptions.InvalidArgumentException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class JokerTest {

    @Test
    public void testConstructor() {
        final Suit INITIAL_SUIT = Suit.JOKER;
        final int INITIAL_RANK = 15;
        final int INITIAL_ID = 1;


        try {
            final Joker JOKER_CARD = new Joker(INITIAL_SUIT, INITIAL_RANK, -1);
            final int EXPECTED_POINT_VALUE = 20;
            final String EXPECTED_NAME = "Joker";


            assertEquals("getSuit() != INITIAL_SUIT", INITIAL_SUIT, JOKER_CARD.getSuit());
            assertEquals("getPointValue() != EXPECTED_POINT_VALUE", EXPECTED_POINT_VALUE, JOKER_CARD.getPointValue());
            assertEquals("getName() != EXPECTED_NAME", EXPECTED_NAME, JOKER_CARD.getName());
            assertEquals("getRank() != INITIAL_RANK", INITIAL_RANK, JOKER_CARD.getRank());
            assertEquals("getId() != INITIAL_ID", INITIAL_ID, JOKER_CARD.getId());
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void changeRank() {
        final Suit INITIAL_SUIT = Suit.JOKER;
        final int INITIAL_RANK = 15;
        final int INITIAL_ID = 1;

        try {
            final Joker INITIAL_JOKER_CARD = new Joker(INITIAL_SUIT, INITIAL_RANK, INITIAL_ID);

            //VALID rank
            try {
                final int VALID_RANK = 2;
                INITIAL_JOKER_CARD.changeRank(VALID_RANK);

                assertEquals("getRank != VALID_RANK", VALID_RANK, INITIAL_JOKER_CARD.getRank());
            } catch (Card.InvalidCardRankException e) {
                fail("Unexpected Exception");
            }

            //INVALID RANK

            try {
                final int INVALID_RANK = -2;
                INITIAL_JOKER_CARD.changeRank(INVALID_RANK);

                fail("Invalid rank did not throw an exception");
            } catch (Card.InvalidCardRankException e) {
                //passed
            }
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
    }
}