package commands.client;

import commands.Command;

public class IntroducePlayer extends ClientCommand {
    protected String playerName;
    protected int playerId;
    protected int seatNumber;

    public IntroducePlayer() {
        super(Command.CommandNames.INTRODUCE_PLAYER);
    }

    public IntroducePlayer(CommandNames name) {
        super(name);
    }

    public IntroducePlayer(String playerName, int playerId, int seatNumber) {
        this();

        this.seatNumber = seatNumber;
        this.playerName = playerName;
        this.playerId = playerId;

        this.addParameter(playerName);
        this.addParameter(playerId);
        this.addParameter(seatNumber);
    }

    @Override
    public void doParse(String commandStr) {
        playerName = scanner.next();
        playerId = scanner.nextInt();
        seatNumber = scanner.nextInt();
    }

    @Override
    public void doExecute() {
        manager.introducePlayer(playerName, playerId, seatNumber, false);
    }
}
