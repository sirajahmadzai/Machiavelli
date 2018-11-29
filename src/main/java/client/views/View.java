package client.views;

import client.App;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import utils.constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.net.URL;

import static utils.constants.HOME_BACKGROUND_FILENAME;
import static utils.constants.GREY_BTN_STYLE;


public abstract class View {

    /*******************
     ***** PROTECTS ****
     *******************/
    protected String fxml;
    protected Parent root;


    /*****************************
     ****PRIVATE STATIC FINALS****
     *****************************/


    private static final int BTN_MAX_WIDTH = 120;
    private static final int BTN_MAX_HEIGHT = 60;

    /********************
     ****** PRIVATES *****
     ********************/
    private App mainApp;

    /**
     * loads the fxml
     *
     * @throws Exception
     */
    protected void loadFxml() throws Exception {
        if (fxml == null || fxml.isEmpty()) {
            throw new InvalidObjectException("The fxml field should be set before calling loadFxml method.");
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setController(this);
        loader.setLocation(View.class.getResource(fxml));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.root = loader.getRoot();
    }

    /**
     * GETTERS
     */
    /**
     * gets the background
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
     * gets this view's root
     *
     * @return
     */
    public Parent getRoot() {

        return root;
    }

    /**
     * SETTERS
     */
    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(App mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * creates a button with the given text and the default style, height and width
     *
     * @param text
     * @return Button
     */
    public Button createBtn(String text) {
        Button btn = new Button(text);
        btn.setStyle(GREY_BTN_STYLE);
        btn.setMinWidth(BTN_MAX_WIDTH);
        btn.setMinHeight(BTN_MAX_HEIGHT);
        return btn;
    }

    /**
     * creates a VBox with the given spacing, padding and the default alignment and homeBackground
     *
     * @param spacing
     * @param padding
     * @return VBox
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
