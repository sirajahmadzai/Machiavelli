package models;

import com.sun.javaws.exceptions.InvalidArgumentException;
import models.cards.*;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class PlayerTest {

    @Test
    public void testConstructor() {
        final int INITIAL_POINT_VALUE = 0;

        final int INITIAL_PLAYER_ID = 1;

        final String INITIAL_NAME = "player1";

        final ArrayList<Card> INITIAL_HAND = new ArrayList<>();

        final Player PLAYER = new Player(INITIAL_PLAYER_ID, INITIAL_NAME);

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
            final Card CARD2 = new Ace(SUIT2, RANK2, ID2);
            final Card CARD3 = new Joker(SUIT3, RANK3, ID3);
            final Card CARD4 = new Basic(SUIT4, RANK4, ID4);
            final Card CARD5 = new Basic(SUIT5, RANK5, ID5);
            final Card CARD6 = new Basic(SUIT6, RANK6, ID16);
            final Card CARD7 = new Basic(SUIT7, RANK7, ID7);

            INITIAL_HAND.add(CARD1);
            INITIAL_HAND.add(CARD2);
            INITIAL_HAND.add(CARD3);
            INITIAL_HAND.add(CARD4);
            INITIAL_HAND.add(CARD5);
            INITIAL_HAND.add(CARD6);
            INITIAL_HAND.add(CARD7);

            assertEquals("", INITIAL_PLAYER_ID, PLAYER.getPlayerID());
            assertEquals("", INITIAL_NAME, PLAYER.getName());

            PLAYER.setHand(INITIAL_HAND);
            PLAYER.setPointValue(INITIAL_POINT_VALUE);

            assertEquals("", INITIAL_HAND, PLAYER.getHand());
            assertEquals("", INITIAL_POINT_VALUE, PLAYER.getPointValue());

        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void setHand() {
    }

    @Test
    public void setPointValue() {
    }
}