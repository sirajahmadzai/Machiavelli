import junit.framework.TestCase;
import models.Player;
import models.cards.*;

import java.util.ArrayList;

public class PlayerTest extends TestCase {

    /**
     * tests construction of Player object
     */
    public void testPlayer() {

        //cards for testing hand
        Card card1 = new Basic(Suit.CLUBS, 5, "5", 5, -1);
        Card card2 = new Ace(Suit.CLUBS, 14, "Ace", 14, -1);
        Card card3 = new Joker(Suit.JOKER, 15, "Joker", 15, -1);
        Card card4 = new Basic(Suit.CLUBS, 6, "6", 6, -1);
        Card card5 = new Basic(Suit.CLUBS, 7, "7", 7, -1);
        Card card6 = new Basic(Suit.CLUBS, 8, "8", 8, -1);
        Card card7 = new Basic(Suit.CLUBS, 9, "9", 9, -1);


        int INITIAL_PLAYER_ID = 1;

        String INITIAL_NAME = "player1";

        ArrayList<Card> INITIAL_HAND = new ArrayList<>();

        INITIAL_HAND.add(card1);
        INITIAL_HAND.add(card2);
        INITIAL_HAND.add(card3);
        INITIAL_HAND.add(card4);
        INITIAL_HAND.add(card5);
        INITIAL_HAND.add(card6);
        INITIAL_HAND.add(card7);

        int INITIAL_POINT_VALUE = 0;

        Player player = new Player(INITIAL_PLAYER_ID, INITIAL_NAME);

        assertEquals(INITIAL_PLAYER_ID, player.getPlayerID());
        assertEquals(INITIAL_NAME, player.getName());

        player.setHand(INITIAL_HAND);
        player.setPointValue(INITIAL_POINT_VALUE);

        assertEquals(INITIAL_HAND, player.getHand());
        assertEquals(INITIAL_POINT_VALUE, player.getPointValue());

    }
}
