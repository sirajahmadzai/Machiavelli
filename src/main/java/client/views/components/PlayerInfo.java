package client.views.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Simple class to display user infoText and picture.
 */
public class PlayerInfo extends VBox{
    private Label infoLabel;

    public PlayerInfo(String name){
        infoLabel = new Label(name);
        this.setBackground(new Background(new BackgroundFill(Color.color(0.8,1,0.8,0.6), new CornerRadii(5),null)));
        this.setAlignment(Pos.CENTER);
        this.setMaxWidth(200);

        getChildren().add(infoLabel);
    }

    public void setInfoText(String infoText) {
        infoLabel.setText(infoText);
    }
}
