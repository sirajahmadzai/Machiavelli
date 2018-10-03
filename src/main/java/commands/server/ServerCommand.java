package commands.server;

import commands.Command;
import server.models.Machiavelli;

public class ServerCommand extends Command {
    protected Machiavelli machiavelli = Machiavelli.getInstance();

    public ServerCommand(CommandNames name) {
        super(name);
    }

    @Override
    public void execute() {
        doExecute();
    }

    @Override
    protected void doExecute() {
        System.out.println("Server Command executing itself.");
    }
}
