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

        Table INITIAL_TABLE = new Table();

        ArrayList<Player> INITIAL_LIST_OF_PLAYERS = new ArrayList<>();

        int INITIAL_NUM_OF_PLAYERS = 2;

        Machiavelli game = new Machiavelli(2);

        game.setTable(INITIAL_TABLE);
        game.setPlayers(INITIAL_LIST_OF_PLAYERS);

        assertEquals(INITIAL_TABLE, game.getTable());
        assertEquals(INITIAL_LIST_OF_PLAYERS, game.getPlayers());


    }

    public void testDrawCardFromDeck() {

        Machiavelli game = new Machiavelli(2);

        Player p1 = new Player(0, "siraj");
        Player p2 = new Player(1, "steph");

        ArrayList<Player> players = new ArrayList<>();

        players.add(p1);
        players.add(p2);

        Table table = new Table();
        Basic card1 = new Basic(Suit.CLUBS, 10, "Jack", 10, -1);
        Basic card2 = new Basic(Suit.HEARTS, 10, "Queen", 10, -1);

        table.getDeck().add(card1);
        table.getDeck().add(card2);

        assertEquals(card1, game.drawCardFromDeck());
        assertEquals(card2, game.drawCardFromDeck());


    }

    public void testPlayCard() {
        int INITIAL_NUM_OF_PLAYERS = 2;
        int INITIAL_PLAYER_ID = 1;

        int INITIAL_CARD_ID = 1;

        Machiavelli game = new Machiavelli(INITIAL_NUM_OF_PLAYERS);


        Card card = game.playCard(INITIAL_PLAYER_ID, INITIAL_CARD_ID);

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
        ArrayList<Card> INITIAL_LIST_OF_CARDS = new ArrayList<>();

        Set INITIAL_SET = new Set(INITIAL_LIST_OF_CARDS);

        Card CARD_TO_ADD = new Basic(Suit.DIAMONDS, 5, "5", 5, 1);

        Machiavelli game = new Machiavelli(2);

        game.appendCard(INITIAL_SET, CARD_TO_ADD);

        assertEquals(CARD_TO_ADD, INITIAL_SET.getCards().get(0));

    }

    public void testAppendCard() {
        ArrayList<Card> INITIAL_LIST_OF_CARDS = new ArrayList<>();

        Set INITIAL_SET = new Set(INITIAL_LIST_OF_CARDS);

        Card CARD_TO_ADD = new Basic(Suit.DIAMONDS, 5, "5", 5, 1);

        Machiavelli game = new Machiavelli(2);

        game.appendCard(INITIAL_SET, CARD_TO_ADD);

        assertEquals(CARD_TO_ADD, INITIAL_SET.getCards().get(INITIAL_SET.getCards().size() - 1));
    }

    public void testVerifyTable() {

    }

    public void testDealHands() {

    }

    public void testGetRandomPlayer() {

    }

}
