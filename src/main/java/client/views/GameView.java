package client.views;

import client.ClientManager;
import client.GameSeats;
import client.ViewHelper;
import client.views.components.CardSetView;
import client.views.components.CardView;
import client.views.components.PlayArea;
import client.views.components.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import server.models.CardSet;
import server.models.cards.Card;
import server.models.cards.HiddenCard;

public class GameView extends View {
    /*************************************************************
     ************************FXML INJECTIONS**********************
     *************************************************************/
    @FXML // fx:id="board"
    private BorderPane board; // Value injected by FXMLLoader

    @FXML
    private ImageView deckImageView;

    @FXML
    private Text messageBox;

    @FXML
    private FlowPane setsArea;

    @FXML
    private Button revertButton;

    @FXML
    public void onRevertClicked(ActionEvent ae){
        resetMove();
    }

    /*************************************************************
     ************************END OF FXML INJECTIONS***************
     *************************************************************/

    /*************************************************************
     *****************************PRIVATES************************
     *************************************************************/

    private PlayArea playArea;

    private GameSeats seats;

    private int playerCount = 0;

    private static GameView ourInstance = new GameView();

    public static GameView getInstance() {
        return ourInstance;
    }

    private GameView() {
        super();
        fxml = "/fxml/GameView.fxml";

        try {
            loadFxml();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Number of players the game set up for.
     * @return
     */
    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
        this.seats = new GameSeats(board, playerCount);
        this.playArea = new PlayArea(setsArea);

        deckImageView.setImage(ViewHelper.getImage(Card.BACK_OF_CARD_IMAGE));
        deckImageView.setVisible(false);
        deckImageView.setOnMouseClicked(event -> ClientManager.getInstance().endTurn(event));
        setMessage("Waiting for other players to join.");
    }

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

    }

    /************************************************************
     ********** MODIFIERS TO BE USED BY CONTROLLERS **************
     *************************************************************/

    /**
     * set the text for messageBox
     *
     * @param message
     */
    public void setMessage(String message) {
        messageBox.setText(message);
    }

    public void clearMessage() {
        setMessage("");
    }

    /***********************************************
     *************************HANDS******************
     ************************************************/
    public CardView addCardToHand(int seatNumber, Card card) {
        Player player = seats.getPlayer(seatNumber);
        return player.addCardToHand(card);
    }

    /**
     * Deal 1 card to each opponent
     */
    public void dealHands() {
        // TODO: maybe an animation?
        for (Player player : seats.getOpponents()) {
            player.addCardToHand(HiddenCard.getInstance());
        }
    }

    /***********************************************
     **************END OF HAND METHODS**************
     ************************************************/

    /***********************************************
     ******************PLAY AREA*************************
     ************************************************/
    /**
     * empties deckOfCards
     */
    public void emptyDeck() {
        deckImageView.setVisible(false);
    }

    public void fillDeck() {
        deckImageView.setVisible(true);
    }


    /********************************************************
     ************* HELPERS********************
     *********************************************************************************/

    /**
     * Set the owner player id and seat number for this client.
     * @param ownerPlayerId
     * @param seatNumber
     */
    public void setOwnerPlayer(int ownerPlayerId, int seatNumber) {
        seats.setOwnerSeat(seatNumber, ownerPlayerId);
    }

    public int getOwnerSeat() {
        return seats.getOwnerPlayer().getSeatNumber();
    }

    /**
     * Add new player to specified seat.
     * @param playerName
     * @param playerId
     * @param seatNumber
     */
    public void fillSeat(String playerName, int playerId, int seatNumber) {
        seats.setPlayerInfo(seatNumber, playerName, playerId);
    }

    public void setPlayAreaActive(boolean active) {
        playArea.setActive(active);
        seats.getOwnerPlayerHand().setReceiverMode(active);
    }

    public void setPlayAreaActive(CardSet cardSet) {
        playArea.setActive(cardSet);
        seats.getOwnerPlayerHand().setReceiverMode(cardSet);
    }

    public void takeSnapshot() {
        playArea.takeSnapshot();
        seats.getOwnerPlayerHand().takeSnapshot();
    }

    public PlayArea getPlayArea() {
        return playArea;
    }

    public CardSetView getHand() {
        return seats.getOwnerPlayerHand();
    }

    public void switchTurn(int seatNumber) {
        for (int i = 1; i <= playerCount; i++) {
            seats.getPlayer(i).setActive(i == seatNumber);
        }
        if (seatNumber == getOwnerSeat()) {
            setMessage("It's your turn. Click on the deck when you're done.");
        }else {
            setMessage("Please wait for your turn.");
        }
    }

    private void resetMove() {
        playArea.rollbackMoves();
        getHand().rollbackMoves();
    }

    public void removeCardsFrom(int seatNumber, CardSet playedCards) {
        Player player = seats.getPlayer(seatNumber);

        player.getHand().removeCards(playedCards.totalCount());
    }


}
