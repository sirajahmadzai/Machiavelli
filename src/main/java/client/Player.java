package client;

import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import server.models.cards.Card;

public class Player extends HBox {
    private PlayerPosition position;
    private String name;
    private int sequence;
    private int playerId;

    public Player(int sequence) {
        this.sequence = sequence;
        this.setSpacing(-100);
        this.setAlignment(Pos.CENTER);
    }

    public void addCardToHand(Card card) {
        ImageView cardImage = ViewHelper.createCard(card);
        getChildren().add(cardImage);
    }

    public PlayerPosition getPosition() {
        return position;
    }

    public void setPosition(PlayerPosition position) {
        this.position = position;
        if(position==PlayerPosition.LEFT || position == PlayerPosition.RIGHT)
        this.setRotate(90);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSequence() {
        return sequence;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
