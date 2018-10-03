package commands.client;

import server.models.CardSet;

public class DealHands extends ClientCommand {

    private final int seatNumber;
    private final CardSet hand;

    public DealHands(int seatNumber, CardSet hand) {
        super(CommandNames.DEAL_HANDS);
        this.seatNumber = seatNumber;
        this.hand = hand;

        this.addParameter(seatNumber);
        this.addParameter(hand);
    }

    public DealHands(String cmdString) {
        parseName(cmdString);

        hand = new CardSet(scanner.next());
        seatNumber = scanner.nextInt();
    }

    public void doExecute() {
        manager.dealHand(seatNumber, hand);
    }
}
