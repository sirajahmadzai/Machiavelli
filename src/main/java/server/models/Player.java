package server.models;

import server.ClientHandler;
import server.models.cards.Card;

import java.util.ArrayList;
import java.util.StringJoiner;

public class Player {

    /********************************
     ******** PRIVATES **************
     ********************************/
    private int playerID;
    private String name;
    private ArrayList<Card> hand;
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

        hand = new ArrayList<>();
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
    public ArrayList<Card> getHand() {
        return hand;
    }

    public String getHandAsText() {
        StringJoiner joiner = new StringJoiner(" ");
        for(Card card : hand){
            joiner.add(card.toString());
        }

        return joiner.toString();
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
