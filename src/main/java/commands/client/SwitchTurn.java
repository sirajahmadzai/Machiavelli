package commands.client;

/**
 * Server tells clients to switch the turn to the next player.
 */
public class SwitchTurn extends ClientCommand {
    /**
     * PRIVATES
     */
    private int seatNumber;


    /**
     * CONSTRUCTOR
     */
    public SwitchTurn() {
        super(CommandNames.SWITCH_TURN);
    }

    /**
     * CONSTRUCTOR
     *
     * @param commandStr
     */
    public SwitchTurn(String commandStr) {
        super(commandStr);
    }

    /**
     * CONSTRUCTOR
     *
     * @param seatNumber the seat number of the turn
     */
    public SwitchTurn(int seatNumber) {
        this();
        this.seatNumber = seatNumber;
        addParameter(seatNumber);
    }

    /**
     * @param commandStr
     */
    @Override
    public void doParse(String commandStr) {
        this.seatNumber = scanner.nextInt();
    }

    /**
     *
     */
    @Override
    public void doExecute() {
        manager.switchTurn(seatNumber);
    }
}
