package client.views;

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
import server.models.Player;
import server.models.cards.Card;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class GameView extends View {


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

    @FXML
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

    private Map<Player, ObservableList<Node>> playerHands;


    private Map<Card, ObservableList<Node>> sets;

    /**
     * PlayerPosition is an enum, Integer is the playerID
     */
    private Map<Player, PlayerPosition> playerPositions;

    /*************************************************************
     *****************************END OF MAPS*********************
     *************************************************************/

    @FXML
    // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        messageBox = new HBox();
    }

    /******************************************************
     ********************GUI MAPPING***********************
     *****************************************************/

    /**
     * receives player ids and maps out game areas to each player
     *
     * @param players
     */
    private void initPlayerMaps(ArrayList<Player> players) {

        //bottom for player1 and top for player2 in a 2 player game
        if (players.size() == 2) {
            playerHands.put(players.get(0), bottomPlayer.getChildren());

            playerHands.put(players.get(1), topPlayer.getChildren());
        }

        //CLOCKWISE
        if (players.size() >= 3) {
            playerHands.put(players.get(0), bottomPlayer.getChildren());
            playerHands.put(players.get(1), leftPlayer.getChildren());
            playerHands.put(players.get(2), topPlayer.getChildren());

        }
        if (players.size() == 4) {
            playerHands.put(players.get(3), rightPlayer.getChildren());
        }
    }

    /**
     * @param players
     */
    private void init(ArrayList<Player> players) {
        playerHands = new HashMap<>();

        initPlayerMaps(players);
        initMessageBox();
    }

    /**
     * set up 2 player game
     *
     * @param bottomPlayer
     * @param topPlayer
     */
    public void setup(Player bottomPlayer, Player topPlayer) {
        ArrayList<Player> players = new ArrayList<>();

        players.add(bottomPlayer);
        players.add(topPlayer);

        init(players);

        playerPositions = new HashMap<>();
        playerPositions.put(bottomPlayer, PlayerPosition.BOTTOM);
        playerPositions.put(topPlayer, PlayerPosition.TOP);

    }

    /**
     * setup 3 player game
     *
     * @param bottomPlayer
     * @param leftPlayer
     * @param topPlayer
     */
    public void setup(Player bottomPlayer, Player leftPlayer, Player topPlayer) {
        ArrayList<Player> players = new ArrayList<>();

        players.add(bottomPlayer);
        players.add(leftPlayer);
        players.add(topPlayer);

        init(players);

        playerPositions = new HashMap<>();
        playerPositions.put(bottomPlayer, PlayerPosition.BOTTOM);
        playerPositions.put(leftPlayer, PlayerPosition.LEFT);
        playerPositions.put(topPlayer, PlayerPosition.TOP);

    }

    /**
     * set up 4 player game
     *
     * @param bottomPlayer
     * @param leftPlayer
     * @param topPlayer
     * @param rightPlayer
     */
    public void setup(Player bottomPlayer, Player leftPlayer, Player topPlayer, Player rightPlayer) {
        ArrayList<Player> players = new ArrayList<>();

        players.add(bottomPlayer);
        players.add(leftPlayer);
        players.add(topPlayer);
        players.add(rightPlayer);

        init(players);

        playerPositions = new HashMap<>();
        playerPositions.put(bottomPlayer, PlayerPosition.BOTTOM);
        playerPositions.put(leftPlayer, PlayerPosition.LEFT);
        playerPositions.put(topPlayer, PlayerPosition.TOP);
        playerPositions.put(rightPlayer, PlayerPosition.RIGHT);

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

    //todo: remove currentPlayerId and instead check for ownership of the client and unuse mouseEvent

    /**
     * @param player
     * @param mouseEvent
     */
    public void addCardToHand(Player player, Card card, EventHandler<MouseEvent> mouseEvent) {
        if (true) { //if current player show
            addCardToMap(playerHands, player, card, mouseEvent);
        } else { //if not current player hide
            addCardToMap(playerHands, player, card, mouseEvent);
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

    /***********************************************
     ******************DECKS*************************
     ************************************************/
    /**
     * empties deckOfCards
     */
    public void emptyDeckOfCards() {
        deck.getChildren().removeAll();
    }

    /*********************************************************************************
     ************* HELPERS********************
     *********************************************************************************/

    private void initMessageBox() {
        Label text = new Label("Click on the story deck to get started!");
        text.setWrapText(true);

        messageBox.getChildren().add(text);
        messageBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.8);");
        messageBox.setPadding(new Insets(20));

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
     * @param card
     * @return
     */
    private ImageView createCard(Card card) {
        ImageView imv = new ImageView(card.getImgUrl());
        Image type = getImage(card.getImgUrl());
        imv.setImage(type);
        imv.setFitWidth(CARD_PREF_WIDTH);
        imv.setFitHeight(CARD_MIN_HEIGHT);
        imv.setUserData(card);
        imv.setId(String.valueOf(card.getId()));
        return imv;
    }

    /**
     * to add a card to an ObservableList
     *
     * @param list
     * @param imgUrl
     */
    private void addCardToObservableList(ObservableList<Node> list, Card card, String imgUrl) {

        list.add(createCard(card));

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

    //todo: what is this?
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
     * @param player
     * @param mouseEvent
     */
    private void addCardToMap(Map<Player, ObservableList<Node>> map, Player player, Card card, EventHandler<MouseEvent> mouseEvent) {
        //rotation is hard coded for now
        PlayerPosition pos = playerPositions.get(player);

        ImageView imv = createCard(card);
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

        map.get(player).add(imv);
    }


    private enum PlayerPosition {
        BOTTOM,
        LEFT,
        TOP,
        RIGHT
    }
}
