package commands.server;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * To let the server the logged in player name.
 */
public class PlayerLogin extends ServerCommand {
    /**
     * PRIVATES
     */
    private String playerName;

    /**
     * CONSTRUCTOR
     */
    public PlayerLogin() {
        super(CommandNames.PLAYER_LOGIN);
    }

    /**
     * CONSTRUCTOR
     *
     * @param playerName
     */
    public PlayerLogin(String playerName) {
        this();
        this.playerName = URLEncoder.encode(playerName);
        this.addParameter(this.playerName);
    }

    /**
     * @param commandStr
     */
    @Override
    public void doParse(String commandStr) {
        playerName = scanner.next();
    }

    /**
     *
     */
    @Override
    public void doExecute() {
        // Do nothing.
    }

    /**
     * @return
     */
    public String getPlayerName() {
        return URLDecoder.decode(playerName);
    }
}
