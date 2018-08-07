package models;

import com.sun.javaws.exceptions.InvalidArgumentException;
import models.cards.Basic;
import models.cards.Card;
import models.cards.Suit;
import org.junit.Test;
import org.omg.CORBA.PUBLIC_MEMBER;

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
    }

    @Test
    public void setPlayers() {
    }

    @Test
    public void drawCardFromDeck() {

        final Machiavelli INITIAL_GAME = new Machiavelli(2);

        final Player PLAYER1 = new Player(0, "siraj");
        final Player PLAYER2 = new Player(1, "steph");

        final ArrayList<Player> INITIAL_PLAYERS = new ArrayList<>();

        INITIAL_PLAYERS.add(PLAYER1);
        INITIAL_PLAYERS.add(PLAYER2);

        final Table INITIAL_TABLE = new Table();
        final Basic CARD1;
        try {
            CARD1 = new Basic(Suit.CLUBS, 10, -1);
            final Basic CARD2 = new Basic(Suit.HEARTS, 10, -1);

            INITIAL_TABLE.getDeck().add(CARD1);
            INITIAL_TABLE.getDeck().add(CARD2);

            assertEquals("drawCardFromDeck() != CARD1", CARD1, INITIAL_GAME.drawCardFromDeck());
            assertEquals("drawCardFromDeck() != CARD2", CARD2, INITIAL_GAME.drawCardFromDeck());
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void playCard() {
        final int INITIAL_NUM_OF_PLAYERS = 2;
        final int INITIAL_PLAYER_ID = 1;

        final int INITIAL_CARD_ID = 1;

        final Machiavelli INITIAL_GAME = new Machiavelli(INITIAL_NUM_OF_PLAYERS);


        Card card = INITIAL_GAME.playCard(INITIAL_PLAYER_ID, INITIAL_CARD_ID);

        assertEquals(card, INITIAL_GAME.playCard(INITIAL_PLAYER_ID, INITIAL_CARD_ID));
    }

    @Test
    public void playCardFromPlayArea() {
    }

    @Test
    public void mergeSet() {
    }

    @Test
    public void splitSet() {
    }

    @Test
    public void splitSetRemove() {
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

            assertEquals(CARD_TO_ADD, INITIAL_SET.getCards().get(0));
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
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

            assertEquals(CARD_TO_ADD, INITIAL_SET.getCards().get(INITIAL_SET.getCards().size() - 1));
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getRandomPlayer() {
    }
}