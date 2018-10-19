package client.views.components;

import client.CardEvent;
import client.PlayerPosition;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.VBox;
import server.models.cards.Card;

import java.util.Random;

public class Player extends Group implements EventHandler<CardEvent> {
    private PlayerPosition position;
    private String name;
    private int playerId;
    private int seatNumber;
    private PlayerInfo playerInfo;
    private CardSetView hand;
    public VBox container = new VBox();

    public Player() {
        this.playerInfo = new PlayerInfo("Not joined yet!");
        this.hand = new CardSetView();
        hand.setCardEventHandler(this);
        hand.setCheckForValidity(false);
        initContainer();
    }

    private void initContainer() {
        this.getChildren().add(container);
        Random r = new Random();
        container.setAlignment(Pos.CENTER);
        container.setPrefHeight(CardView.CARD_PREF_HEIGHT);

//        Random bg color for debugging.
//        container.setBackground(new Background(new BackgroundFill(
//                Color.color(r.nextDouble(), r.nextDouble(), r.nextDouble(), 0.7)
//                , null, null)));
        container.setSpacing(5);
        container.getChildren().add(playerInfo);
        container.getChildren().add(hand);
    }

    public CardView addCardToHand(Card card) {
        CardView newCardView = hand.addCard(card);
        updateInfoText();
        return newCardView;
    }

    public PlayerPosition getPosition() {
        return position;
    }

    public void setPosition(PlayerPosition position) {
        this.position = position;
        switch (position) {
            case BOTTOM:
                container.setRotate(0);
                break;
            case TOP:
                container.setRotate(180);
                this.playerInfo.setRotate(180);
                break;
            case LEFT:
                container.setRotate(90);
                break;
            case RIGHT:
                container.setRotate(-90);
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

    public int getSeatNumber() {
        return seatNumber;
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

    public void setActive(boolean active) {
        playerInfo.setActive(active);
    }

    @Override
    public void handle(CardEvent event) {
        updateInfoText();
    }

    /**
     * When the {@code hand} scaled parent container doesn't update it's height automatically.
     * So after the hand is scaled we move it close back to the playerInfo.
     * @param scale
     */
    public void setScale(double scale) {
        hand.setScaleX(scale);
        hand.setScaleY(scale);

        double translateCards = CardView.CARD_PREF_HEIGHT / 2 * (1 - scale);
        hand.setTranslateY(-translateCards);
    }
}
