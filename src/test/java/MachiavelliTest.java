import junit.framework.TestCase;
import models.Machiavelli;
import models.Player;
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

    }

    public void testAppendCard() {

    }

    public void testVerifyTable() {

    }

    public void testDealHands() {

    }

    public void testPickPlayerTurn() {
        int INITIAL_NUM = 1;
        Player p1 = new Player(0, "Siraj");
        Player p2 = new Player(1, "Steph");
        Player p3 = new Player(2, "player3");
        Player p4 = new Player(3, "player4");
        ArrayList<Player> players = new ArrayList<>();

        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);


        Machiavelli game = new Machiavelli(4);
        INITIAL_NUM = game.getRandomPlayer().getPlayerID();

        if (INITIAL_NUM == 0) assertEquals(players.get(INITIAL_NUM), game.getRandomPlayer());
        else if (INITIAL_NUM == 1) assertEquals(players.get(INITIAL_NUM), game.getRandomPlayer());
        else if (INITIAL_NUM == 2) assertEquals(players.get(INITIAL_NUM), game.getRandomPlayer());
        else if (INITIAL_NUM == 3) assertEquals(players.get(INITIAL_NUM), game.getRandomPlayer());

    }

}
