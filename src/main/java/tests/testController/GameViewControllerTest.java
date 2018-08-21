package tests.testController;

import client.App;
import client.views.GameView;
import com.sun.javaws.exceptions.InvalidArgumentException;
import server.models.Player;
import server.models.cards.Basic;
import server.models.cards.Card;
import server.models.cards.Suit;

public class GameViewControllerTest {
    private App app;
    private GameView gameView;

    public GameViewControllerTest(App app, GameView gameView) {
        this.app = app;
        this.gameView = gameView;

        Player bottomPlayer = new Player(1, "siraj");
        Player topPlayer = new Player(2, "steph");

        gameView.setup(bottomPlayer, topPlayer);

        try {
            Card card = new Basic(Suit.CLUBS, 4, 1);

            gameView.addCardToHand(bottomPlayer, card, null);

        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }



    }
}
