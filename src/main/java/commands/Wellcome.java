package commands;

public class Wellcome extends IntroducePlayer{
    private int numOfPlayers;
    public Wellcome(String playerName, int playerId, int seatNumber, int numOfPlayers) {
        super(playerName, playerId, seatNumber);

        this.numOfPlayers = numOfPlayers;
        this.addParameter(numOfPlayers);
        this.name = CommandNames.WELLCOME;
    }

    public void doExecute() {
        manager.startGame(numOfPlayers);
        manager.introducePlayer(playerName, playerId, seatNumber, true);
    }


}
