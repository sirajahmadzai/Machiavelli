package commands.server;

public class PassTurn extends ServerCommand {
    public PassTurn() {
        super(CommandNames.PASS_TURN);
    }

    @Override
    public void doParse(String commandStr) {
        // No parameter to parse.
    }

    @Override
    protected void doExecute() {
        machiavelli.passTurn();
    }
}
