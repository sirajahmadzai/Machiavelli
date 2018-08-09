package models;

import com.sun.javaws.exceptions.InvalidArgumentException;
import models.cards.Basic;
import models.cards.Card;
import models.cards.Suit;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MachiavelliTest {

    @Test
    public void testConstructor() {
        final Table INITIAL_TABLE = new Table();

        final ArrayList<Player> INITIAL_LIST_OF_PLAYERS = new ArrayList<>();

        final int NUM_OF_PLAYERS = 2;

        final Machiavelli INITIAL_GAME = new Machiavelli(NUM_OF_PLAYERS);

        INITIAL_GAME.setTable(INITIAL_TABLE);
        INITIAL_GAME.setPlayers(INITIAL_LIST_OF_PLAYERS);

        assertEquals("getTable() != INITIAL_TABLE", INITIAL_TABLE, INITIAL_GAME.getTable());
        assertEquals("getPlayers() != INITIAL_LIST_OF_PLAYERS", INITIAL_LIST_OF_PLAYERS, INITIAL_GAME.getPlayers());
    }


    @Test
    public void setTable() {
        final Table TABLE = new Table();

        final int NUM_OF_PLAYERS = 2;

        final Machiavelli GAME = new Machiavelli(NUM_OF_PLAYERS);

        GAME.setTable(TABLE);

        assertEquals("getTable != TABLE", TABLE, GAME.getTable());
    }

    @Test
    public void setPlayers() {
        final int PLAYER_ID = 1;
        final String PLAYER_NAME = "PLAYER1";
        final Player PLAYER = new Player(PLAYER_ID, PLAYER_NAME);

        final ArrayList<Player> PLAYERS = new ArrayList<>();
        PLAYERS.add(PLAYER);

        final int NUM_OF_PLAYERS = 2;
        final Machiavelli GAME = new Machiavelli(NUM_OF_PLAYERS);
        GAME.setPlayers(PLAYERS);

        assertEquals("getPlayers != PLAYERS", PLAYERS, GAME.getPlayers());

    }

    @Test
    public void drawCardFromDeck() {

        final int NUM_OF_PLAYERS = 2;
        final Machiavelli GAME = new Machiavelli(NUM_OF_PLAYERS);

        final int PLAYER1_ID = 0;
        final int PLAYER2_ID = 1;

        final String PLAYER1_NAME = "siraj";
        final String PLAYER2_NAME = "steph";

        final Player PLAYER1 = new Player(PLAYER1_ID, PLAYER1_NAME);
        final Player PLAYER2 = new Player(PLAYER2_ID, PLAYER2_NAME);

        final ArrayList<Player> PLAYERS = new ArrayList<>();

        PLAYERS.add(PLAYER1);
        PLAYERS.add(PLAYER2);

        GAME.setPlayers(PLAYERS);

        final Table TABLE = new Table();

        GAME.setTable(TABLE);
        final Basic CARD1;
        try {
            final Suit SUIT1 = Suit.CLUBS;
            final Suit SUIT2 = Suit.HEARTS;

            final int RANK1 = 10;
            final int RANK2 = 9;

            final int CARD_ID_1 = 1;
            final int CARD_ID_2 = 2;

            CARD1 = new Basic(SUIT1, RANK1, CARD_ID_1);
            final Basic CARD2 = new Basic(SUIT2, RANK2, CARD_ID_2);


            TABLE.getDeck().add(CARD1);
            TABLE.getDeck().add(CARD2);

            assertEquals("drawCardFromDeck() != CARD1", CARD2, GAME.drawCardFromDeck());
            assertEquals("drawCardFromDeck() != CARD2", CARD1, GAME.drawCardFromDeck());
        } catch (InvalidArgumentException e) {
            fail("Unexpected Exception");
        }
    }

    @Test
    public void playCard() {
        final int INITIAL_NUM_OF_PLAYERS = 2;

        final int INITIAL_PLAYER_ID = 1;

        final int INITIAL_CARD_ID = 1;

        final Suit SUIT = Suit.CLUBS;

        final int RANK = 5;

        final String PLAYER_NAME = "PLAYER1";

        final Player PLAYER1 = new Player(INITIAL_PLAYER_ID, PLAYER_NAME);

        final ArrayList<Player> players = new ArrayList<>();

        players.add(PLAYER1);

        try {

            final Card EXPECTED_CARD = new Basic(SUIT, RANK, INITIAL_CARD_ID);

            final Machiavelli GAME = new Machiavelli(INITIAL_NUM_OF_PLAYERS);

            GAME.setPlayers(players);

            final int INDEX_OF_PLAYER1 = 0;

            final int INDEX_OF_EXPECTED_CARD = 0;

            GAME.getPlayers().get(INDEX_OF_PLAYER1).getHand().add(EXPECTED_CARD);

            assertEquals(EXPECTED_CARD, GAME.playCard(INDEX_OF_PLAYER1, INDEX_OF_EXPECTED_CARD));
        } catch (InvalidArgumentException e) {
            fail("Unexpected Exception");
        }


    }

    @Test
    public void playCardFromPlayArea() {

        final int INITIAL_NUM_OF_PLAYERS = 2;

        final int INITIAL_PLAYER_ID = 1;

        final int INITIAL_CARD_ID = 1;

        final Suit SUIT = Suit.CLUBS;

        final int RANK = 5;

        final String PLAYER_NAME = "PLAYER1";

        final Player PLAYER1 = new Player(INITIAL_PLAYER_ID, PLAYER_NAME);

        final ArrayList<Player> players = new ArrayList<>();

        players.add(PLAYER1);

        try {

            final Card EXPECTED_CARD = new Basic(SUIT, RANK, INITIAL_CARD_ID);

            final ArrayList<Card> LIST_OF_CARDS = new ArrayList<>();

            LIST_OF_CARDS.add(EXPECTED_CARD);

            final Machiavelli GAME = new Machiavelli(INITIAL_NUM_OF_PLAYERS);

            GAME.setPlayers(players);

            final Table TABLE = new Table();

            final Set SET = new Set(LIST_OF_CARDS);

            final ArrayList<Set> LIST_OF_SETS = new ArrayList<>();

            LIST_OF_SETS.add(SET);

            TABLE.setSets(LIST_OF_SETS);

            GAME.setTable(TABLE);

            final int INDEX_OF_PLAYER1 = 0;

            final int INDEX_OF_EXPECTED_CARD = 0;

            final int INDEX_OF_SET = 0;

            final boolean CARD_ADDED = GAME.getPlayers().get(INDEX_OF_PLAYER1).getHand().add(TABLE.getSets().get(INDEX_OF_SET).getCards().get(INDEX_OF_EXPECTED_CARD));
            assertEquals("playCardFromPlayArea() != CARD_ADDED", CARD_ADDED, GAME.playCardFromPlayArea(INDEX_OF_PLAYER1, INDEX_OF_SET, INDEX_OF_EXPECTED_CARD));
        } catch (InvalidArgumentException e) {
            fail("Unexpected Exception");
        }

    }

    @Test
    public void mergeSet() {
        final int RANK = 5;
        final int ID1 = 1;
        final int ID2 = 2;
        final int ID3 = 3;
        final int ID4 = 4;
        try {
            final Card CARD1;

            CARD1 = new Basic(Suit.CLUBS, RANK, ID1);
            final Card CARD2 = new Basic(Suit.HEARTS, RANK, ID2);
            final Card CARD3 = new Basic(Suit.DIAMONDS, RANK, ID3);
            final Card CARD4 = new Basic(Suit.SPADES, RANK, ID4);


            final ArrayList<Card> INITIAL_LIST_OF_CARDS = new ArrayList<>();
            INITIAL_LIST_OF_CARDS.add(CARD1);
            INITIAL_LIST_OF_CARDS.add(CARD2);
            INITIAL_LIST_OF_CARDS.add(CARD3);
            INITIAL_LIST_OF_CARDS.add(CARD4);

            final Set SET1 = new Set(INITIAL_LIST_OF_CARDS);
            final Set SET2 = new Set(INITIAL_LIST_OF_CARDS);

            final int NUM_OF_PLAYERS = 2;

            Machiavelli GAME = new Machiavelli(NUM_OF_PLAYERS);

            final boolean SET_MERGED = SET1.getCards().addAll(SET2.getCards());

            assertTrue("mergeSet() != SET_MERGED", (SET_MERGED == GAME.mergeSet(SET1, SET2)));


        } catch (InvalidArgumentException e) {
            fail("Unexpected Exception");
        }
    }

    @Test
    public void splitSet() {
        final int RANK = 5;
        final int ID1 = 1;
        final int ID2 = 2;
        final int ID3 = 3;
        final int ID4 = 4;
        try {
            final Card CARD1 = new Basic(Suit.CLUBS, RANK, ID1);
            final Card CARD2 = new Basic(Suit.HEARTS, RANK, ID2);
            final Card CARD3 = new Basic(Suit.DIAMONDS, RANK, ID3);
            final Card CARD4 = new Basic(Suit.SPADES, RANK, ID4);


            final ArrayList<Card> INITIAL_LIST_OF_CARDS = new ArrayList<>();
            INITIAL_LIST_OF_CARDS.add(CARD1);
            INITIAL_LIST_OF_CARDS.add(CARD2);
            INITIAL_LIST_OF_CARDS.add(CARD3);
            INITIAL_LIST_OF_CARDS.add(CARD4);

            final Set INITIAL_SET = new Set(INITIAL_LIST_OF_CARDS);
            final int INDEX_FROM = 1;
            final int INDEX_TO = 2;
            final int NUM_OF_PLAYERS = 2;

            Machiavelli GAME = new Machiavelli(NUM_OF_PLAYERS);
            final Set EXPECTED_SET = new Set(new ArrayList<>(INITIAL_LIST_OF_CARDS.subList(INDEX_FROM, INDEX_TO)));
            assertEquals("splitSet() != EXPECTED_SET", EXPECTED_SET, GAME.splitSet(INITIAL_SET, INDEX_FROM, INDEX_TO));

        } catch (InvalidArgumentException e) {
            fail("Unexpected Exception");
        }
    }

    @Test
    public void splitSetRemove() {

        final int RANK = 5;
        final int ID1 = 1;
        final int ID2 = 2;
        final int ID3 = 3;
        final int ID4 = 4;
        try {
            final Card CARD1 = new Basic(Suit.CLUBS, RANK, ID1);
            final Card CARD2 = new Basic(Suit.HEARTS, RANK, ID2);
            final Card CARD3 = new Basic(Suit.DIAMONDS, RANK, ID3);
            final Card CARD4 = new Basic(Suit.SPADES, RANK, ID4);


            final ArrayList<Card> INITIAL_LIST_OF_CARDS = new ArrayList<>();
            INITIAL_LIST_OF_CARDS.add(CARD1);
            INITIAL_LIST_OF_CARDS.add(CARD2);
            INITIAL_LIST_OF_CARDS.add(CARD3);
            INITIAL_LIST_OF_CARDS.add(CARD4);

            final Set INITIAL_SET = new Set(INITIAL_LIST_OF_CARDS);
            final int INDEX_FROM = 1;
            final int INDEX_TO = 2;
            final int INDEX_TO_REMOVE_AT = 2;
            final int NUM_OF_PLAYERS = 2;

            Machiavelli GAME = new Machiavelli(NUM_OF_PLAYERS);
            final Set EXPECTED_SET = new Set(new ArrayList<>(INITIAL_LIST_OF_CARDS.subList(INDEX_FROM, INDEX_TO)));
            assertEquals("splitSetRemove() != EXPECTED_SET", EXPECTED_SET, GAME.splitSetRemove(INITIAL_SET, INDEX_FROM, INDEX_TO, INDEX_TO_REMOVE_AT));

        } catch (InvalidArgumentException e) {
            fail("Unexpected Exception");
        }
    }

    @Test
    public void prependCard() {
        final ArrayList<Card> INITIAL_LIST_OF_CARDS = new ArrayList<>();

        final Set INITIAL_SET = new Set(INITIAL_LIST_OF_CARDS);

        final Card CARD_TO_ADD;
        try {
            CARD_TO_ADD = new Basic(Suit.DIAMONDS, 5, 1);

            final Machiavelli INITIAL_GAME = new Machiavelli(2);

            INITIAL_GAME.prependCard(INITIAL_SET, CARD_TO_ADD);

            assertEquals("getCards().get() != CARD_TO_ADD", CARD_TO_ADD, INITIAL_SET.getCards().get(0));
        } catch (InvalidArgumentException e) {
            fail("Unexpected Exception");
        }
    }

    @Test
    public void appendCard() {

        final ArrayList<Card> INITIAL_LIST_OF_CARDS = new ArrayList<>();

        final Set INITIAL_SET = new Set(INITIAL_LIST_OF_CARDS);

        final Card CARD_TO_ADD;
        try {
            CARD_TO_ADD = new Basic(Suit.DIAMONDS, 5, 1);


            final Machiavelli INITIAL_GAME = new Machiavelli(2);

            INITIAL_GAME.appendCard(INITIAL_SET, CARD_TO_ADD);

            assertEquals("getCards().get() != CARD_TO_ADD ", CARD_TO_ADD, INITIAL_SET.getCards().get(INITIAL_SET.getCards().size() - 1));
        } catch (InvalidArgumentException e) {
            fail("Unexpected Exception");
        }
    }
}