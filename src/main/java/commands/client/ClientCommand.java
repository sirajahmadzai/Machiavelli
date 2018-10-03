package commands.client;

import client.Client;
import client.ClientManager;
import commands.Command;
import javafx.application.Platform;

public class ClientCommand extends Command {
    protected final ClientManager manager;

    public ClientCommand() {
        manager = ClientManager.getInstance();
    }

    public ClientCommand(CommandNames name) {
        this();
        this.name = name;
    }

    @Override
    public void execute() {
        Platform.runLater(() -> doExecute());
    }

    @Override
    protected void doExecute() {
        System.out.println("Command executing itself.");
    }
}
