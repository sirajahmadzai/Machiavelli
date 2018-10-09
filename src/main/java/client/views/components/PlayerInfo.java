package client.views.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

/**
 * Simple class to display user infoText and picture.
 */
public class PlayerInfo extends HBox{
    private Label infoLabel;
    private Circle statusCircle;

    public PlayerInfo(String name){
        infoLabel = new Label(name);
        statusCircle = new Circle();
        statusCircle.setRadius(5);

        this.setBackground(new Background(new BackgroundFill(Color.color(0.8,1,0.8,0.6), new CornerRadii(5),null)));
        this.setAlignment(Pos.CENTER);
        this.setMaxWidth(200);
        this.setSpacing(10);

        getChildren().addAll(statusCircle, infoLabel);
        setActive(false);
    }

    public void setInfoText(String infoText) {
        infoLabel.setText(infoText);
    }

    public void setActive(boolean active){
        Paint fill = active ? Color.GREEN : Color.GRAY;
        statusCircle.setFill(fill);
    }
}
