package server;

import org.junit.Test;
import server.models.Player;
import server.models.cards.*;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class PlayerTest {

    @Test
    public void testConstructor() {
        final int POINT_VALUE = 0;

        final int PLAYER_ID = 1;

        final String PLAYER_NAME = "player1";

        final ArrayList<Card> PLAYER_HAND = new ArrayList<>();

        final Player PLAYER = new Player(PLAYER_ID, PLAYER_NAME);

        final Suit SUIT1 = Suit.CLUBS;
        final Suit SUIT2 = Suit.DIAMONDS;
        final Suit SUIT3 = Suit.JOKER;
        final Suit SUIT4 = Suit.HEARTS;
        final Suit SUIT5 = Suit.SPADES;
        final Suit SUIT6 = Suit.CLUBS;
        final Suit SUIT7 = Suit.DIAMONDS;

        final int RANK1 = 5;
        final int RANK2 = 14;
        final int RANK3 = 15;
        final int RANK4 = 6;
        final int RANK5 = 7;
        final int RANK6 = 8;
        final int RANK7 = 9;

        final int ID1 = 1;
        final int ID2 = 2;
        final int ID3 = 3;
        final int ID4 = 4;
        final int ID5 = 5;
        final int ID16 = 6;
        final int ID7 = 7;


        try {
            //cards for testing hand
            final Card CARD1 = new Basic(SUIT1, RANK1, ID1);
            final Card CARD2 = new Ace(SUIT2, ID2);
            final Card CARD3 = new Joker(SUIT3, ID3);
            final Card CARD4 = new Basic(SUIT4, RANK4, ID4);
            final Card CARD5 = new Basic(SUIT5, RANK5, ID5);
            final Card CARD6 = new Basic(SUIT6, RANK6, ID16);
            final Card CARD7 = new Basic(SUIT7, RANK7, ID7);

            PLAYER_HAND.add(CARD1);
            PLAYER_HAND.add(CARD2);
            PLAYER_HAND.add(CARD3);
            PLAYER_HAND.add(CARD4);
            PLAYER_HAND.add(CARD5);
            PLAYER_HAND.add(CARD6);
            PLAYER_HAND.add(CARD7);

            assertEquals("getPlayerID() != PLAYER_ID", PLAYER_ID, PLAYER.getPlayerID());
            assertEquals("getName() != PLAYER_NAME", PLAYER_NAME, PLAYER.getName());

            PLAYER.setHand(PLAYER_HAND);
            PLAYER.setPointValue(POINT_VALUE);

            assertEquals("getHand() != PLAYER_HAND", PLAYER_HAND, PLAYER.getHand());
            assertEquals("getPointValue() != POINT_VALUE", POINT_VALUE, PLAYER.getPointValue());

        } catch (IllegalArgumentException e) {
            fail("Unexpected Exception!");
        }
    }

    @Test
    public void setHand() {
        ArrayList<Card> PLAYER_HAND = new ArrayList<>();

        final Suit SUIT = Suit.CLUBS;
        final int RANK = 6;
        final int CARD_ID = 1;

        try {
            final Card CARD = new Basic(SUIT, RANK, CARD_ID);

            PLAYER_HAND.add(CARD);

            final int PLAYER_ID = 1;

            final String PLAYER_NAME = "siraj";
            final Player PLAYER = new Player(PLAYER_ID, PLAYER_NAME);

            PLAYER.setHand(PLAYER_HAND);


            assertEquals("getHand() != PLAYER_HAND", PLAYER_HAND, PLAYER.getHand());
        } catch (IllegalArgumentException e) {
            fail("Unexpected Exception!");
        }

    }

    @Test
    public void setPointValue() {
        final int PLAYER_ID = 1;
        final String PLAYER_NAME = "siraj";

        final Player PLAYER = new Player(PLAYER_ID, PLAYER_NAME);

        final int POINT_VALUE = 10;

        PLAYER.setPointValue(POINT_VALUE);

        assertEquals("getPointValue() != POINT_VALUE", POINT_VALUE, PLAYER.getPointValue());
    }
}