package client.views;

import client.ClientManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


public class StartOptionsView extends View {
    private static StartOptionsView ourInstance = new StartOptionsView();

    public static StartOptionsView getInstance() {
        return ourInstance;
    }

    private StartOptionsView() {
        super();
        fxml = "/fxml/StartOptions.fxml";

        try {
            loadFxml();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private TextField joinIp;
    @FXML
    private TextField joinPort;
    @FXML
    private TextField newGamePort;
    @FXML
    private TextField numOfPlayers;
    @FXML
    private TextField userName;
    @FXML
    private TextField adminUserName;
    @FXML
    private Text messageText;

    @FXML
    public void onNewGame(ActionEvent ae){
        int numberOfPlayers = Integer.parseInt(numOfPlayers.getText());
        int port = Integer.parseInt(newGamePort.getText());

        try {
            ClientManager.getInstance().startServer(port,numberOfPlayers,adminUserName.getText());
            messageText.setText("Started server at port "+ port);
        } catch (Exception e) {
            messageText.setText("Couldn't start server:"+ e.getMessage());
        }
    }

    @FXML
    public void onJoinGame(ActionEvent ae){
        int port = Integer.parseInt(joinPort.getText());
        String ip = joinIp.getText();
//        TODO: use the username
        String userNameText = userName.getText();

        ClientManager.getInstance().loginServer(ip,port,userNameText);
    }
}
