package commands.server;

/**
 * When player can not play any card from hand this command lets server to pass the turn to the next player.
 */
public class PassTurn extends ServerCommand {
    /**
     *
     */
    public PassTurn() {
        super(CommandNames.PASS_TURN);
    }

    /**
     * @param commandStr
     */
    @Override
    public void doParse(String commandStr) {
        // No parameter to parse.
    }

    /**
     * Just call the relative method.
     */
    @Override
    protected void doExecute() {
        machiavelli.passTurn();
    }
}
