package client.views;

import client.PlayerPosition;
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
import javafx.scene.layout.*;
import server.models.CardSet;
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

    private ArrayList<client.Player> clientPlayers = new ArrayList<>();

    /*************************************************************
     *****************************END OF MAPS*********************
     *************************************************************/

    /*************************************************************
     *****************************PRIVATES************************
     *************************************************************/
    private String backOfCardPath;

    private ImageView deckImageView;

    private Label messageBox;

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

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
        this.clientPlayers = new ArrayList<>();

        PlayerPosition[] positions = {PlayerPosition.BOTTOM, PlayerPosition.LEFT, PlayerPosition.TOP, PlayerPosition.RIGHT};

        // Put 2nd player at top if there are only 2 players.
        if(playerCount==2){
            positions[1] =PlayerPosition.TOP;
        }

        for (int i = 0; i < playerCount; i++) {
            client.Player player = new client.Player(i);

            player.setPlayerId(i);  //TODO: Get this id from server!
            player.setPosition(positions[i]);

            clientPlayers.add(player);

            switch (player.getPosition()){
                case TOP:
                    board.setTop(player);
                    break;
                case BOTTOM:
                    board.setBottom(player);
                    break;
                case LEFT:
                    board.setLeft(player);
                    break;
                case RIGHT:
                    board.setRight(player);
                    break;
            }
        }
    }

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
//        bottomPlayer.setSpacing(-20);
//        leftPlayer.setSpacing(-50);
//        topPlayer.setSpacing(-20);
//        rightPlayer.setSpacing(-50);

//        leftPlayer.setRotate(90);
//        topPlayer.setRotate(90);
    }

    /******************************************************
     ********************GUI MAPPING***********************
     *****************************************************/

    @FXML
    public void onCommandEntered(ActionEvent ae) {
        String cmdText = ((TextField) ae.getSource()).getText();
        Command cmd = new Command(cmdText);
        getMainApp().sendCommandToServer(cmd);
    }

    /**
     * @param players
     */
    private void init(ArrayList<Player> players) {
//        setViews = new HashMap<>();
//        playerHands = new HashMap<>();

//        initPlayerMaps(players);
    }

    private void initPlayAreaTop() {
        deckImageView = createImageView(backOfCardPath);
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

    private client.Player getPlayerById(int id) {
        for (client.Player player : clientPlayers) {
            if (player.getPlayerId()== id)
                return player;
        }

//        TODO: Write better code.
        return null;
    }

    public void addCardToHand(String playerId, Card card, EventHandler<MouseEvent> mouseEvent) {
        client.Player player = getPlayerById(Integer.parseInt(playerId));
        player.addCardToHand(card);
    }

    public void dealHands() {
//        Distribute back of the cards to other players.
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
            ImageView imageView = createImageView(card.getImgUrl());
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
        ImageView imv = createImageView(card.getImgUrl());
        imv.setUserData(card);
        imv.setId(String.valueOf(card.getId()));
        return imv;
    }

    /**
     * creates an ImageView
     *
     * @param imgUrl
     * @return
     */
    private ImageView createImageView(String imgUrl) {
        ImageView imageView = new ImageView(imgUrl);
        Image type = getImage(imgUrl);
        imageView.setImage(type);
        imageView.setFitWidth(CARD_PREF_WIDTH);
        imageView.setFitHeight(CARD_PREF_HEIGHT);

        return imageView;
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

}
