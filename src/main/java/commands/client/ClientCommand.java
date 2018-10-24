package commands.client;

import client.ClientManager;
import commands.Command;
import javafx.application.Platform;

/**
 * Base class for all client commands.
 * This class just offers a ClientManager instance for easy access from within subclasses.
 * And implements abstract execute method.
 */
public abstract class ClientCommand extends Command {
    /**
     * PROTECTS
     */
    protected final ClientManager manager = ClientManager.getInstance();

    /**
     * CONSTRUCTOR
     *
     * @param name
     */
    public ClientCommand(CommandNames name) {
        super(name);
    }

    /**
     * CONSTRUCTOR
     *
     * @param commandStr
     */
    public ClientCommand(String commandStr) {
        super(commandStr);
    }

    /**
     * Runs the command inside the main UI thread.
     */
    @Override
    public void execute() {
        Platform.runLater(this::doExecute);
    }

}
