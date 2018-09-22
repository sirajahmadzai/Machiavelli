package client.views.components;

import client.PlayerPosition;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import server.models.cards.Card;

public class Player extends VBox {
    private PlayerPosition position;
    private String name;
    private int playerId;
    private int seatNumber;
    private PlayerInfo playerInfo;
    private CardSetView hand;

    public Player() {
        this.setAlignment(Pos.CENTER);

        this.playerInfo = new PlayerInfo("Not joined yet!");
        this.hand = new CardSetView();

        this.setSpacing(5);
        this.getChildren().add(playerInfo);
        this.getChildren().add(hand);
        setBackground(new Background(new BackgroundFill(Color.color(1,0,0,0.7),null,null)));
    }

    public void addCardToHand(Card card) {
        hand.addCard(card);
        updateInfoText();
    }

    public PlayerPosition getPosition() {
        return position;
    }

    public void setPosition(PlayerPosition position) {
        this.position = position;
        switch (position) {
            case BOTTOM:
                this.setRotate(0);
                break;
            case TOP:
                this.setRotate(180);
                this.playerInfo.setRotate(180);
                break;
            case LEFT:
                this.setRotate(90);
                break;
            case RIGHT:
                this.setRotate(-90);
                break;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        updateInfoText();
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public CardSetView getHand() {
        return hand;
    }

    private void updateInfoText() {
        String infoText = this.name;
        int cardCount = hand.getCardCount();

        if (cardCount > 0) {
            infoText += " (" + cardCount + " Cards)";
        }
        this.playerInfo.setInfoText(infoText);
    }
}
