package commands.client;

public class Welcome extends IntroducePlayer{
    private int numOfPlayers;
    public Welcome(String playerName, int playerId, int seatNumber, int numOfPlayers) {
        super(playerName, playerId, seatNumber);

        this.numOfPlayers = numOfPlayers;
        this.addParameter(numOfPlayers);
        this.name = CommandNames.WELCOME;
    }

    public void doExecute() {
        manager.startGame(numOfPlayers);
        manager.introducePlayer(playerName, playerId, seatNumber, true);
    }


}
