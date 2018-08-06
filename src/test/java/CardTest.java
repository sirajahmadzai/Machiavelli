import com.sun.javaws.exceptions.InvalidArgumentException;
import junit.framework.TestCase;
import models.cards.Basic;
import models.cards.Card;
import models.cards.Suit;

public class CardTest extends TestCase {

    public void testGetCardNameByRank() {

        final int INITIAL_RANK = 5;


        final Card INITIAL_CARD = new Basic(Suit.CLUBS, 5, "5", 5, 0);
        //cant make this one a constant
        String INITIAL_CARD_NAME = null;

        try {
            INITIAL_CARD_NAME = INITIAL_CARD.getCardNameByRank(INITIAL_RANK);
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }

        try {
            assertEquals(INITIAL_CARD_NAME, INITIAL_CARD.getCardNameByRank(INITIAL_RANK));
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }

    }

    public void testGetCardValueByRank() {
        //cant make this one final
        int INITIAL_RANK = 0;

        final Card INITIAL_CARD = new Basic(Suit.SPADES, 5, "5", 5, 1);

        try {
            INITIAL_RANK = INITIAL_CARD.getCardValueByRank(INITIAL_RANK);
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
        try {
            assertEquals(INITIAL_RANK, INITIAL_CARD.getCardValueByRank(INITIAL_RANK));
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }

    }
}
