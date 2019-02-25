package commands.client;

import commands.Command;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * When a player joins to the server, server introduces the new player to all other players with this command.
 */
public class IntroducePlayer extends ClientCommand {
    /**
     * PROCTECTS
     */
    protected String playerName;
    protected int playerId;
    protected int seatNumber;

    /**
     * CONSTRUCTOR
     */
    public IntroducePlayer() {
        super(Command.CommandNames.INTRODUCE_PLAYER);
    }

    /**
     * CONSTRUCTOR
     *
     * @param name
     */
    public IntroducePlayer(CommandNames name) {
        super(name);
    }

    /**
     * CONSTUCTOR
     *
     * @param playerName name of the new player
     * @param playerId   id of the new player
     * @param seatNumber the seat number of the new player.
     */
    public IntroducePlayer(String playerName, int playerId, int seatNumber) {
        this();

        this.seatNumber = seatNumber;
        this.playerName = URLEncoder.encode(playerName);
        this.playerId = playerId;

        this.addParameter(this.playerName);
        this.addParameter(this.playerId);
        this.addParameter(this.seatNumber);
    }

    /**
     * @param commandStr
     */
    @Override
    public void doParse(String commandStr) {
        playerName = scanner.next();
        playerId = scanner.nextInt();
        seatNumber = scanner.nextInt();
    }

    /**
     *
     */
    @Override
    public void doExecute() {
        manager.introducePlayer(URLDecoder.decode(playerName), playerId, seatNumber);
    }
}
