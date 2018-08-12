package models;

import models.cards.Card;

import java.util.ArrayList;

class Player {

    /********************************
     ******** PRIVATES **************
     ********************************/
    private int playerID;
    private String name;
    private ArrayList<Card> hand;
    private int pointValue;

    /**
     * CONSTRUCTOR
     *
     * @param playerID
     * @param name
     */
    Player(int playerID, String name) {
        this.playerID = playerID;
        this.name = name;
        hand = new ArrayList<>();
        pointValue = 0;
    }

    /**
     * gets player id
     *
     * @return
     */
    int getPlayerID() {
        return playerID;
    }

    /**
     * gets name
     *
     * @return
     */
    String getName() {
        return name;
    }

    /**
     * gets hand
     *
     * @return
     */
    ArrayList<Card> getHand() {
        return hand;
    }

    /**
     * gets pointValue
     *
     * @return
     */
    int getPointValue() {
        return pointValue;
    }

    /**
     * sets hand
     *
     * @param hand
     */
    void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    /**
     * sets pointValue
     *
     * @param pointValue
     */
    void setPointValue(int pointValue) {
        this.pointValue = pointValue;
    }
}
