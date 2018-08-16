package views;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class GameView {


    /*************************************************************
     ************************FXML INJECTIONS**********************
     *************************************************************/

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="board"
    private BorderPane board; // Value injected by FXMLLoader

    @FXML // fx:id="bottomPlayer"
    private HBox bottomPlayer; // Value injected by FXMLLoader

    @FXML // fx:id="leftPlayer"
    private VBox leftPlayer; // Value injected by FXMLLoader

    @FXML // fx:id="rightPlayer"
    private VBox rightPlayer; // Value injected by FXMLLoader

    @FXML // fx:id="topPlayer"
    private HBox topPlayer; // Value injected by FXMLLoader

    @FXML // fx:id="deck"
    private HBox deck; // Value injected by FXMLLoader

    private HBox messageBox;

    /*************************************************************
     ************************END OF FXML INJECTIONS***************
     *************************************************************/

    /*******************************************************************
     * *************************PRIVATE STATIC FINALS*******************
     ******************************************************************/
    private static final int CARD_PREF_WIDTH = 100;
    private static final int CARD_MIN_HEIGHT = 120;

    /*************************************************************
     *****************************PRIVATE MAPS****************************
     *************************************************************/

    private Map<Integer, ObservableList<Node>> playerHands;


    private Map<Integer, ObservableList<Node>> sets;

    private Map<Integer, PlayerPosition> playerPositions;

    /*************************************************************
     *****************************END OF MAPS*********************
     *************************************************************/

    /*************************************************************
     *****************************OBSERVABLE LISTS*****************
     *************************************************************/

    private ObservableList<Node> deckOfCards;


    /*************************************************************
     *********************END OF OBSERVABLE LISTS*****************
     *************************************************************/


    @FXML
    // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert board != null : "fx:id=\"board\" was not injected: check your FXML file 'GameView.fxml'.";
        assert bottomPlayer != null : "fx:id=\"bottomPlayer\" was not injected: check your FXML file 'GameView.fxml'.";
        assert leftPlayer != null : "fx:id=\"leftPlayer\" was not injected: check your FXML file 'GameView.fxml'.";
        assert rightPlayer != null : "fx:id=\"rightPlayer\" was not injected: check your FXML file 'GameView.fxml'.";
        assert topPlayer != null : "fx:id=\"topPlayer\" was not injected: check your FXML file 'GameView.fxml'.";
        assert deck != null : "fx:id=\"deck\" was not injected: check your FXML file 'GameView.fxml'.";

    }

    /******************************************************
     ********************GUI MAPPING***********************
     *****************************************************/

    /**
     * receives player ids and maps out game areas to each player
     *
     * @param playerIDs
     */
    private void initPlayerMaps(ArrayList<Integer> playerIDs) {

        //bottom for player1 and top for player2 in a 2 player game
        if (playerIDs.size() == 2) {
            playerHands.put(playerIDs.get(0), bottomPlayer.getChildren());

            playerHands.put(playerIDs.get(1), topPlayer.getChildren());
        }

        //CLOCKWISE
        if (playerIDs.size() >= 3) {
            playerHands.put(playerIDs.get(0), bottomPlayer.getChildren());
            playerHands.put(playerIDs.get(1), leftPlayer.getChildren());
            playerHands.put(playerIDs.get(2), topPlayer.getChildren());

        }
        if (playerIDs.size() == 4) {
            playerHands.put(playerIDs.get(3), rightPlayer.getChildren());
        }
    }

    /**
     * @param playerIDs
     */
    private void init(ArrayList<Integer> playerIDs) {
        playerHands = new HashMap<>();

        initPlayerMaps(playerIDs);
//        initCentre();
//        initMessage();
    }

    /**
     * set up 2 player game
     *
     * @param bottomPlayerID
     * @param topPlayerID
     */
    public void setup(int bottomPlayerID, int topPlayerID) {
        ArrayList<Integer> playerIDs = new ArrayList<>();

        playerIDs.add(bottomPlayerID);
        playerIDs.add(topPlayerID);

        init(playerIDs);

        playerPositions = new HashMap<>();
        playerPositions.put(bottomPlayerID, PlayerPosition.BOTTOM);
        playerPositions.put(topPlayerID, PlayerPosition.TOP);

    }

    /**
     * setup 3 player game
     *
     * @param bottomPlayerID
     * @param leftPlayerID
     * @param topPlayerID
     */
    public void setup(int bottomPlayerID, int leftPlayerID, int topPlayerID) {
        ArrayList<Integer> playerIDs = new ArrayList<>();

        playerIDs.add(bottomPlayerID);
        playerIDs.add(leftPlayerID);
        playerIDs.add(topPlayerID);

        init(playerIDs);

        playerPositions = new HashMap<>();
        playerPositions.put(bottomPlayerID, PlayerPosition.BOTTOM);
        playerPositions.put(leftPlayerID, PlayerPosition.LEFT);
        playerPositions.put(topPlayerID, PlayerPosition.TOP);

    }

    /**
     * set up 4 player game
     *
     * @param bottomPlayerID
     * @param leftPlayerID
     * @param topPlayerID
     * @param rightPlayerID
     */
    public void setup(int bottomPlayerID, int leftPlayerID, int topPlayerID, int rightPlayerID) {
        ArrayList<Integer> playerIDs = new ArrayList<>();
        playerIDs.add(bottomPlayerID);
        playerIDs.add(leftPlayerID);
        playerIDs.add(topPlayerID);
        playerIDs.add(rightPlayerID);

        init(playerIDs);

        playerPositions = new HashMap<>();
        playerPositions.put(bottomPlayerID, PlayerPosition.BOTTOM);
        playerPositions.put(leftPlayerID, PlayerPosition.LEFT);
        playerPositions.put(topPlayerID, PlayerPosition.TOP);
        playerPositions.put(rightPlayerID, PlayerPosition.RIGHT);

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
    public void setMessageBox(String message) {
        ((Label) messageBox.getChildren().get(0)).setText(message);
    }


    /***********************************************
     *************************HANDS******************
     ************************************************/
    /**
     * to add a card to a specific hand
     *
     * @param playerId
     * @param cardId
     * @param imgUrl
     */
    public void addCardToHand(int playerId, int currentPlayerId, int cardId, String imgUrl, EventHandler<MouseEvent> mouseEvent) {
        if (playerId == currentPlayerId) { //if current player show
            addCardToMap(playerHands, playerId, cardId, imgUrl, mouseEvent);
        } else { //if not current player hide
            addCardToMap(playerHands, playerId, cardId, "back/adventure_back.jpg", mouseEvent);
        }
    }

    /**
     * to remove a card from a specific hand
     *
     * @param playerId
     * @param cardId
     */
    public void removeCardFromHand(int playerId, int cardId) {
        removeNodeFromObservableList(playerHands.get(playerId), cardId);
    }


    /***********************************************
     **************END OF HAND METHODS**************
     ************************************************/


    /**
     * @param playerId
     * @param cardId
     * @param imgUrl
     */
    public void changeCardImage(int playerId, int cardId, String imgUrl) {
        ImageView card = createCard(cardId, imgUrl);

        for (Node node : playerHands.get(playerId)) {
            if (Integer.parseInt(node.getId()) == cardId) {
                node = card;
            }
        }
    }

    /***********************************************
     ******************DECKS*************************
     ************************************************/
    /**
     * empties deckOfCards
     */
    public void emptyDeckOfCards() {
        deckOfCards.removeAll();
    }

    /*********************************************************************************
     ************* HELPERS********************
     *********************************************************************************/

    private void initMessage() {
        Label text = new Label("Click on the story deck to get started!");
        text.setWrapText(true);

        messageBox.getChildren().add(text);
        messageBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.8);");
        messageBox.setPadding(new Insets(20));

    }

    /**
     * init centre of the game view
     * specifically placement of story deck and adventure deck
     */
    private void initCentre() {
        deckOfCards = deck.getChildren();

    }

    /**
     * @param resource
     * @return Image
     */
    private Image getImage(String resource) {
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource(resource);
        File file = null;
        if (url != null) {
            file = new File(url.getFile());
        }
        Image thisImage = null;
        try {
            if (file != null) {
                thisImage = new Image(new FileInputStream(file));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return thisImage;
    }


    /**
     * to create a card
     *
     * @param path
     * @return ImageView
     */
    private ImageView createCard(int cardId, String path) {
        path = path.replace(" ", "");
        ImageView imv = new ImageView(path);
        Image type = getImage(path);
        imv.setImage(type);
        imv.setFitWidth(CARD_PREF_WIDTH);
        imv.setFitHeight(CARD_MIN_HEIGHT);
        imv.setUserData(cardId);
        imv.setId(String.valueOf(cardId));
        return imv;
    }

    /**
     * to add a card to an ObservableList
     *
     * @param list
     * @param cardId
     * @param imgUrl
     */
    private void addCardToObservableList(ObservableList<Node> list, int cardId, String imgUrl) {

        list.add(createCard(cardId, imgUrl));

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

    private void removeSelectedNodeFromObservableList(ObservableList<Node> list, int cardId) {
        Node nodeToRemove = null;

        for (Node node : list) {
            if (node.getId().equals(String.valueOf(cardId))) {
                if (node.getEffect() == null) {
                    continue;
                }
                nodeToRemove = node;
                break; //doesn't cause problem
            }
        }

        if (nodeToRemove == null) {
            removeNodeFromObservableList(list, cardId);
        } else {
            list.remove(nodeToRemove);
        }
    }


    /**
     * @param map
     * @param playerId
     * @param cardId
     * @param imgUrl
     */
    private void addCardToMap(Map<Integer, ObservableList<Node>> map, int playerId, int cardId, String imgUrl, EventHandler<MouseEvent> mouseEvent) {
        //rotation is hard coded for now
        PlayerPosition pos = playerPositions.get(playerId);

        imgUrl = imgUrl.replace(" ", "");

        ImageView imv = createCard(cardId, imgUrl);
        if (pos == PlayerPosition.BOTTOM) {
            //DO NOTHING
        } else if (pos == PlayerPosition.LEFT) {
            imv.setRotate(90);
        } else if (pos == PlayerPosition.RIGHT) {
            imv.setRotate(270);
        } else {
            imv.setRotate(180);
        }
        imv.setOnMouseClicked(mouseEvent);

        map.get(playerId).add(imv);
    }


    private enum PlayerPosition {
        BOTTOM,
        LEFT,
        TOP,
        RIGHT
    }
}
