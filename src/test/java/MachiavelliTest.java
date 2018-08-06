import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import junit.framework.TestCase;
import models.Machiavelli;
import models.Player;
import models.Set;
import models.Table;
import models.cards.Basic;
import models.cards.Card;
import models.cards.Suit;
import sun.management.counter.perf.PerfLongArrayCounter;

import java.util.ArrayList;
import java.util.Random;

public class MachiavelliTest extends TestCase {

    /**
     * tests construction of Machiavelli object
     */
    public void testMachiavelli() {

        final Table INITIAL_TABLE = new Table();

        final ArrayList<Player> INITIAL_LIST_OF_PLAYERS = new ArrayList<>();

        final int NUM_OF_PLAYERS = 2;

        final Machiavelli INITIAL_GAME = new Machiavelli(NUM_OF_PLAYERS);

        INITIAL_GAME.setTable(INITIAL_TABLE);
        INITIAL_GAME.setPlayers(INITIAL_LIST_OF_PLAYERS);

        assertEquals(INITIAL_TABLE, INITIAL_GAME.getTable());
        assertEquals(INITIAL_LIST_OF_PLAYERS, INITIAL_GAME.getPlayers());


    }

    public void testDrawCardFromDeck() {

        final Machiavelli INITIAL_GAME = new Machiavelli(2);

        final Player PLAYER1 = new Player(0, "siraj");
        final Player PLAYER2 = new Player(1, "steph");

        final ArrayList<Player> INITIAL_PLAYERS = new ArrayList<>();

        INITIAL_PLAYERS.add(PLAYER1);
        INITIAL_PLAYERS.add(PLAYER2);

        final Table INITIAL_TABLE = new Table();
        final Basic CARD1 = new Basic(Suit.CLUBS, 10, "Jack", 10, -1);
        final Basic CARD2 = new Basic(Suit.HEARTS, 10, "Queen", 10, -1);

        INITIAL_TABLE.getDeck().add(CARD1);
        INITIAL_TABLE.getDeck().add(CARD2);

        assertEquals(CARD1, INITIAL_GAME.drawCardFromDeck());
        assertEquals(CARD2, INITIAL_GAME.drawCardFromDeck());


    }

    public void testPlayCard() {
       final int INITIAL_NUM_OF_PLAYERS = 2;
        final int INITIAL_PLAYER_ID = 1;

        final int INITIAL_CARD_ID = 1;

        final Machiavelli INITIAL_GAME = new Machiavelli(INITIAL_NUM_OF_PLAYERS);


        Card card = INITIAL_GAME.playCard(INITIAL_PLAYER_ID, INITIAL_CARD_ID);

        assertEquals(INITIAL_CARD_ID, card.getId());
    }

    public void testPlayCardFromPlayArea() {

    }

    public void testMergeSet() {

    }

    public void testSplitSet() {

    }

    public void testSplitSetRemove() {

    }

    public void testPrependCard() {
        final ArrayList<Card> INITIAL_LIST_OF_CARDS = new ArrayList<>();

        final Set INITIAL_SET = new Set(INITIAL_LIST_OF_CARDS);

        final Card CARD_TO_ADD = new Basic(Suit.DIAMONDS, 5, "5", 5, 1);

        final Machiavelli INITIAL_GAME = new Machiavelli(2);

        INITIAL_GAME.prependCard(INITIAL_SET, CARD_TO_ADD);

        assertEquals(CARD_TO_ADD, INITIAL_SET.getCards().get(0));

    }

    public void testAppendCard() {
        final ArrayList<Card> INITIAL_LIST_OF_CARDS = new ArrayList<>();

        final Set INITIAL_SET = new Set(INITIAL_LIST_OF_CARDS);

        final Card CARD_TO_ADD = new Basic(Suit.DIAMONDS, 5, "5", 5, 1);

        final Machiavelli INITIAL_GAME = new Machiavelli(2);

        INITIAL_GAME.appendCard(INITIAL_SET, CARD_TO_ADD);

        assertEquals(CARD_TO_ADD, INITIAL_SET.getCards().get(INITIAL_SET.getCards().size() - 1));
    }

    public void testVerifyTable() {

    }

    public void testDealHands() {

    }

    public void testGetRandomPlayer() {

    }

}
