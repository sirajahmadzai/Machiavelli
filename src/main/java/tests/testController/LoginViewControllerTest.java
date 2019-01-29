package tests.testController;

import client.App;
import client.views.LoginView;

public class LoginViewControllerTest {
    private App app;
    private LoginView loginView;
    private String ip;
    private int port;

    public LoginViewControllerTest(App app, LoginView loginView) {
        this.app = app;
        this.loginView = loginView;


        loginView.addBtnLoginAction(e -> {
            String ip = loginView.getIp();
            int port = loginView.getPort();

            //todo: use this ip and port to connect, if it connects you're connected, if not show an error message.

            if (false) {

            } else

            {
                System.out.println("Test");
                loginView.getLblMessage().setText("Connection refused check IP and Port!");
            }
        });
    }
}
