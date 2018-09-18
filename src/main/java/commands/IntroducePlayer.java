package commands;

import client.ClientManager;
import client.views.GameView;
import javafx.application.Platform;

import java.util.Scanner;
import java.util.Stack;
import java.util.StringJoiner;


public class IntroducePlayer extends Command {
    protected final ClientManager manager;
    protected String playerName;
    protected int playerId;
    protected int seatNumber;

    public IntroducePlayer(String playerName, int playerId, int seatNumber) {
        this.name = CommandNames.INTRODUCE_PLAYER;
        this.manager = ClientManager.getInstance();

        this.seatNumber = seatNumber;
        this.playerName = playerName;
        this.playerId = playerId;

        this.addParameter(playerName);
        this.addParameter(playerId);
        this.addParameter(seatNumber);
    }

    public void doExecute() {
        manager.introducePlayer(playerName, playerId, seatNumber, false);
    }
}
