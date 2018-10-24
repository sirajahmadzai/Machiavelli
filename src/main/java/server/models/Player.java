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

    /**
     * GETTERS
     */
    /**
     * gets the clientHandler object
     *
     * @return
     */
    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    /**
     * gets player id
     *
     * @return
     */
    public int getSeatNumber() {
        return seatNumber;
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
     * SETTERS
     */
    /**
     * sets this player's hand
     *
     * @param hand
     */
    public void setHand(ArrayList<Card> hand) {
        this.hand = new CardSet(hand);
    }

    /**
     * sets this player's pointValue
     *
     * @param pointValue
     */
    public void setPointValue(int pointValue) {
        this.pointValue = pointValue;
    }

    /**
     * sets this player's name
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * sets this player's clientHandler object
     *
     * @param clientHandler
     */
    public void setClientHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    /**
     * sets this player's seatNumber
     *
     * @param seatNumber
     */
    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }
}
