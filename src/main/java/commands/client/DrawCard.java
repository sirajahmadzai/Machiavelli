package commands.client;


import server.models.cards.Card;

/**
 * When a player passes without playing any card server draws a card to his hand and inform other players using this command.
 */
public class DrawCard extends ClientCommand {
    /**
     * PRIVATES
     */
    private int seatNumber;
    private String card;

    /**
     * CONSTRUCTOR
     */
    public DrawCard() {
        super(CommandNames.DRAW_CARD);
    }

    /**
     * CONSTRUCTOR
     *
     * @param seatNumber the seat number of the player who draws a card.
     * @param card       the card drawn from the deck.
     */
    public DrawCard(int seatNumber, String card) {
        this();
        this.seatNumber = seatNumber;
        this.card = card;
        addParameter(seatNumber);
        addParameter(card);
    }

    /**
     * @param commandStr
     */
    @Override
    public void doParse(String commandStr) {
        seatNumber = scanner.nextInt();
        card = scanner.next();
    }

    /**
     *
     */
    @Override
    protected void doExecute() {
        try {
            manager.drawCard(seatNumber, Card.fromString(card));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
