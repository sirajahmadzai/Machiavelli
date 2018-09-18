package client;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Simple class to display user name and picture.
 */
public class PlayerInfo extends VBox{
    private Label nameLabel;
    private ImageView playerImage;

    public PlayerInfo(String name){
        nameLabel = new Label(name);
        playerImage =  ViewHelper.createImageView("images/gambler.png");
        this.setBackground(new Background(new BackgroundFill(Color.color(0.8,1,0.8,1),null,null)));
        this.setAlignment(Pos.CENTER);

        getChildren().add(nameLabel);
        getChildren().add(playerImage);
    }

    public void setName(String name) {
        nameLabel.setText(name);
    }
}
