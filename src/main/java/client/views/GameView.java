package client.views;

import client.CardEvent;
import client.GameSeats;
import client.ViewHelper;
import client.views.components.CardSetView;
import client.views.components.CardView;
import client.views.components.PlayArea;
import client.views.components.Player;
import commands.Command;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import server.models.CardSet;
import server.models.cards.Card;
import server.models.cards.HiddenCard;

import java.util.Map;

public class GameView extends View implements EventHandler<CardEvent>{
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
    private PlayArea playArea;


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

    private Map<CardSet, HBox> setViews;

    /*************************************************************
     *****************************END OF MAPS*********************
     *************************************************************/

    /*************************************************************
     *****************************PRIVATES************************
     *************************************************************/

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
        messageBox.setText("Click on the deck to get started!");
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

    /**
     * adds a set to the setsArea FlowPane and setViews map
     *
     * @param set
     */
    public void addSet(CardSet set) {
        CardSetView setView = new CardSetView(set);

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
        list.add(new CardView(card));
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

    public void setPlayAreaActive(boolean active){
        playArea.setActive(active);
    }

    @Override
    public void handle(CardEvent event) {
        String eventType = event.getEventType().getName();

        switch (eventType){
            case "CARD_SELECTED_AT_HAND":
                playArea.setActive(event.getCardView() != null);
                break;
            case "CARD_SELECTED_AT_TABLE":
                break;
        }
    }
}
