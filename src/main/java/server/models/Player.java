package server.models;

import server.models.cards.Card;

import java.util.ArrayList;

public class Player {

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
    public Player(int playerID, String name) {
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
    public int getPlayerID() {
        return playerID;
    }

    /**
     * gets name
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * gets hand
     *
     * @return
     */
    public ArrayList<Card> getHand() {
        return hand;
    }

    /**
     * gets pointValue
     *
     * @return
     */
    public int getPointValue() {
        return pointValue;
    }

    /**
     * sets hand
     *
     * @param hand
     */
    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    /**
     * sets pointValue
     *
     * @param pointValue
     */
    public void setPointValue(int pointValue) {
        this.pointValue = pointValue;
    }
}
