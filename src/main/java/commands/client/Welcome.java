package commands.client;

import utils.constants;

/**
 * When a player joins the game server lets him know
 * 1- The number of players the game is set up for
 * 2- The seat number assign to the player
 * 3- The player id assigned to the player
 */
public class Welcome extends IntroducePlayer {

    /**
     * PRIVATES
     */
    private int numOfPlayers;
    private constants.GameMode gameMode;


    /**
     * CONSTRUCTOR
     */
    public Welcome() {
        super(CommandNames.WELCOME);
    }

    /**
     * CONSTRUCTOR
     *
     * @param playerName   name of the new player
     * @param playerId     id of the new player
     * @param seatNumber   the seat number of the new player.
     * @param numOfPlayers the number of players the game is set up for
     */
    public Welcome(String playerName, int playerId, int seatNumber, int numOfPlayers, constants.GameMode gameMode) {
        super(playerName, playerId, seatNumber);

        this.name = CommandNames.WELCOME;
        this.numOfPlayers = numOfPlayers;
        this.gameMode = gameMode;
        this.addParameter(numOfPlayers);
        this.addParameter(gameMode);
    }

    /**
     *
     */
    @Override
    public void doExecute() {
        manager.joinTable(numOfPlayers);
        manager.welcomePlayer(playerName, playerId, seatNumber, gameMode);
    }

    /**
     * @param commandStr
     */
    @Override
    public void doParse(String commandStr) {
        super.doParse(commandStr);
        numOfPlayers = scanner.nextInt();
        gameMode = constants.GameMode.valueOf(scanner.next());
    }
}
