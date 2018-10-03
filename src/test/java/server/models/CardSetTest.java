package server.models;

import com.sun.javaws.exceptions.InvalidArgumentException;
import server.models.cards.Card;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class CardSetTest {
    @org.junit.jupiter.api.BeforeEach
    void setUp() {

    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void addCard() {

    }

    @org.junit.jupiter.api.Test
    void removeCard() {
    }

    @org.junit.jupiter.api.Test
    void getCards() {
    }

    @org.junit.jupiter.api.Test
    void isAValidMeld() {
    }

    private void checkIsAStraight(String setText, boolean expected) {
        CardSet set = new CardSet(setText);
        assertEquals(set.isAStraight(), expected);
    }

    @org.junit.jupiter.api.Test
    void isAStraight() {
//      Set
        checkIsAStraight("2s", true);
        checkIsAStraight("2s,3s", true);
        checkIsAStraight("2s,3s,4s", true);
        checkIsAStraight("2s,3s,4s,5s", true);
        checkIsAStraight("as,2s,3s,4s,5s", true);
        checkIsAStraight("as,2s,3s,4s,joker", true);
        checkIsAStraight("as,2s,3s,joker,5s", true);
        checkIsAStraight("10s,js,qs,ks,as", true);

//      False
        checkIsAStraight("js,qs,ks,as,2s", false);
        checkIsAStraight("2s,3s,5s", false);
        checkIsAStraight("2s,3s,4d", false);
        checkIsAStraight("2s,3s,3s", false);
        checkIsAStraight("2s,3s,3d", false);
        checkIsAStraight("2s,3s,joker,6s", false);
    }

    private void checkIsASet(String setText, boolean expected) {
        CardSet set = new CardSet(setText);
        assertEquals(set.isASet(), expected);
    }

    @org.junit.jupiter.api.Test
    void isASet() {
        checkIsASet("2s", true);
        checkIsASet("2s,2d,2h", true);
        checkIsASet("2s,2d,2h,2c", true);
        checkIsASet("2s,2d,2h,joker", true);
        checkIsASet("joker", true);

        checkIsASet("2s,2d,2h,2h", false);
        checkIsASet("2s,2d,2h,3h", false);
    }

    @org.junit.jupiter.api.Test
    void sort() {
    }

    @org.junit.jupiter.api.Test
    void canAcceptCard() {
    }

    @org.junit.jupiter.api.Test
    void totalCount() {
    }

    @org.junit.jupiter.api.Test
    void cardCount() {
    }

    @org.junit.jupiter.api.Test
    void jokerCount() {
    }

    private void checkEquals(String set1, String set2, boolean expected) {
        CardSet s1 = new CardSet(set1);
        CardSet s2 = new CardSet(set2);
        assertEquals(expected, s1.equals(s2));
    }

    @org.junit.jupiter.api.Test
    void equals() {
        checkEquals("2s,3s,4s", "2s,3s,4s", true);
        checkEquals("2s,3s,4s", "2s,4s,3s", true);
        checkEquals("2s,3s,4s", "2s,3s,4c", false);
        checkEquals("2s,3s,4s", "2s,3s", false);
    }

    private void checkSuperSet(String set1, String set2, boolean expected) {
        CardSet s1 = new CardSet(set1);
        CardSet s2 = new CardSet(set2);
        assertEquals(expected, s1.superSetOf(s2));
    }

    @org.junit.jupiter.api.Test
    void superSetOf() {
        checkSuperSet("2s,3s,4s,5s", "2s,3s,4s,5s", true);
        checkSuperSet("2s,3s,4s,5s", "2s,3s,4s", true);
        checkSuperSet("2s,3s,4s,5s", "2s", true);

        checkSuperSet("2s,3s,4s,5s", "2s,3s,4s,5s,6s", false);
        checkSuperSet("2s,3s,4s,5s", "2s,3s,4s,5d,", false);
        checkSuperSet("2s,3s,4s,5s", "2s,3s,4d", false);
    }

    private void checkDiff(String set1, String set2, String expected) {
        CardSet s1 = new CardSet(set1);
        CardSet s2 = new CardSet(set2);
        assertEquals(expected, s1.diff(s2).toString());

    }

    @org.junit.jupiter.api.Test
    void diff() {
        checkDiff("2s,3s,4s,5s", "2s,3s,4s,5s", "");
        checkDiff("2s,3s,4s,5s", "2s,3s,4s,5s,6s", "");
        checkDiff("2s,3s,4s,5s", "2s,3s,4s,5c", "5s");
        checkDiff("2s,3s,4s,5s", "2s,3s,4s", "5s");
        checkDiff("2s,3s,4s,5s", "2s,3s,5s", "4s");
        checkDiff("2s,3s,4s,5s", "2s,3s", "4s,5s");

        checkDiff("2s,2s,3s", "2s,3s", "2s");
    }


}