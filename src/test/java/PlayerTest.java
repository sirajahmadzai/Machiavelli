import junit.framework.TestCase;
import models.Player;
import models.cards.*;

import java.util.ArrayList;

public class PlayerTest extends TestCase {

    /**
     * tests construction of Player object
     */
    public void testPlayer() {


        final int INITIAL_POINT_VALUE = 0;

        final int INITIAL_PLAYER_ID = 1;

        final String INITIAL_NAME = "player1";

        final ArrayList<Card> INITIAL_HAND = new ArrayList<>();

        final Player PLAYER = new Player(INITIAL_PLAYER_ID, INITIAL_NAME);

        //cards for testing hand
        final Card CARD1 = new Basic(Suit.CLUBS, 5, "5", 5, -1);
        final Card CARD2 = new Ace(Suit.CLUBS, 14, "Ace", 14, -1);
        final Card CARD3 = new Joker(Suit.JOKER, 15, "Joker", 15, -1);
        final Card CARD4 = new Basic(Suit.CLUBS, 6, "6", 6, -1);
        final Card CARD5 = new Basic(Suit.CLUBS, 7, "7", 7, -1);
        final Card CARD6 = new Basic(Suit.CLUBS, 8, "8", 8, -1);
        final Card CARD7 = new Basic(Suit.CLUBS, 9, "9", 9, -1);

        INITIAL_HAND.add(CARD1);
        INITIAL_HAND.add(CARD2);
        INITIAL_HAND.add(CARD3);
        INITIAL_HAND.add(CARD4);
        INITIAL_HAND.add(CARD5);
        INITIAL_HAND.add(CARD6);
        INITIAL_HAND.add(CARD7);

        assertEquals(INITIAL_PLAYER_ID, PLAYER.getPlayerID());
        assertEquals(INITIAL_NAME, PLAYER.getName());

        PLAYER.setHand(INITIAL_HAND);
        PLAYER.setPointValue(INITIAL_POINT_VALUE);

        assertEquals(INITIAL_HAND, PLAYER.getHand());
        assertEquals(INITIAL_POINT_VALUE, PLAYER.getPointValue());

    }
}
