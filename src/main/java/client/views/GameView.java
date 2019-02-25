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
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import server.models.CardSet;
import server.models.cards.Card;
import server.models.cards.HiddenCard;
import utils.constants;

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
    private ScrollPane scrollArea;

    @FXML
    private Button revertButton;
    private constants.GameMode gameMode;

    @FXML
    public void onRevertClicked(ActionEvent ae) {
        rollbackMove();
    }

    /*************************************************************
     ************************END OF FXML INJECTIONS***************
     *************************************************************/

    /**
     * PRIVATE STATICS
     */
    // instance of GameView
    private static GameView ourInstance = new GameView();

    /*************************************************************
     *****************************PRIVATES************************
     *************************************************************/

    private PlayArea playArea;

    private GameSeats seats;

    private int playerCount = 0;

    private GameView() {
        super();
        fxml = "/fxml/GameView.fxml";

        try {
            loadFxml();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*******************
     ***** GETTERS *****
     *******************/
    /**
     * @return
     */
    public static GameView getInstance() {
        return ourInstance;
    }

    /**
     * @return
     */
    public int getOwnerSeat() {
        return seats.getOwnerPlayer().getSeatNumber();
    }

    /**
     * @return
     */
    public PlayArea getPlayArea() {
        return playArea;
    }

    /**
     * @return
     */
    public CardSetView getHand() {
        return seats.getOwnerPlayerHand();
    }

    /**********************
     ******* SETTERS ******
     **********************/
    /**
     * @param playerCount
     */
    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
        initializeView();
        setMessage("Waiting for other players to join.");
    }

    public void initializeView() {
        this.seats = new GameSeats(board, playerCount);
        this.playArea = new PlayArea(setsArea);

        deckImageView.setImage(ViewHelper.getImage(Card.BACK_OF_CARD_IMAGE));
        deckImageView.setOnMouseClicked(event -> ClientManager.getInstance().endTurn(event));
        deckImageView.setVisible(false);
        revertButton.setVisible(false);
    }

    public void removePlayer(int seatNumber) {
        Player player = seats.getPlayer(seatNumber);
        player.setName("Player disconnected");
        player.getHand().removeAllCards();
    }

    public void resetView() {
        this.seats.reset();
        this.playArea.reset();

        deckImageView.setVisible(false);
        revertButton.setVisible(false);
    }


    public void setMessage(String message) {
        messageBox.setText(message);
    }

    /**
     * activates playArea
     *
     * @param active
     */
    public void setPlayAreaActive(boolean active) {
        playArea.setActive(active);
        seats.getOwnerPlayerHand().setReceiverMode(active);
    }

    /**
     * activates playArea
     *
     * @param cardSet
     */
    public void setPlayAreaActive(CardSet cardSet) {
        playArea.setActive(cardSet);
        seats.getOwnerPlayerHand().setReceiverMode(cardSet);
    }


    /**
     * Set the owner player id and seat number for this client.
     *
     * @param ownerPlayerId
     * @param seatNumber
     */
    public void setOwnerPlayer(int ownerPlayerId, int seatNumber) {
        seats.setOwnerSeat(seatNumber, ownerPlayerId);
    }

    /**
     * This method is called by the FXMLLoader when initialization is complete
     */
    @FXML
    void initialize() {
    }

    /************************************************************
     ********** MODIFIERS TO BE USED BY CONTROLLERS **************
     *************************************************************/
    /**
     * clears the messageBox
     */
    public void clearMessage() {
        setMessage("");
    }

    /***********************************************
     *************************HANDS******************
     ************************************************/

    /**
     * adds the given card to the player at the given seatNumber
     *
     * @param seatNumber
     * @param card
     * @return
     */
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
     * fills the deck
     */
    public void fillDeck() {
        deckImageView.setVisible(true);
    }


    /****************************
     ********* HELPERS **********
     ****************************
     /**
     * Add new player to specified seat.
     *
     * @param playerName
     * @param playerId
     * @param seatNumber
     */
    public void fillSeat(String playerName, int playerId, int seatNumber) {
        seats.setPlayerInfo(seatNumber, playerName, playerId);
    }

    /**
     *
     */
    public void takeSnapshot() {
        playArea.takeSnapshot();
        seats.getOwnerPlayerHand().takeSnapshot();
        clearMessage();
    }

    public void init_snapstate() {

        playArea.init_history();
        seats.getOwnerPlayerHand().init_snapshots();
    }

    /**
     * switches turn from current player to next player
     *
     * @param seatNumber
     */
    public void switchTurn(int seatNumber) {
        if (gameMode == constants.GameMode.PROACTIVE) {
            // All players are active in turnless mode.
            for (int i = 1; i <= playerCount; i++) {
                seats.getPlayer(i).setActive(true);
            }
            setMessage("Everybody can play, ther's no turn.");
        } else {
            for (int i = 1; i <= playerCount; i++) {
                seats.getPlayer(i).setActive(i == seatNumber);
            }

            if (seatNumber == getOwnerSeat()) {
                setMessage("It's your turn. Click on the deck when you're done.");
            } else {
                setMessage("Please wait for your turn.");
            }
        }

        if (!revertButton.isVisible()) {
            revertButton.setVisible(true);
            fillDeck();
        }
    }

    /**
     * resets the move
     */
    private void rollbackMove() {
        playArea.rollbackMoves();
        getHand().rollbackMoves();
    }

    /**
     * gets the player by seatNumber then removes cards from the player's CardSet
     *
     * @param seatNumber
     * @param playedCards
     */
    public void removeCardsFrom(int seatNumber, CardSet playedCards) {
        Player player = seats.getPlayer(seatNumber);

        player.getHand().removeCards(playedCards.totalCount());
    }

    public void setGameMode(constants.GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public constants.GameMode getGameMode() {
        return gameMode;
    }
}
