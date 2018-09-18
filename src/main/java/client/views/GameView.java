package client.views;

import client.GameSeats;
import client.Player;
import client.ViewHelper;
import commands.Command;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import server.models.CardSet;
import server.models.cards.Card;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class GameView extends View {
    /*************************************************************
     ************************FXML INJECTIONS**********************
     *************************************************************/
    @FXML // fx:id="board"
    private BorderPane board; // Value injected by FXMLLoader

    @FXML
    private HBox playAreaTop;

    @FXML
    private FlowPane setsArea;


    /*************************************************************
     ************************END OF FXML INJECTIONS***************
     *************************************************************/

    /*******************************************************************
     * *************************PRIVATE STATIC FINALS*******************
     ******************************************************************/
    private static final int CARD_PREF_WIDTH = 120;
    private static final int CARD_PREF_HEIGHT = 140;


    /*************************************************************
     *********************PRIVATE MAPS****************************
     *************************************************************/

    private Map<Card, ObservableList<Node>> sets;

    private Map<CardSet, VBox> setViews;

    /*************************************************************
     *****************************END OF MAPS*********************
     *************************************************************/

    /*************************************************************
     *****************************PRIVATES************************
     *************************************************************/
    private ImageView deckImageView;

    private Label messageBox;

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
        initPlayAreaTop();
    }

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {

    }

    /******************************************************
     ********************GUI MAPPING***********************
     *****************************************************/

    /**
     * for testing purposes
     * @param ae
     */
    @FXML
    public void onCommandEntered(ActionEvent ae) {
        String cmdText = ((TextField) ae.getSource()).getText();
        Command cmd = new Command(cmdText);
        getMainApp().sendCommandToServer(cmd);
    }

    private void initPlayAreaTop() {
        deckImageView = ViewHelper.createImageView(Card.BACK_OF_CARD_IMAGE);
        playAreaTop.getChildren().add(deckImageView);
        deckImageView.setVisible(false);

        messageBox = new Label("Click on the deck to get started!");
        messageBox.setWrapText(true);
        messageBox.setId("messageBox");

        playAreaTop.getChildren().add(messageBox);
    }


    /*******************************
     * GUI MAPPING ENDS************
     *******************************/

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


    /***********************************************
     *************************HANDS******************
     ************************************************/
    public void addCardToHand(int seatNumber, Card card, EventHandler<MouseEvent> mouseEvent) {
        Player player = seats.getPlayer(seatNumber);
        player.addCardToHand(card);
    }

    /**
     * Deal 1 card to each opponent
     */
    public void dealHands() {
        // TODO: maybe an animation?
        for (Player player : seats.getOpponents()) {
            player.addHiddenCardToHand();
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

    /**
     * adds a set to the setsArea FlowPane and setViews map
     *
     * @param set
     */
    public void addSet(CardSet set) {
        VBox setView = new VBox(-20);
        setView.setPadding(new Insets(10, 10, 10, 10));
        for (Card card : set.getCards()) {
            ImageView imageView = ViewHelper.createImageView(card.getImgUrl());
            setView.getChildren().add(imageView);
        }
        setsArea.getChildren().add(setView);
        setViews.put(set, setView);
    }

    /**
     * removes set from setsArea
     *
     * @param set
     */
    public void removeSet(CardSet set) {
        setsArea.getChildren().remove(setViews.remove(set));
    }


    /********************************************************
     ************* HELPERS********************
     *********************************************************************************/

    /**
     * to add a card to an ObservableList
     *
     * @param list
     * @param imgUrl
     */
    private void addCardToObservableList(ObservableList<Node> list, Card card, String imgUrl) {
        list.add(ViewHelper.createCard(card));
    }

    /**
     * to remove a Node from a specific ObservableList
     *
     * @param list
     * @param cardId
     */
    private void removeNodeFromObservableList(ObservableList<Node> list, int cardId) {
        Node nodeToRemove = null;

        for (Node node : list) {
            if (node instanceof ImageView) {
                if (node.getId().equals(String.valueOf(cardId))) {
                    nodeToRemove = node;
                    break; //doesn't cause problem
                }
            }
        }
        list.remove(nodeToRemove);
    }

    /**
     * Set the owner player id and seat number for this client.
     * @param ownerPlayerId
     * @param seatNumber
     */
    public void setOwnerPlayer(int ownerPlayerId, int seatNumber) {
        seats.setOwnerSeat(seatNumber, ownerPlayerId);
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
}
