package models;

import models.cards.Card;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TableTest {

    @Test
    public void testConstructor() {
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

    @Test
    public void setDeck() {
    }

    @Test
    public void setSets() {
    }

    @Test
    public void setCardsInPlay() {
    }

    @Test
    public void shuffleDeck() {
        final Table table = new Table();
        table.shuffleDeck();
        Table table2 = new Table();
        assertTrue(table.decksDifferent(table, table2));
    }

    @Test
    public void decksDifferent() {
    }

    @Test
    public void getCardByIndex() {
    }
}