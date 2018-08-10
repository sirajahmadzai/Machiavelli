package models.cards;

import com.sun.javaws.exceptions.InvalidArgumentException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BasicTest {

    @Test
    public void testConstructor() {
        final Suit INITIAL_SUIT = Suit.CLUBS;
        final int INITIAL_POINT_VALUE = 5;
        final String INITIAL_NAME = "5";
        final int INITIAL_RANK = 5;

        final Basic BASIC_CARD;
        try {
            BASIC_CARD = new Basic(INITIAL_SUIT, INITIAL_RANK, -1);

            assertEquals(INITIAL_SUIT, BASIC_CARD.getSuit());
            assertEquals(INITIAL_POINT_VALUE, BASIC_CARD.getPointValue());
            assertEquals(INITIAL_NAME, BASIC_CARD.getName());
            assertEquals(INITIAL_RANK, BASIC_CARD.getRank());
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
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
                    e.printStackTrace();
                }
            } catch (InvalidArgumentException e) {
                e.printStackTrace();
            }
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
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
                    e.printStackTrace();
                }
            } catch (InvalidArgumentException e) {
                e.printStackTrace();
            }
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
    }
}
