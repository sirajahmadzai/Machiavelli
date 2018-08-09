package models;

import com.sun.javaws.exceptions.InvalidArgumentException;
import models.cards.Basic;
import models.cards.Card;
import models.cards.Suit;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class SetTest {

    @Test
    public void testConstructor() {
        try {
            final Suit SUIT1 = Suit.CLUBS;
            final Suit SUIT2 = Suit.DIAMONDS;
            final Suit SUIT3 = Suit.JOKER;
            final Suit SUIT4 = Suit.HEARTS;

            final int RANK1 = 5;
            final int RANK2 = 14;
            final int RANK3 = 15;
            final int RANK4 = 6;

            final int ID1 = 1;
            final int ID2 = 2;
            final int ID3 = 3;
            final int ID4 = 4;

            final Card CARD1 = new Basic(SUIT1, RANK1, ID1);
            final Card CARD2 = new Basic(SUIT2, RANK2, ID2);
            final Card CARD3 = new Basic(SUIT3, RANK3, ID3);
            final Card CARD4 = new Basic(SUIT4, RANK4, ID4);
            final ArrayList<Card> INITIAL_CARDS = new ArrayList<>();

            INITIAL_CARDS.add(CARD1);
            INITIAL_CARDS.add(CARD2);
            INITIAL_CARDS.add(CARD3);
            INITIAL_CARDS.add(CARD4);

            final Set INITIAL_SET = new Set(INITIAL_CARDS);

            assertEquals(INITIAL_CARDS, INITIAL_SET.getCards());
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }

    }

}