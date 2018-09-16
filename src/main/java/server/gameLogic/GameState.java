package server.gameLogic;

import commands.Command;

public abstract class GameState {
    public abstract void handleCommand(Command cmd);
}
