package commands.client;

import server.models.CardSet;

public class DealHands extends ClientCommand {

    private int seatNumber;
    private CardSet hand;

    public DealHands() {
        super(CommandNames.DEAL_HANDS);
    }

    public DealHands(int seatNumber, CardSet hand) {
        this();
        this.seatNumber = seatNumber;
        this.hand = hand;

        this.addParameter(seatNumber);
        this.addParameter(hand);
    }

    public DealHands(String cmdString) {
        super(cmdString);
    }

    @Override
    public void doParse(String commandStr) {
        seatNumber = scanner.nextInt();
        hand = new CardSet(scanner.next());
    }

    public void doExecute() {
        manager.dealHand(seatNumber, hand);
    }
}
