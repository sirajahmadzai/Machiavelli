package server.gameLogic;

import commands.Command;

public class GamePlayState extends GameState {
    /**
     * PRIVATE STATICS
     */
    private static GamePlayState ourInstance = new GamePlayState();

    /**
     * @return
     */
    public static GamePlayState getInstance() {
        return ourInstance;
    }

    /**
     *
     */
    private GamePlayState() {
    }

    /**
     * @param cmd
     */
    @Override
    public void handleCommand(Command cmd) {

    }
}
