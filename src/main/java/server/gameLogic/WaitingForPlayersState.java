package server.gameLogic;

import commands.Command;

public class WaitingForPlayersState extends GameState {
    private static WaitingForPlayersState ourInstance = new WaitingForPlayersState();

    public static WaitingForPlayersState getInstance() {
        return ourInstance;
    }

    private WaitingForPlayersState() {
    }

    @Override
    public void handleCommand(Command cmd) {

    }
}
