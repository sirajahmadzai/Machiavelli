import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.cards.Card;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Main extends Application {


    public void start(Stage stage) {
        stage.setTitle("Machiavelli.java");

    }

    public static void main(String[] command_line_parameters) {
        launch(command_line_parameters);
    }
}

