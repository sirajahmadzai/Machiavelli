package commands.server;

import commands.BasicCommand;
import server.models.CardSet;

import java.util.ArrayList;
import java.util.List;

/**
 * The command that represents a player move. This is used both by server and client.
 */
public class PlayerMove extends BasicCommand {
    /**
     * PROTECTS
     */
    protected int seatNumber;
    protected List<CardSet> table;
    protected CardSet playedCards;

    /**
     * CONSTRUCTOR
     */
    public PlayerMove() {
        super(CommandNames.PLAYER_MOVE);
        table = new ArrayList<>();
    }

    /**
     * CONSTRUCTOR
     *
     * @param cmdString
     */
    public PlayerMove(String cmdString) {
        super(cmdString);
        table = new ArrayList<>();
    }

    /**
     * CONSTRUCTOR
     *
     * @param seatNumber  the seat number of the player who made the move
     * @param playedCards the cards player puts on the table from his hand.
     * @param table       resulting sets of cards that are on the table after the proposed move has been made.
     */
    public PlayerMove(int seatNumber, CardSet playedCards, List<CardSet> table) {
        this();
        this.seatNumber = seatNumber;
        this.table = table;
        this.playedCards = playedCards;

        this.addParameter(seatNumber);
        this.addParameter(playedCards);
        for (CardSet set : table) {
            this.addParameter(set.toString());
        }
    }

    /**
     * GETTERS
     */
    public int getSeatNumber() {
        return seatNumber;
    }


    public List<CardSet> getTable() {
        return table;
    }


    public CardSet getPlayedCards() {
        return playedCards;
    }


    /**
     * SETTERS
     */
    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public void setTable(List<CardSet> table) {
        this.table = table;
    }

    public void setPlayedCards(CardSet playedCards) {
        this.playedCards = playedCards;
    }


    /**
     * @param commandStr
     */
    @Override
    public void doParse(String commandStr) {


        table.clear();
        int count = 0;

        for (Object param : parameters) {
            if (count == 0) {
                seatNumber = Integer.parseInt(param.toString());
            } else if (count == 1) {
                playedCards = new CardSet(param.toString());
            } else {
                table.add(new CardSet(param.toString()));
            }
            count++;
        }
    }
}
