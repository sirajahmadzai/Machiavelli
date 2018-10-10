package commands.server;

import java.net.URLEncoder;

public class PlayerLogin extends ServerCommand {
    private String playerName;

    public PlayerLogin() {
        super(CommandNames.PLAYER_LOGIN);
    }

    public PlayerLogin(String playerName) {
        this();
        this.playerName = playerName;

        this.addParameter(playerName);
    }

    @Override
    public void doParse(String commandStr) {
        playerName = URLEncoder.encode(scanner.nextLine());
    }

    @Override
    public void doExecute() {
        // Do nothing.
    }

    public String getPlayerName() {
        return playerName;
    }
}
