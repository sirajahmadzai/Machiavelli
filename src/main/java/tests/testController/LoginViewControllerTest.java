package tests.testController;

import client.App;
import client.views.LoginView;

public class LoginViewControllerTest {
    /**
     * PRIVATES
     */
    private App app;
    private LoginView loginView;
    private String ip;
    private int port;

    /**
     * CONSTRUCTOR
     *
     * @param app
     * @param loginView
     */
    public LoginViewControllerTest(App app, LoginView loginView) {
        this.app = app;
        this.loginView = loginView;


        loginView.addBtnLoginAction(e -> {
            String ip = loginView.getIp();
            int port = loginView.getPort();



            if (false) {

            } else {
                System.out.println("Test");
                loginView.getLblMessage().setText("Connection refused check IP and Port!");
            }
        });
    }
}
