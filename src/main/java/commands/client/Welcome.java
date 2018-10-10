package commands.client;

public class Welcome extends IntroducePlayer{
    private int numOfPlayers;

    public Welcome() {
        super(CommandNames.WELCOME);
    }

    public Welcome(String playerName, int playerId, int seatNumber, int numOfPlayers) {
        super(playerName, playerId, seatNumber);

        this.name = CommandNames.WELCOME;
        this.numOfPlayers = numOfPlayers;
        this.addParameter(numOfPlayers);
    }

    @Override
    public void doExecute() {
        manager.startGame(numOfPlayers);
        manager.introducePlayer(playerName, playerId, seatNumber, true);
    }

    @Override
    public void doParse(String commandStr) {
        super.doParse(commandStr);
        numOfPlayers = scanner.nextInt();
    }
}
