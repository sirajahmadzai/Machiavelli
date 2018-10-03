package commands.client;

import commands.Command;


public class IntroducePlayer extends ClientCommand {
    protected String playerName;
    protected int playerId;
    protected int seatNumber;

    public IntroducePlayer(String playerName, int playerId, int seatNumber) {
        super(Command.CommandNames.INTRODUCE_PLAYER);

        this.seatNumber = seatNumber;
        this.playerName = playerName;
        this.playerId = playerId;

        this.addParameter(playerName);
        this.addParameter(playerId);
        this.addParameter(seatNumber);
    }

    public void doExecute() {
        manager.introducePlayer(playerName, playerId, seatNumber, false);
    }
}
