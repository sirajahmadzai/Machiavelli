package client;

import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.HashMap;


public class GameSeats {
    private BorderPane board;
    private HashMap<Integer, Player> seats;
    private ArrayList<Player> players;
    private int numberOfPlayers;
    private Player ownerPlayer;
    private ArrayList<Player> opponents;

    /**
     *
     * @param board the BorderPane instance to place the players on.
     * @param numberOfPlayers
     */
    public GameSeats(BorderPane board, int numberOfPlayers) {
        this.board = board;
        this.numberOfPlayers = numberOfPlayers;
        this.seats = new HashMap<>(numberOfPlayers);
        this.players = new ArrayList<>(numberOfPlayers);
        this.opponents = new ArrayList<>(numberOfPlayers-1);

        PlayerPosition[] positions = {PlayerPosition.BOTTOM, PlayerPosition.LEFT, PlayerPosition.TOP, PlayerPosition.RIGHT};

        // Put 2nd player at top if there are only 2 players.
        if (numberOfPlayers == 2) {
            positions[1] = PlayerPosition.TOP;
        }

//        Place dummy players clockwise around the table.
//        First one is the owner of this client's hand so it's placed at the bottom.
        for (int i = 0; i < numberOfPlayers; i++) {
            Player player = new Player();

            player.setPlayerId(i);  //TODO: Get this id from server!
            player.setPosition(positions[i]);

            players.add(player);
            if(i>0){
                opponents.add(player);
            }

            switch (player.getPosition()) {
                case TOP:
                    board.setTop(player);
                    break;
                case BOTTOM:
                    board.setBottom(player);
                    break;
                case LEFT:
                    board.setLeft(player);
                    break;
                case RIGHT:
                    board.setRight(player);
                    break;
            }
        }
        ownerPlayer = players.get(0);
    }

    public Player getPlayer(int seatNumber) {
        return seats.get(seatNumber);
    }

    public void setOwnerSeat(int ownerSeatNumber, int ownerId) {
        ownerPlayer.setPlayerId(ownerId);

//      Distribute the seat numbers from the perspective of this (owner) client.
//      Start from ownerSeatNumber as the bottom player and increase the seat numbers in clockwise.
        int seatNo = ownerSeatNumber;
        for (Player p : players) {
            seats.put(seatNo, p);
            p.setSeatNumber(seatNo);
            seatNo++;
            if (seatNo > numberOfPlayers) {
                seatNo = 1;
            }
        }
    }

    public void setPlayerInfo(int seatNumber, String playerName, int playerId) {
        Player player= seats.get(seatNumber);
        player.setPlayerId(playerId);
        player.setName(playerName);
    }

    public ArrayList<Player> getOpponents() {
        return opponents;
    }

    public void setOpponents(ArrayList<Player> opponents) {
        this.opponents = opponents;
    }
}
