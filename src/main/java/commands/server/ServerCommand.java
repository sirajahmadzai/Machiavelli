package commands.server;

import commands.Command;
import server.models.Machiavelli;

public abstract class ServerCommand extends Command {
    protected Machiavelli machiavelli = Machiavelli.getInstance();

    public ServerCommand(CommandNames name) {
        super(name);
    }

    public ServerCommand(String cmdString) {
        super(cmdString);
    }

    @Override
    public void execute() {
        doExecute();
    }
}
