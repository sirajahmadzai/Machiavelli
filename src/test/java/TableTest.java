import com.sun.javaws.exceptions.InvalidArgumentException;
import junit.framework.TestCase;
import models.Set;
import models.Table;
import models.cards.Card;
import org.hamcrest.CoreMatchers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TableTest extends TestCase {

    /**
     * tests construction of Table object
     */
    public void testTable() {
        ArrayList<Card> INITIAL_DECK = new ArrayList<>();
        ArrayList<Set> INITIAL_SETS = new ArrayList<>();
        ArrayList<Card> INITIAL_CARDS_IN_PLAY = new ArrayList<>();

        Table table = new Table();
        table.setDeck(INITIAL_DECK);
        table.setSets(INITIAL_SETS);
        table.setCardsInPlay(INITIAL_CARDS_IN_PLAY);

        assertEquals(INITIAL_DECK, table.getDeck());
        assertEquals(INITIAL_SETS, table.getSets());
        assertEquals(INITIAL_CARDS_IN_PLAY, table.getCardsInPlay());

    }

    public void testInitMachiavelliDeck() {

    }

    public void testGenerateStandardDeck() {

    }

    public void testShuffleDeck() {
       Table table = new Table();
        table.shuffleDeck();
        Table table2 = new Table();
        assertTrue(table.decksDifferent(table, table2));
    }

    public void testGetCardNameByRank() {

        int INITIAL_RANK = 5;

        Table table = new Table();
    String INITIAL_CARD_NAME = "";
        try {
             INITIAL_CARD_NAME = table.getCardNameByRank(INITIAL_RANK);
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
        try {
            assertEquals(INITIAL_CARD_NAME, table.getCardNameByRank(INITIAL_RANK).toString());
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }

    }

    public void testGetCardValueByRank() {
        int INITIAL_RANK_VALUE = 0;

        Table table = new Table();

        try {
            INITIAL_RANK_VALUE = table.getCardValueByRank(INITIAL_RANK_VALUE);
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
        try {
            assertEquals(INITIAL_RANK_VALUE, table.getCardValueByRank(INITIAL_RANK_VALUE));
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }

    }
}
