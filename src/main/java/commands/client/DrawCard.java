package commands.client;


import com.sun.javaws.exceptions.InvalidArgumentException;
import server.models.cards.Card;

public class DrawCard extends ClientCommand {
    private int seatNumber;
    private String card;

    public DrawCard() {
        super(CommandNames.DRAW_CARD);
    }

    public DrawCard(int seatNumber, String card) {
        this();
        this.seatNumber = seatNumber;
        this.card = card;
        addParameter(seatNumber);
        addParameter(card);
    }

    @Override
    public void doParse(String commandStr) {
        seatNumber = scanner.nextInt();
        card = scanner.next();
    }

    @Override
    protected void doExecute() {
        try {
            manager.drawCard(seatNumber, Card.fromString(card));
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
    }
}
