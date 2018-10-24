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
    /**
     * PRIVATES
     */
    private PlayerPosition position;
    private String name;
    private int playerId;
    private int seatNumber;
    private PlayerInfo playerInfo;
    private CardSetView hand;

    /**
     * PUBLICS
     */
    public VBox container = new VBox();

    /**
     * CONSTRUCTOR
     */
    public Player() {
        this.playerInfo = new PlayerInfo("Not joined yet!");
        this.hand = new CardSetView();
        hand.setCardEventHandler(this);
        hand.setCheckForValidity(false);
        initContainer();
    }

    /**
     * sets up this player's container
     */
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

    /**
     * gets this player's position
     *
     * @return
     */
    public PlayerPosition getPosition() {
        return position;
    }

    /**
     * gets this player's name
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * gets this player's playerId
     *
     * @return
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * gets this player's seatNumber
     *
     * @return
     */
    public int getSeatNumber() {
        return seatNumber;
    }

    /**
     * gets this player's hand
     *
     * @return
     */
    public CardSetView getHand() {
        return hand;
    }

    /**
     * sets this player's position
     *
     * @param position
     */
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

    /**
     * sets this player's name
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
        updateInfoText();
    }


    /**
     * sets this player's playerId
     *
     * @param playerId
     */
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    /**
     * sets this player's seatNumber
     *
     * @param seatNumber
     */
    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }


    /**
     * sets this player's active value
     *
     * @param active
     */
    public void setActive(boolean active) {
        playerInfo.setActive(active);
    }

    /**
     * When the {@code hand} scaled parent container doesn't update it's height automatically.
     * So after the hand is scaled we move it close back to the playerInfo.
     *
     * @param scale
     */
    public void setScale(double scale) {
        hand.setScaleX(scale);
        hand.setScaleY(scale);

        double translateCards = CardView.CARD_PREF_HEIGHT / 2 * (1 - scale);
        hand.setTranslateY(-translateCards);
    }

    /**
     * adds a card to this player's CardSetView (hand)
     *
     * @param card
     * @return CardSetView object newCardView
     */
    public CardView addCardToHand(Card card) {
        CardView newCardView = hand.addCard(card);
        updateInfoText();
        return newCardView;
    }


    /**
     * update player's infoText
     */
    private void updateInfoText() {
        String infoText = this.name;
        int cardCount = hand.getCardCount();

        if (cardCount > 0) {
            infoText += " (" + cardCount + " Cards)";
        }
        this.playerInfo.setInfoText(infoText);
    }

    /**
     * @param event
     */
    @Override
    public void handle(CardEvent event) {
        updateInfoText();
    }
}
