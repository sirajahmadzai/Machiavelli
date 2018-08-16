package server;

import com.sun.javaws.exceptions.InvalidArgumentException;
import server.models.cards.Basic;
import server.models.cards.Card;
import server.models.cards.Suit;
import org.junit.Test;
import server.models.CardSet;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CardSetTest {

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
            final ArrayList<Card> LIST_OF_CARDS = new ArrayList<>();

            LIST_OF_CARDS.add(CARD1);
            LIST_OF_CARDS.add(CARD2);
            LIST_OF_CARDS.add(CARD3);
            LIST_OF_CARDS.add(CARD4);

            final CardSet INITIAL_Card_SET = new CardSet(LIST_OF_CARDS);

            assertEquals(LIST_OF_CARDS, INITIAL_Card_SET.getCards());
        } catch (InvalidArgumentException e) {
            fail("Unexpected Exception");
        }
    }
}