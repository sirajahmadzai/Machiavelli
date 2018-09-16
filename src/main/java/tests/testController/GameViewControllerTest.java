package tests.testController;

import client.App;
import client.views.GameView;
import com.sun.javaws.exceptions.InvalidArgumentException;
import server.models.CardSet;
import server.models.Player;
import server.models.cards.*;

import java.util.ArrayList;

public class GameViewControllerTest {
    private App app;
    private GameView gameView;

    public GameViewControllerTest(App app, GameView gameView) {
//        this.app = app;
//        this.gameView = gameView;
//
//        Player bottomPlayer = new Player(1, "siraj");
//        Player topPlayer = new Player(2, "steph");
//        Player rightPlayer = new Player(3, "right");
//        Player leftPlayer = new Player(4, "left");
//
//        gameView.setup("images/b.png", bottomPlayer, leftPlayer, topPlayer, rightPlayer);
//
//        try {
//            Card card = new Basic(Suit.CLUBS, 6, 2);
//
//            Card card2 = new Basic(Suit.HEARTS, 5, 2);
//
//            Card card3 = new Joker(Suit.JOKER, 15, 3);
//
//            Card card4 = new Ace(Suit.HEARTS, 14, 4);
//
//            ArrayList<Card> cards = new ArrayList<>();
//            cards.add(card);
//
//            CardSet cardSet = new CardSet(cards);
//
//            gameView.addSet(cardSet);
//            gameView.addSet(cardSet);
////            gameView.addSet(cardSet);
////            gameView.addSet(cardSet);
////            gameView.addSet(cardSet);
////            gameView.addSet(cardSet);
////            gameView.addSet(cardSet);
////            gameView.addSet(cardSet);
////            gameView.addSet(cardSet);
////            gameView.addSet(cardSet);
////            gameView.addSet(cardSet);
////            gameView.addSet(cardSet);
////            gameView.addSet(cardSet);
////            gameView.addSet(cardSet);
////            gameView.addSet(cardSet);
////            gameView.addSet(cardSet);
////            gameView.addSet(cardSet);
////            gameView.addSet(cardSet);
////            gameView.addSet(cardSet);
////            gameView.addSet(cardSet);
//
//
//            gameView.addCardToHand(bottomPlayer, card, null);
//            gameView.addCardToHand(topPlayer, card2, null);
//            gameView.addCardToHand(leftPlayer, card3, null);
//            gameView.addCardToHand(rightPlayer, card4, null);
//
//            gameView.addCardToHand(bottomPlayer, card, null);
//            gameView.addCardToHand(topPlayer, card2, null);
//            gameView.addCardToHand(leftPlayer, card3, null);
//            gameView.addCardToHand(rightPlayer, card4, null);
//
//            gameView.addCardToHand(bottomPlayer, card, null);
//            gameView.addCardToHand(topPlayer, card2, null);
//            gameView.addCardToHand(leftPlayer, card3, null);
//            gameView.addCardToHand(rightPlayer, card4, null);
////
////            gameView.addCardToHand(bottomPlayer, card, null);
////            gameView.addCardToHand(topPlayer, card2, null);
////            gameView.addCardToHand(leftPlayer, card3, null);
////            gameView.addCardToHand(rightPlayer, card4, null);
////            gameView.addCardToHand(bottomPlayer, card, null);
////            gameView.addCardToHand(topPlayer, card2, null);
////            gameView.addCardToHand(leftPlayer, card3, null);
////            gameView.addCardToHand(rightPlayer, card4, null);
////            gameView.addCardToHand(bottomPlayer, card, null);
////            gameView.addCardToHand(topPlayer, card2, null);
////            gameView.addCardToHand(leftPlayer, card3, null);
////            gameView.addCardToHand(rightPlayer, card4, null);
////            gameView.addCardToHand(bottomPlayer, card, null);
////            gameView.addCardToHand(topPlayer, card2, null);
////            gameView.addCardToHand(leftPlayer, card3, null);
////            gameView.addCardToHand(rightPlayer, card4, null);
////            gameView.addCardToHand(bottomPlayer, card, null);
////            gameView.addCardToHand(topPlayer, card2, null);
////            gameView.addCardToHand(leftPlayer, card3, null);
////            gameView.addCardToHand(rightPlayer, card4, null);
////            gameView.addCardToHand(bottomPlayer, card, null);
////            gameView.addCardToHand(topPlayer, card2, null);
////            gameView.addCardToHand(leftPlayer, card3, null);
////            gameView.addCardToHand(rightPlayer, card4, null);
////            gameView.addCardToHand(bottomPlayer, card, null);
////            gameView.addCardToHand(topPlayer, card2, null);
////            gameView.addCardToHand(leftPlayer, card3, null);
////            gameView.addCardToHand(rightPlayer, card4, null);
////            gameView.addCardToHand(bottomPlayer, card, null);
////            gameView.addCardToHand(topPlayer, card2, null);
////            gameView.addCardToHand(leftPlayer, card3, null);
////            gameView.addCardToHand(rightPlayer, card4, null);
////            gameView.addCardToHand(bottomPlayer, card, null);
////            gameView.addCardToHand(topPlayer, card2, null);
////            gameView.addCardToHand(leftPlayer, card3, null);
////            gameView.addCardToHand(rightPlayer, card4, null);
//
//
//            gameView.fillDeck();
//
//
//        } catch (InvalidArgumentException e) {
//            e.printStackTrace();
//        }
    }
}
