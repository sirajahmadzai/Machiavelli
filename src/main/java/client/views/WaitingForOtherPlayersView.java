package client.views;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class WaitingForOtherPlayersView extends View {

    /******************************
     **** PRIVATE STATIC FINALS ****
     *******************************/
    private static final int LAYOUT_SPACING = 20;
    private static final int LAYOUT_PADDING = 20;


    /*****************
     **** PRIVATES ****
     *****************/
    private VBox layout;

    //private static instance
    private static WaitingForOtherPlayersView ourInstance = new WaitingForOtherPlayersView();


    /**
     * CONSTRUCTOR
     */
    private WaitingForOtherPlayersView() {
        super();
        //create the layout with the default spacing and padding
        layout = createLayout(LAYOUT_SPACING, LAYOUT_PADDING);
        //set the background for the layout
        layout.setBackground(new Background(new BackgroundFill(Color.color(1, 1, 1, 0.5), null, null)));

        //create a label
        Label lblMsg = new Label("Waiting for other players to join");
        lblMsg.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
        lblMsg.setTextFill(Color.RED);
//        lblMsg.setPrefWidth(500);
//        lblMsg.setPrefHeight(100);

        //add lblMsg  to the layout
        layout.getChildren().add(lblMsg);
    }


    /**
     * GETTERS
     */
    /**
     * gets this classes's instance
     *
     * @return
     */
    public static WaitingForOtherPlayersView getInstance() {
        return ourInstance;
    }

    /**
     * gets the layout
     *
     * @return
     */
    public VBox getLayout() {
        return layout;
    }

    /**
     * gets the root
     *
     * @return
     */
    @Override
    public Parent getRoot() {
        return layout;
    }
}
