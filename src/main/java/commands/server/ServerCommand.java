package commands.server;

import commands.Command;
import server.models.Machiavelli;

/**
 * Base class for all server commands.
 * This class just offers a Machiavelli instance for easy access from within subclasses.
 * And implements abstract execute method.
 */
public abstract class ServerCommand extends Command {
    /**
     * PROTECTS
     */
    protected Machiavelli machiavelli = Machiavelli.getInstance();

    /**
     * CONSTRUCTOR
     *
     * @param name
     */
    public ServerCommand(CommandNames name) {
        super(name);
    }

    /**
     * CONSTRUCTOR
     *
     * @param cmdString
     */
    public ServerCommand(String cmdString) {
        super(cmdString);
    }

    /**
     * Just call doExecute to let subclass do it's job.
     */
    @Override
    public void execute() {
        doExecute();
    }
}
