package commands.server;

import commands.client.ClientCommand;

public class WinnerCommand extends ClientCommand {

    protected int winnerSeatNumber;
    public WinnerCommand() {
        super(CommandNames.SET_WINNER);
    }

    public WinnerCommand(String cmdString) {
        super(cmdString);
    }

    public WinnerCommand(int winnerSeatNumber) {
        this();
        this.winnerSeatNumber = winnerSeatNumber;
        this.addParameter(winnerSeatNumber);
    }

    @Override
    public void doParse(String commandString) {
        winnerSeatNumber =scanner.nextInt();
    }

    /**
     *
     */
    @Override
    public void doExecute() {
        manager.setWinner(winnerSeatNumber);
    }

    public int getWinnerSeatNumber() {
        return winnerSeatNumber;
    }
}
