package server;

import com.sun.javaws.exceptions.InvalidArgumentException;
import org.junit.Test;
import server.models.CardSet;
import server.models.Machiavelli;
import server.models.Player;
import server.models.Table;
import server.models.cards.Basic;
import server.models.cards.Card;
import server.models.cards.Suit;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class MachiavelliTest {

    @Test
    public void testConstructor() {
        final int NUM_OF_PLAYERS = 2;

        final Machiavelli GAME = Machiavelli.getInstance();
        GAME.initialize(NUM_OF_PLAYERS);

//        TODO: prove the game has players, table and deals hannds
    }


    @Test
    public void setTable() {
        final Table TABLE = new Table();

        final int NUM_OF_PLAYERS = 2;

        final Machiavelli GAME = Machiavelli.getInstance();
        GAME.initialize(NUM_OF_PLAYERS);

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
        final Machiavelli GAME = Machiavelli.getInstance();
        GAME.initialize(NUM_OF_PLAYERS);
        GAME.setPlayers(PLAYERS);

        assertEquals("getPlayers != PLAYERS", PLAYERS, GAME.getPlayers());

    }

    @Test
    public void drawCardFromDeck() {

        final int NUM_OF_PLAYERS = 2;
        final Machiavelli GAME = Machiavelli.getInstance();
        GAME.initialize(NUM_OF_PLAYERS);

        final int PLAYER1_ID = 0;
//        final int PLAYER2_ID = 1;

        final String PLAYER1_NAME = "siraj";
//        final String PLAYER2_NAME = "steph";

        final Player PLAYER1 = new Player(PLAYER1_ID, PLAYER1_NAME);
//        final Player PLAYER2 = new Player(PLAYER2_ID, PLAYER2_NAME);

        final ArrayList<Player> PLAYERS = new ArrayList<>();

        PLAYERS.add(PLAYER1);
//        PLAYERS.add(PLAYER2);

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

            assertEquals("", CARD2, TABLE.getDeck().get(TABLE.getDeck().size() - 1));

            try {
                GAME.drawCardFromDeck(PLAYER1);
            } catch (Machiavelli.EmptyDeckException e) {
                fail("Deck is empty!");
            }

            assertEquals("drawCardFromDeck() != CARD1", -1, TABLE.getDeck().indexOf(CARD2));
//            assertEquals("", CARD2, GAME.getPlayers().get(PLAYER1_ID).getHand().get(GAME.getPlayers().get(PLAYER1_ID).getHand().size() - 1));
        } catch (InvalidArgumentException e) {
            fail("Unexpected Exception");
        }
    }

    @Test
    public void removeCardFromHand() {
//        final int INITIAL_NUM_OF_PLAYERS = 2;
//
//        final int INITIAL_PLAYER_ID = 1;
//
//        final int INITIAL_CARD_ID = 1;
//
//        final Suit SUIT = Suit.CLUBS;
//
//        final int RANK = 5;
//
//        final String PLAYER_NAME = "PLAYER1";
//
//        final Player PLAYER1 = new Player(INITIAL_PLAYER_ID, PLAYER_NAME);
//
//        final ArrayList<Player> players = new ArrayList<>();
//
//        players.add(PLAYER1);
//
//        try {
//
//            final Card EXPECTED_CARD = new Basic(SUIT, RANK, INITIAL_CARD_ID);
//
//            final Machiavelli GAME = Machiavelli.getInstance();
//            GAME.initialize(INITIAL_NUM_OF_PLAYERS);
//
//            GAME.setPlayers(players);
//
//            final int INDEX_OF_PLAYER1 = 0;
//
//            final int INDEX_OF_EXPECTED_CARD = 0;
//
//            GAME.getPlayers().get(INDEX_OF_PLAYER1).getHand().add(EXPECTED_CARD);
//            GAME.getPlayers().get(INDEX_OF_PLAYER1).getHand().add(new Joker(Suit.JOKER, 2));
//            GAME.getPlayers().get(INDEX_OF_PLAYER1).getHand().add(new Ace(Suit.CLUBS, 3));
//
//            assertEquals("", EXPECTED_CARD, GAME.getPlayers().get(INDEX_OF_PLAYER1).getHand().get(INDEX_OF_EXPECTED_CARD));
//
//            final Card CARD_REMOVED = GAME.removeCardFromHand(INDEX_OF_PLAYER1, INDEX_OF_EXPECTED_CARD);
//
//            assertEquals(EXPECTED_CARD, CARD_REMOVED);
//
//            assertEquals("", -1, GAME.getPlayers().get(INDEX_OF_PLAYER1).getHand().indexOf(EXPECTED_CARD));
//        } catch (InvalidArgumentException e) {
//            fail("Unexpected Exception");
//        }


    }

//    @Test
//    public void playCardFromPlayArea() {
//
//        final int NUM_OF_PLAYERS = 2;
//
//        final int PLAYER_ID = 1;
//
//        final int CARD_ID = 1;
//
//        final Suit SUIT = Suit.CLUBS;
//
//        final int RANK = 5;
//
//        final String PLAYER_NAME = "PLAYER1";
//
//        final Player PLAYER1 = new Player(PLAYER_ID, PLAYER_NAME);
//
//        final ArrayList<Player> players = new ArrayList<>();
//
//        players.add(PLAYER1);
//
//        try {
//
//            final Card EXPECTED_CARD = new Basic(SUIT, RANK, CARD_ID);
//
//            final ArrayList<Card> LIST_OF_CARDS = new ArrayList<>();
//
//            LIST_OF_CARDS.add(EXPECTED_CARD);
//
//            final Machiavelli GAME = new Machiavelli(NUM_OF_PLAYERS);
//
//            GAME.setPlayers(players);
//
//            final Table TABLE = new Table();
//
//            final CardSet CARD_SET = new CardSet(LIST_OF_CARDS);
//
//            final ArrayList<CardSet> LIST_OF_CARD_SETS = new ArrayList<>();
//
//            LIST_OF_CARD_SETS.add(CARD_SET);
//
//            TABLE.setCardSets(LIST_OF_CARD_SETS);
//
//            GAME.setTable(TABLE);
//
//            final int INDEX_OF_PLAYER1 = 0;
//
//            final int INDEX_OF_EXPECTED_CARD = 0;
//
//            final int INDEX_OF_SET = 0;
//
//            assertEquals("", EXPECTED_CARD, GAME.getPlayers().get(INDEX_OF_PLAYER1).getHand().get(INDEX_OF_EXPECTED_CARD));
//            assertEquals("", CARD_SET);
//
//            GAME.playCardFromPlayArea(INDEX_OF_PLAYER1, INDEX_OF_SET, INDEX_OF_EXPECTED_CARD);
//
//            GAME.getPlayers().get(INDEX_OF_PLAYER1).getHand().add(TABLE.getCardSets().get(INDEX_OF_SET).getCards().get(INDEX_OF_EXPECTED_CARD));
//            assertEquals("playCardFromPlayArea() != CARD_ADDED", EXPECTED_CARD, );
//        } catch (InvalidArgumentException e) {
//            fail("Unexpected Exception");
//        }
//
//    }

    @Test
    public void mergeSetAppend() {

        final int INITIAL_SIZE_OF_CARD_SETS = 2;
        final int EXPECTED_SIZE_OF_CARD_SETS = 1;
        final int SIZE_OF_EXPECTED_CARD_SET = 8;
        final int INDEX_OF_CARD1 = 0;
        final int INDEX_OF_CARD4 = 3;
        final int INITIAL_INDEX_OF_CARD5 = 0;
        final int INITIAL_INDEX_OF_CARD8 = 3;
        final int EXPECTED_INDEX_OF_CARD5 = 4;
        final int EXPECTED_INDEX_OF_CARD8 = 7;
        final int INDEX_OF_CARD_SET_TO_KEEP = 0;

        final int RANK = 5;
        final int ID1 = 1;
        final int ID2 = 2;
        final int ID3 = 3;
        final int ID4 = 4;

        final int RANK2 = 7;

        final int ID5 = 5;
        final int ID6 = 6;
        final int ID7 = 7;
        final int ID8 = 8;
        try {


            final Card CARD1 = new Basic(Suit.CLUBS, RANK, ID1);
            final Card CARD2 = new Basic(Suit.HEARTS, RANK, ID2);
            final Card CARD3 = new Basic(Suit.DIAMONDS, RANK, ID3);
            final Card CARD4 = new Basic(Suit.SPADES, RANK, ID4);


            final Card CARD5 = new Basic(Suit.CLUBS, RANK2, ID5);
            final Card CARD6 = new Basic(Suit.HEARTS, RANK2, ID6);
            final Card CARD7 = new Basic(Suit.DIAMONDS, RANK2, ID7);
            final Card CARD8 = new Basic(Suit.SPADES, RANK2, ID8);

            final ArrayList<Card> INITIAL_LIST_OF_CARDS = new ArrayList<>();
            INITIAL_LIST_OF_CARDS.add(CARD1);
            INITIAL_LIST_OF_CARDS.add(CARD2);
            INITIAL_LIST_OF_CARDS.add(CARD3);
            INITIAL_LIST_OF_CARDS.add(CARD4);

            final ArrayList<Card> LIST_OF_CARDS2 = new ArrayList<>();
            LIST_OF_CARDS2.add(CARD5);
            LIST_OF_CARDS2.add(CARD6);
            LIST_OF_CARDS2.add(CARD7);
            LIST_OF_CARDS2.add(CARD8);

            final CardSet CARD_SET_TO_KEEP = new CardSet(INITIAL_LIST_OF_CARDS);
            final CardSet CARD_SET_TO_APPEND = new CardSet(LIST_OF_CARDS2);

            final int NUM_OF_PLAYERS = 2;

            final Machiavelli GAME = Machiavelli.getInstance();
            GAME.initialize(NUM_OF_PLAYERS);

            final Table TABLE = new Table();
            TABLE.getCardSets().add(CARD_SET_TO_KEEP);
            TABLE.getCardSets().add(CARD_SET_TO_APPEND);

            assertEquals("CARD1 != first card in CARD_SET_TO_KEEP", CARD1, CARD_SET_TO_KEEP.getCards().get(INDEX_OF_CARD1));
            assertEquals("CARD4 != last card in CARD_SET_TO_KEEP", CARD4, CARD_SET_TO_KEEP.getCards().get(INDEX_OF_CARD4));

            assertEquals("CARD5 != first card in CARD_SET_TO_APPEND", CARD5, CARD_SET_TO_APPEND.getCards().get(INITIAL_INDEX_OF_CARD5));
            assertEquals("CARD8 != last card in CARD_SET_TO_APPEND", CARD8, CARD_SET_TO_APPEND.getCards().get(INITIAL_INDEX_OF_CARD8));

            GAME.setTable(TABLE);


            assertEquals("size of card sets != INITIAL_SIZE_OF_CARD_SETS", INITIAL_SIZE_OF_CARD_SETS, GAME.getTable().getCardSets().size());

            GAME.mergeSetAppend(CARD_SET_TO_KEEP, CARD_SET_TO_APPEND);

            assertEquals("CARD_SET_TO_KEEP != first set in card sets", CARD_SET_TO_KEEP, GAME.getTable().getCardSets().get(INDEX_OF_CARD_SET_TO_KEEP));
            assertEquals("EXPECTED_SIZE_OF_CARD_SETS != size of card sets", EXPECTED_SIZE_OF_CARD_SETS, GAME.getTable().getCardSets().size());
            assertEquals("EXPECTED_SIZE_OF_CARD_SET_TO_KEEP != size of CARD_SET_TO_KEEP", SIZE_OF_EXPECTED_CARD_SET, GAME.getTable().getCardSets().get(INDEX_OF_CARD_SET_TO_KEEP).getCards().size());

            assertEquals("CARD1 != first card in CARD_SET_TO_KEEP", CARD1, TABLE.getCardSets().get(INDEX_OF_CARD_SET_TO_KEEP).getCards().get(INDEX_OF_CARD1));
            assertEquals("CARD4 != 4th card in CARD_SET_TO_KEEP", CARD4, TABLE.getCardSets().get(INDEX_OF_CARD_SET_TO_KEEP).getCards().get(INDEX_OF_CARD4));
            assertEquals("CARD5 != 5th card in CARD_SET_TO_KEEP", CARD5, TABLE.getCardSets().get(INDEX_OF_CARD_SET_TO_KEEP).getCards().get(EXPECTED_INDEX_OF_CARD5));
            assertEquals("CARD4 != last card in CARD_SET_TO_KEEP", CARD8, TABLE.getCardSets().get(INDEX_OF_CARD_SET_TO_KEEP).getCards().get(EXPECTED_INDEX_OF_CARD8));


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

            final CardSet INITIAL_Card_SET = new CardSet(INITIAL_LIST_OF_CARDS);
            final ArrayList<CardSet> LIST_OF_CARD_SETS = new ArrayList<>();

            LIST_OF_CARD_SETS.add(INITIAL_Card_SET);

            final Table TABLE = new Table();

            TABLE.setCardSets(LIST_OF_CARD_SETS);


            final int INDEX_TO_SPLIT_SET_AT = 1;
            final int NUM_OF_PLAYERS = 2;
            final int EXPECTED_NUM_OF_CARD_SETS = 2;
            final int EXPECTED_SIZE_OF_SET1 = 1;
            final int EXPECTED_SIZE_OF_SET2 = 3;
            final int INDEX_OF_SET1 = 0;
            final int INDEX_OF_SET2 = 1;
            final int INDEX_OF_CARD1 = 0;
            final int INDEX_OF_CARD2 = 0;
            final int INDEX_OF_CARD3 = 1;
            final int INDEX_OF_CARD4 = 2;


            final Machiavelli GAME = Machiavelli.getInstance();
            GAME.initialize(NUM_OF_PLAYERS);

            GAME.setTable(TABLE);

            GAME.splitSet(INITIAL_Card_SET, INDEX_TO_SPLIT_SET_AT);

            assertEquals("size of sets != EXPECTED_NUM_OF_CARD_SETS", EXPECTED_NUM_OF_CARD_SETS, GAME.getTable().getCardSets().size());
            assertEquals("size of set1 != EXPECTED_SIZE_OF_SET1", EXPECTED_SIZE_OF_SET1, GAME.getTable().getCardSets().get(INDEX_OF_SET1).getCards().size());
            assertEquals("size of set2 != EXPECTED_SIZE_OF_SET2", EXPECTED_SIZE_OF_SET2, GAME.getTable().getCardSets().get(INDEX_OF_SET2).getCards().size());
            assertEquals("card at INDEX_OF_CARD1 != CARD1", CARD1, GAME.getTable().getCardSets().get(INDEX_OF_SET1).getCards().get(INDEX_OF_CARD1));
            assertEquals("card at INDEX_OF_CARD2 != CARD2", CARD2, GAME.getTable().getCardSets().get(INDEX_OF_SET2).getCards().get(INDEX_OF_CARD2));
            assertEquals("card at INDEX_OF_CARD3 != CARD3", CARD3, GAME.getTable().getCardSets().get(INDEX_OF_SET2).getCards().get(INDEX_OF_CARD3));
            assertEquals("card at INDEX_OF_CARD4 != CARD4", CARD4, GAME.getTable().getCardSets().get(INDEX_OF_SET2).getCards().get(INDEX_OF_CARD4));
        } catch (InvalidArgumentException e) {
            fail("Unexpected Exception");
        }
    }

    @Test
    public void removeCard() {

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


            final ArrayList<Card> LIST_OF_CARDS = new ArrayList<>();
            LIST_OF_CARDS.add(CARD1);
            LIST_OF_CARDS.add(CARD2);
            LIST_OF_CARDS.add(CARD3);
            LIST_OF_CARDS.add(CARD4);

            final CardSet CARD_SET = new CardSet(LIST_OF_CARDS);
            final ArrayList<CardSet> LIST_OF_CARD_SETS = new ArrayList<>();

            LIST_OF_CARD_SETS.add(CARD_SET);

            final Table TABLE = new Table();

            TABLE.setCardSets(LIST_OF_CARD_SETS);


            final int INDEX_OF_CARD_TO_REMOVE = 1;
            final int NUM_OF_PLAYERS = 2;
            final int EXPECTED_NUM_OF_CARD_SETS = 2;
            final int EXPECTED_SIZE_OF_SET1 = 1;
            final int EXPECTED_SIZE_OF_SET2 = 3;
            final int INDEX_OF_SET1 = 0;
            final int INDEX_OF_SET2 = 1;
            final int INDEX_OF_CARD1 = 0;
            final int INDEX_OF_CARD2 = 0;
            final int INDEX_OF_CARD3 = 1;
            final int INDEX_OF_CARD4 = 2;


            final Machiavelli GAME = Machiavelli.getInstance();
            GAME.initialize(NUM_OF_PLAYERS);

            GAME.setTable(TABLE);

            final Card CARD_TO_REMOVE = CARD_SET.getCards().get(INDEX_OF_CARD_TO_REMOVE);

            GAME.removeCard(CARD_SET, INDEX_OF_CARD_TO_REMOVE);


            assertEquals("size of cards sets on TABLE != EXPECTED_NUM_OF_CARD_SETS", EXPECTED_NUM_OF_CARD_SETS, GAME.getTable().getCardSets().size());
            assertEquals("size of SET1 != EXPECTED_SIZE_OF_SET1", EXPECTED_SIZE_OF_SET1, GAME.getTable().getCardSets().get(INDEX_OF_SET1).getCards().size());
            assertEquals("size of SET2 != EXPECTED_SIZE_OF_SET2", EXPECTED_SIZE_OF_SET2, GAME.getTable().getCardSets().get(INDEX_OF_SET2).getCards().size());
            assertEquals("card at INDEX_OF_CARD1 != CARD1", CARD1, GAME.getTable().getCardSets().get(INDEX_OF_SET1).getCards().get(INDEX_OF_CARD1));
            assertEquals("card at INDEX_OF_CARD2 != CARD2", CARD2, GAME.getTable().getCardSets().get(INDEX_OF_SET2).getCards().get(INDEX_OF_CARD2));
            assertEquals("card at INDEX_OF_CARD3 != CARD3", CARD3, GAME.getTable().getCardSets().get(INDEX_OF_SET2).getCards().get(INDEX_OF_CARD3));
            assertEquals("card at INDEX_OF_CARD4 != CARD4", CARD4, GAME.getTable().getCardSets().get(INDEX_OF_SET2).getCards().get(INDEX_OF_CARD4));
            assertEquals("", CARD_TO_REMOVE, TABLE.getCardsInPlay().get(TABLE.getCardsInPlay().size() - 1));
        } catch (InvalidArgumentException e) {
            fail("Unexpected Exception");
        }

    }

    @Test
    public void prependCard() {
        final ArrayList<Card> LIST_OF_CARDS = new ArrayList<>();

        final CardSet CARD_SET = new CardSet(LIST_OF_CARDS);

        try {
            final Suit SUIT_OF_CARD_TO_PREPEND = Suit.DIAMONDS;
            final int RANK_OF_CARD_TO_PREPEND = 5;
            final int ID_OF_CARD_TO_PREPEND = 1;


            final Card CARD_TO_PREPEND = new Basic(SUIT_OF_CARD_TO_PREPEND, RANK_OF_CARD_TO_PREPEND, ID_OF_CARD_TO_PREPEND);

            final Suit SUIT1 = Suit.SPADES;
            final int RANK1 = 5;
            final int ID1 = 2;

            final Suit SUIT2 = Suit.DIAMONDS;
            final int RANK2 = 8;
            final int ID2 = 3;

            final Suit SUIT3 = Suit.HEARTS;
            final int RANK3 = 9;
            final int ID3 = 5;

            final Card CARD1 = new Basic(SUIT1, RANK1, ID1);
            final Card CARD2 = new Basic(SUIT2, RANK2, ID2);
            final Card CARD3 = new Basic(SUIT3, RANK3, ID3);


            CARD_SET.getCards().add(CARD1);
            CARD_SET.getCards().add(CARD2);
            CARD_SET.getCards().add(CARD3);

            final Machiavelli GAME = Machiavelli.getInstance();
            GAME.initialize(2);

            assertEquals("CARD1 != first card in CARD_SET", 2, CARD_SET.getCards().get(0).getId());

            GAME.prependCard(CARD_SET, CARD_TO_PREPEND);

            assertEquals("CARD_TO_PREPEND != first card in CARD_SET", CARD_TO_PREPEND, CARD_SET.getCards().get(0));
            assertEquals("CARD1 != card after CARD_TO_PREPEND", 2, CARD_SET.getCards().get(1).getId());


        } catch (InvalidArgumentException e) {
            fail("Unexpected Exception");
        }
    }

    @Test
    public void appendCard() {

        final ArrayList<Card> LIST_OF_CARDS = new ArrayList<>();

        final CardSet CARD_SET = new CardSet(LIST_OF_CARDS);


        try {
            final Suit SUIT_OF_CARD_TO_APPEND = Suit.DIAMONDS;
            final int RANK_OF_CARD_TO_APPEND = 5;
            final int ID_OF_CARD_TO_APPEND = 1;

            final Card CARD_TO_APPEND = new Basic(SUIT_OF_CARD_TO_APPEND, RANK_OF_CARD_TO_APPEND, ID_OF_CARD_TO_APPEND);

            final Suit SUIT1 = Suit.SPADES;
            final int RANK1 = 5;
            final int ID1 = 2;

            final Suit SUIT2 = Suit.DIAMONDS;
            final int RANK2 = 8;
            final int ID2 = 3;

            final Suit SUIT3 = Suit.HEARTS;
            final int RANK3 = 9;
            final int ID3 = 5;

            final Card CARD1 = new Basic(SUIT1, RANK1, ID1);
            final Card CARD2 = new Basic(SUIT2, RANK2, ID2);
            final Card CARD3 = new Basic(SUIT3, RANK3, ID3);

            CARD_SET.getCards().add(CARD1);
            CARD_SET.getCards().add(CARD2);
            CARD_SET.getCards().add(CARD3);

            assertEquals("CARD4 != ID of last card in CARD_SET", 4, CARD_SET.getCards().get(CARD_SET.getCards().size() - 1).getId());

            final int NUM_OF_PLAYERS = 2;

            final Machiavelli INITIAL_GAME = Machiavelli.getInstance();
            INITIAL_GAME.initialize(NUM_OF_PLAYERS);

            INITIAL_GAME.appendCard(CARD_SET, CARD_TO_APPEND);

            assertEquals("last card in CARD_SET != CARD_TO_APPEND ", CARD_TO_APPEND, CARD_SET.getCards().get(CARD_SET.getCards().size() - 1));
            assertEquals("CARD != second last card in CARD_SET", 4, CARD_SET.getCards().get(CARD_SET.getCards().size() - 2).getId());
        } catch (InvalidArgumentException e) {
            fail("Unexpected Exception");
        }
    }
}