package interfaces;

import client.views.components.CardSetView;
import client.views.components.Player;

import java.util.ArrayList;

public interface gameSeatsInterface {

    public Player getPlayer(int seatNumber);

    public void setOwnerSeat(int ownerSeatNumber, int ownerId);

    public void setPlayerInfo(int seatNumber, String playerName, int playerId);

    public ArrayList<Player> getOpponents();

    public void setOpponents(ArrayList<Player> opponents);

    public Player getOwnerPlayer();

    public CardSetView getOwnerPlayerHand();

}
