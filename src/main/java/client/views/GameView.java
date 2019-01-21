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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import server.models.CardSet;
import server.models.Machiavelli;
import server.models.cards.Card;
import server.models.cards.HiddenCard;

import java.net.URL;

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

    @FXML
    public void onRevertClicked(ActionEvent ae) {
        resetMove();
    }

    /*************************************************************
     ************************END OF FXML INJECTIONS***************
     *************************************************************/

    /**
     * PRIVATE STATICS
     */
    // instance of GameView
    private static GameView ourInstance = new GameView();

    private static Machiavelli machiavelli = Machiavelli.getInstance();


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
     * Number of players the game set up for.
     *
     * @return
     */
    public int getPlayerCount() {
        return seats.getTotalPlayers().size();
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
        this.seats = new GameSeats(board, playerCount);
        this.playArea = new PlayArea(setsArea);

        URL url = getClass().getClassLoader().getResource(Card.BACK_OF_CARD_IMAGE);
        Image image = new Image(url.toString());

//        deckImageView.setImage(ViewHelper.getImage(Card.BACK_OF_CARD_IMAGE));
        deckImageView.setImage(image);
        deckImageView.setVisible(false);
        deckImageView.setOnMouseClicked(event -> ClientManager.getInstance().endTurn(event));
        revertButton.setVisible(false);
        setMessage("Waiting for other players to join.");
    }


    public int getTotalPlayers() {

        return seats.getOpponents().size();

    }

    public void removePlayer() {

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
     * empties deckOfCards
     */
    public void emptyDeck() {
        deckImageView.setVisible(false);
    }

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
     * Add new player to specified seat.
     *
     * @param playerName
     * @param playerId
     * @param seatNumber
     */
    public void removeSeat(String playerName, int playerId, int seatNumber) {

        //seats.setPlayerInfo(seatNumber, playerName, playerId);
    }


    /**
     *
     */
    public void takeSnapshot() {
        playArea.takeSnapshot();
        seats.getOwnerPlayerHand().takeSnapshot();
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


        for (int i = 1; i <= playerCount; i++) {
            seats.getPlayer(i).setActive(i == seatNumber);
        }
        if (!revertButton.isVisible()) {
            revertButton.setVisible(true);
            fillDeck();
        }


        if (seatNumber == getOwnerSeat()) {
            setMessage("It's your turn. Click on the deck when you're done.");
        } else {
            setMessage("Please wait for your turn.");
        }
        if (machiavelli != null)

            if (machiavelli.getTable() != null)
                if (machiavelli.getTable().getDeck() != null)
                    if (machiavelli.getTable().getDeck().size() <= 0) {
                        deckImageView.setImage(ViewHelper.getImage(Card.NO_CARD_IMAGE));
                    } else {
                        deckImageView.setImage(ViewHelper.getImage(Card.BACK_OF_CARD_IMAGE));
                    }
    }

    /**
     * resets the move
     */
    private void resetMove() {
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
}
