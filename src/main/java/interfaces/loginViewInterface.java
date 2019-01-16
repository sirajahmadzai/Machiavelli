package interfaces;

import javafx.scene.control.Label;
import javafx.scene.effect.Effect;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public interface loginViewInterface {
    public VBox getLayout();

    public VBox createLayout(int spacing, int padding);

    public String getIp();

    public int getPort();

    public Label getLblMessage();

    public void setReflection(GridPane gridPane);

    public Effect dropShadowEffect();
}
