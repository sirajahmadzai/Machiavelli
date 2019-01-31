package interfaces;

import client.views.View;
import commands.Command;
import javafx.stage.Stage;

public interface appInterface {

    public void initRootLayout();

    public View getActiveView();

    public Stage getPrimaryStage();

    public void sendCommandToServer(Command cmd);

}
