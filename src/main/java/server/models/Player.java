package server.models;

import server.ClientHandler;
import server.models.cards.Card;

import java.util.ArrayList;

public class Player {

    /********************************
     ******** PRIVATES **************
     ********************************/
    private int playerID;
    private String name;
    private CardSet hand;
    private int pointValue;
    private ClientHandler clientHandler;
    private int seatNumber;

    /**
     * CONSTRUCTOR
     *
     * @param playerID
     * @param name
     */
    public Player(int playerID, String name) {
        this.playerID = playerID;
        this.name = name;
//        this.seatNumber = seatNumber;

        hand = new CardSet();
        pointValue = 0;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    public void setClientHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    /**
     * gets player id
     *
     * @return
     */
    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
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
    public CardSet getHand() {
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
        this.hand = new CardSet(hand);
    }

    /**
     * sets pointValue
     *
     * @param pointValue
     */
    public void setPointValue(int pointValue) {
        this.pointValue = pointValue;
    }

    public void setName(String name) {
        this.name = name;
    }
}
