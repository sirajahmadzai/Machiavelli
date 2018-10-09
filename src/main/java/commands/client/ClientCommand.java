package commands.client;

import client.ClientManager;
import commands.Command;
import javafx.application.Platform;

public abstract class ClientCommand extends Command {
    protected final ClientManager manager = ClientManager.getInstance();

    public ClientCommand(CommandNames name) {
        super(name);
    }

    public ClientCommand(String commandStr) {
        super(commandStr);
    }

    @Override
    public void execute() {
        Platform.runLater(this::doExecute);
    }

}
