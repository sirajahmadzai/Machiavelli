package client.views;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class WaitingForOtherPlayersView extends View {

    /*******************************************************************
     * *************************PRIVATE STATIC FINALS*******************
     ******************************************************************/
    private static final int LAYOUT_SPACING = 20;
    private static final int LAYOUT_PADDING = 20;


    /*******************************************************************
     * *************************PRIVATES*******************
     ******************************************************************/
    private VBox layout;

    private static WaitingForOtherPlayersView ourInstance = new WaitingForOtherPlayersView();

    public static WaitingForOtherPlayersView getInstance() {
        return ourInstance;
    }

    
    private WaitingForOtherPlayersView() {
        super();
        layout = createLayout(LAYOUT_SPACING, LAYOUT_PADDING);
        layout.setBackground(new Background(new BackgroundFill(Color.color(1,1,1,0.5),null,null)));

        Label lblMsg = new Label("Waiting for other players to join");
        lblMsg.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
        lblMsg.setTextFill(Color.RED);
//        lblMsg.setPrefWidth(500);
//        lblMsg.setPrefHeight(100);
        layout.getChildren().add(lblMsg);
    }

    /**
     * @return
     */
    public VBox getLayout() {
        return layout;
    }

    @Override
    public Parent getRoot() {
        return layout;
    }
}
