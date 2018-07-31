import junit.framework.TestCase;
import models.Machiavelli;
import models.Player;
import models.Table;
import models.cards.Basic;
import models.cards.Suit;

import java.util.ArrayList;

public class MachiavelliTest extends TestCase {

    public void testDrawCardFromDeck(){

        Machiavelli game = new Machiavelli(2);

        Player p1 = new Player(0, "siraj");
        Player p2 = new Player(1, "steph");

        ArrayList<Player> players = new ArrayList<>();

        players.add(p1);
        players.add(p2);

        Table table = new Table();
        Basic card1 = new Basic(Suit.CLUBS, 10, "Jack", 10);
        Basic card2 = new Basic(Suit.CLUBS, 10, "Queen", 10);

        table.getDeck().add(card1);
        table.getDeck().add(card2);

        assertEquals(card1, game.drawCardFromDeck());
        assertEquals(card2, game.drawCardFromDeck());
        
    }

    public void testPlayCard(){

    }

    public void testPlayCardFromPlayArea(){

    }

    public void testMergeSet(){

    }

    public void testSplitSet(){

    }

    public void testSplitSetRemove(){

    }

    public void testPrependCard(){

    }

    public void testAppendCard(){

    }

    public void testVerifyTable(){

    }

    public void testDealHands(){

    }

    public void testPickPlayerTurn(){
        Player p1 = new Player(0, "Siraj");
        Player p2 = new Player(1, "Steph");
        Player p3 = new Player(2, "player3");
        Player p4 = new Player(3, "player4");
        ArrayList<Player> players = new ArrayList<>();

        players.add(0, p1);
        players.add(1, p2);
        players.add(2, p3);
        players.add(3, p4);

        Machiavelli game = new Machiavelli(4);
        assertEquals(p1, game.getRandomPlayer());


    }

}
