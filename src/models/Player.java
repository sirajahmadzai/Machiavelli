package models;

import models.cards.Card;

import java.util.ArrayList;

public class Player {

    private int playerID;
    private String name;



    private ArrayList<Card> hand;
    private int pointValue;

    public Player(int playerID, String name) {
        this.playerID = playerID;
        this.name = name;
        hand = new ArrayList<>();
        pointValue = 0;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }
}
