package server.gameLogic;

import commands.Command;

public class GamePlayState extends GameState{
    private static GamePlayState ourInstance = new GamePlayState();

    public static GamePlayState getInstance() {
        return ourInstance;
    }

    private GamePlayState() {
    }

    @Override
    public void handleCommand(Command cmd) {

    }
}
