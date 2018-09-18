package client;

import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import server.models.cards.Card;

public class Player extends HBox {
    private PlayerPosition position;
    private String name;
    private int playerId;
    private int seatNumber;
    private PlayerInfo playerInfo;
    private HBox cards;

    public Player() {
        this.setAlignment(Pos.CENTER);

        this.playerInfo = new PlayerInfo("Not joined yet!");
        this.cards = new HBox();
        this.cards.setSpacing(-100);
        this.cards.setAlignment(Pos.CENTER);

        this.setSpacing(20);
        this.getChildren().add(playerInfo);
        this.getChildren().add(cards);
    }

    public void addCardToHand(Card card) {
        ImageView cardImage = ViewHelper.createCard(card);
        addCardToHand(cardImage);
    }

    public void addHiddenCardToHand() {
        ImageView cardImage = ViewHelper.createImageView(Card.BACK_OF_CARD_IMAGE);
        addCardToHand(cardImage);
    }

    private void addCardToHand(ImageView cardImage) {
        cards.getChildren().add(cardImage);
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
        this.playerInfo.setName(name);
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

}
