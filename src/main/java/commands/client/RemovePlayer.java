
package commands.client;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * When a player joins to the server, server introduces the new player to all other players with this command.
 */
public class RemovePlayer extends ClientCommand {
    /**
     * PROCTECTS
     */
    protected String playerName;
    protected int playerId;
    protected int seatNumber;

    /**
     * CONSTRUCTOR
     */
    public RemovePlayer() {
        super(CommandNames.REMOVE_PLAYER);
    }

    /**
     * CONSTRUCTOR
     *
     * @param name
     */
    public RemovePlayer(CommandNames name) {
        super(name);
    }

    /**
     * CONSTUCTOR
     *
     * @param playerName name of the new player
     * @param playerId   id of the new player
     * @param seatNumber the seat number of the new player.
     */
    public RemovePlayer(String playerName, int playerId, int seatNumber) {
        this();

        this.seatNumber = seatNumber;
        this.playerName = URLEncoder.encode(playerName);
        this.playerId = playerId;

        this.addParameter(playerName);
        this.addParameter(playerId);
        this.addParameter(seatNumber);


    }

    /**
     * @param commandStr
     */
    @Override
    public void doParse(String commandStr) {
        playerName = URLDecoder.decode(scanner.next());
        playerId = scanner.nextInt();
        seatNumber = scanner.nextInt();
    }

    /**
     *
     */
    @Override
    public void doExecute() {
        manager.removePlayer(playerName, playerId, seatNumber, false);
    }
}
