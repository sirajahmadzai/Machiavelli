package server.gameLogic;

import commands.Command;

public abstract class GameState {

    /**
     * @param cmd
     */
    public abstract void handleCommand(Command cmd);
}
