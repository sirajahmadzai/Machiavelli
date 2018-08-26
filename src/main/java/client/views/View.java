package client.views;

import client.App;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;


public abstract class View {

    /*******************************************************************
     * *************************PRIVATE STATIC FINALS*******************
     ******************************************************************/
    private static final String HOME_BACKGROUND_FILENAME = "game.jpg";
    private static final String GREY_BTN_STYLE = "-fx-base: orange; -fx-text-fill: black;";

    private static final int BTN_MAX_WIDTH = 120;
    private static final int BTN_MAX_HEIGHT = 60;

    /*******************************************************************
     * *************************PRIVATES*******************
     ******************************************************************/
    private App mainApp;


    /**
     * get the background
     *
     * @param fileName
     * @return
     */
    public Background getBackground(String fileName) {
        try {

            ClassLoader classLoader = getClass().getClassLoader();
            URL url = classLoader.getResource(fileName);

            BackgroundSize bSize = new BackgroundSize(100, 100, true, true, true, true);
            if (url != null) {
                File file = new File(url.getFile());
                Image image = new Image(new FileInputStream(file));
                return new Background(new BackgroundImage(image,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER,
                        bSize));
            }
        } catch (Exception e) {
            System.out.println(String.format("Error loading background image %s, so proceeding without background image", fileName));
        }
        return null;
    }

    /**
     * getter for mainApp
     *
     * @return
     */
    public App getMainApp() {
        return mainApp;
    }


    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(App mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * @param text
     * @return
     */
    public Button createBtn(String text) {
        Button btn = new Button(text);
        btn.setStyle(GREY_BTN_STYLE);
        btn.setMinWidth(BTN_MAX_WIDTH);
        btn.setMinHeight(BTN_MAX_HEIGHT);
        return btn;
    }

    /**
     * @param spacing
     * @param padding
     * @return
     */
    public VBox createLayout(int spacing, int padding) {
        VBox layout = new VBox(spacing);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(padding));

        Background homeBackground = getBackground(HOME_BACKGROUND_FILENAME);
        if (null != homeBackground) {

            layout.setBackground(homeBackground);
        }

        return layout;
    }
}
