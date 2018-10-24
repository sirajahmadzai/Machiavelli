package server.gameLogic;

import commands.Command;

public class WaitingForPlayersState extends GameState {
    /**
     * PRIVATE STATICS
     */
    private static WaitingForPlayersState ourInstance = new WaitingForPlayersState();


    /**
     * CONSTRUCTOR
     */
    private WaitingForPlayersState() {
    }

    /**
     * GETTERS
     */
    /**
     * gets the instance for this Class
     *
     * @return
     */
    public static WaitingForPlayersState getInstance() {
        return ourInstance;
    }

    /**
     * @param cmd
     */
    @Override
    public void handleCommand(Command cmd) {

    }
}
