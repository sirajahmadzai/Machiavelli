import com.sun.javaws.exceptions.InvalidArgumentException;
import junit.framework.TestCase;
import models.Set;
import models.Table;
import models.cards.Basic;
import models.cards.Card;
import models.cards.Suit;


import java.util.ArrayList;

public class TableTest extends TestCase {

    /**
     * tests construction of Table object
     */
    public void testTable() {
        final ArrayList<Card> INITIAL_DECK = new ArrayList<>();
        final ArrayList<Set> INITIAL_SETS = new ArrayList<>();
        final ArrayList<Card> INITIAL_CARDS_IN_PLAY = new ArrayList<>();

        final Table table = new Table();
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
        final Table table = new Table();
        table.shuffleDeck();
        Table table2 = new Table();
        assertTrue(table.decksDifferent(table, table2));
    }


}
