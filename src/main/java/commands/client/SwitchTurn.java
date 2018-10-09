package commands.client;

public class SwitchTurn extends ClientCommand {
    private int seatNumber;


    public SwitchTurn() {
        super(CommandNames.SWITCH_TURN);
    }

    public SwitchTurn(String commandStr) {
        super(commandStr);
    }

    public SwitchTurn(int seatNumber) {
        this();
        this.seatNumber = seatNumber;
        addParameter(seatNumber);
    }

    @Override
    public void doParse(String commandStr) {
        this.seatNumber = scanner.nextInt();
    }

    @Override
    public void doExecute() {
        manager.switchTurn(seatNumber);
    }
}
