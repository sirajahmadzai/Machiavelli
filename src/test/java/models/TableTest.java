package models;

import models.cards.Card;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TableTest {

    @Test
    public void testConstructor() {
        final ArrayList<Card> INITIAL_DECK = new ArrayList<>();
        final ArrayList<CardSet> INITIAL_Card_SETS = new ArrayList<>();
        final ArrayList<Card> INITIAL_CARDS_IN_PLAY = new ArrayList<>();

        final Table table = new Table();
        table.setDeck(INITIAL_DECK);
        table.setCardSets(INITIAL_Card_SETS);
        table.setCardsInPlay(INITIAL_CARDS_IN_PLAY);

        assertEquals(INITIAL_DECK, table.getDeck());
        assertEquals(INITIAL_Card_SETS, table.getCardSets());
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
}