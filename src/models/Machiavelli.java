package models;

import com.sun.javaws.exceptions.InvalidArgumentException;
import models.cards.Basic;
import models.cards.Card;
import models.cards.Joker;
import models.cards.Suit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Machiavelli {


    private Table table;
    private ArrayList<Player> players;


    public Machiavelli(int numOfPlayers) {
        players = new ArrayList<>();
        table = new Table();

        for (int playerCounter = 0; playerCounter < numOfPlayers; playerCounter++) {
            players.add(new Player(numOfPlayers, "Player" + numOfPlayers + 1));
        }

        dealHands();

    }


    public void mergeSet() {

    }

    public void splitSet(int pos) {

    }

    public Card splitSetRemove(int pos) {
        return null;
    }

    public void prependCard(Card card) {
    }

    public void appendCard(Card card) {

    }

    private void verifyTable() {

    }

    private void dealHands() {
        int currPlayerID = (new Random()).nextInt(players.size());

        for (int cardCounter = 0; cardCounter < 15; cardCounter++) {

            int playerCounter = 0;

            while (playerCounter < players.size()) {

                if (currPlayerID >= players.size()) {

                    currPlayerID = 0;
                }
                players.get(currPlayerID).getHand().add(table.getDeck().remove(table.getDeck().size() - 1));

                currPlayerID++;
                playerCounter++;
            }
        }
    }
}