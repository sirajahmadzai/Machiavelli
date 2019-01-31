package client;

import client.views.StartOptionsView;
import client.views.View;
import commands.Command;
import interfaces.appInterface;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import utils.constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class App extends Application implements appInterface {

    /*******************************
     **** PRIVATE STATIC FINALS ****
     *******************************/

    private static final int MIN_WIDTH = 800;
    public static final int MIN_HEIGHT = (int) (MIN_WIDTH * 0.66);


    /**
     * main method
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Application.launch(App.class);
    }

    /***************
     ****PRIVATES****
     ***************/
    //stage displayed
    private Stage primaryStage;

    private Scene scene;

    //view in display
    private View activeView;

    private boolean isSizeChanged;

    @FXML
    private StackPane rootLayout;


    /***************
     ****PROTECTS****
     *****************/
    protected BufferedReader in;
    protected PrintWriter out;

    /**
     * Constructor
     *
     * @throws IOException
     */
    public App() throws IOException {
    }

    /**
     * @param primaryStage
     * @throws IOException
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setOnHidden(event -> {
            Platform.exit();
            System.exit(0);
        });
        initRootLayout();

        ClientManager.getInstance().setStage(primaryStage);
        ClientManager.getInstance().setApp(this);

        ClientManager.getInstance().showView(StartOptionsView.getInstance());
    }

    /**
     * sets up the root layout
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/fxml/RootLayout.fxml"));
            rootLayout = loader.load();

            // Show the scene containing the root layout.
            scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            primaryStage.setTitle(constants.TITLE);
            primaryStage.setMaximized(true);
            primaryStage.setMinWidth(MIN_WIDTH);
            primaryStage.setMinHeight(MIN_HEIGHT);
            // primaryStage.setResizable(false);
            primaryStage.show();
            isSizeChanged = false;
            primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
                // Do whatever you want
                if (isSizeChanged) {
                    primaryStage.show();
                    isSizeChanged = false;
                    return;
                }
                isSizeChanged = true;
                primaryStage.setHeight(((double) newVal) * 0.66);

            });
            primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
                // Do whatever you want
                if (isSizeChanged) {
                    primaryStage.show();
                    isSizeChanged = false;
                    return;
                }
                isSizeChanged = true;
                primaryStage.setWidth(((double) newVal) / 0.66);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**************************
     *********GETTERS***********
     **************************/
    /**
     * gets the activeView
     *
     * @return
     */
    public View getActiveView() {
        return activeView;
    }

    /**
     * Returns the main stage.
     *
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /***********************
     ****DISPLAY METHODS****
     ***********************/
    /**
     * displays the given view
     *
     * @param view
     */
    public synchronized void showView(View view) {
        scene.setRoot(view.getRoot());
        activeView = view;
        view.setMainApp(this);
    }

    public void sendCommandToServer(Command cmd) {
        this.out.println(cmd.serialize());
    }
}
