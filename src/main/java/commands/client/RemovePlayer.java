
package commands.client;

/**
 *
 */
public class RemovePlayer extends ClientCommand {
    /**
     * PROCTECTS
     */
    protected int seatNumber;

    /**
     * CONSTRUCTOR
     */
    public RemovePlayer() {
        super(CommandNames.REMOVE_PLAYER);
    }

    /**
     * CONSTUCTOR
     *
     * @param seatNumber the seat number of the new player.
     */
    public RemovePlayer(int seatNumber) {
        this();
        this.seatNumber = seatNumber;
        this.addParameter(seatNumber);
    }

    /**
     * @param commandStr
     */
    @Override
    public void doParse(String commandStr) {
        seatNumber = scanner.nextInt();
    }

    /**
     *
     */
    @Override
    public void doExecute() {
        manager.removePlayer(seatNumber);
    }
}
