package server.models;

import commands.Command;
import commands.NumericCommand;
import commands.StringCommand;
import commands.client.IntroducePlayer;
import commands.client.Welcome;
import commands.server.PlayerMove;
import server.ClientHandler;
import server.models.cards.Card;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Machiavelli {
    /********************************
     ******** PRIVATES **************
     ********************************/
    private Table table;
    private ArrayList<Player> players;
    private int numOfPlayers;
    private boolean gameStarted = false;
    private ArrayList<Seat> seats;

    private static Machiavelli ourInstance = new Machiavelli();
    private Seat currentSeat;

    public static Machiavelli getInstance() {
        return ourInstance;
    }

    /**
     * CONSTRUCTOR
     */
    private Machiavelli() {
    }

    /***************************************
     *************** GETTERS **************
     **************************************/
    /**
     * gets table
     *
     * @return
     */
    public Table getTable() {
        return table;
    }

    /**
     * @return
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * gets a random player from players ArrayList
     *
     * @return
     */
    public Player getRandomPlayer() {
        int currPlayerID = (new Random()).nextInt(players.size());

        return players.get(currPlayerID);
    }

    /***************************************
     *************** SETTERS **************
     **************************************/

    /**
     * @param table
     */
    public void setTable(Table table) {
        this.table = table;
    }

    public void initialize(int numOfPlayers) {
        players = new ArrayList<>();
        table = new Table();
        this.numOfPlayers = numOfPlayers;
        seats = new ArrayList<>();

        // Create seats.
        Seat prevSeat = null;
        for (int seatNumber = 1; seatNumber <= numOfPlayers; seatNumber++) {
            Seat seat = new Seat(seatNumber, false);

            if (prevSeat != null) {
                prevSeat.setNextSeat(seat);
            }

            seats.add(seat);
            prevSeat = seat;
        }
        // Create the circle here.
        prevSeat.setNextSeat(seats.get(0));
    }

    /**
     * @param players
     */
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }


    /***************************************
     *************** PUBLIC MODIFIERS **************
     **************************************/

    /**
     * @param player
     */
    public void drawCardFromDeck(Player player) throws EmptyDeckException {
        try {
            player.getHand().addCard(table.getDeck().pop());
        } catch (EmptyStackException e) {
            throw new EmptyDeckException();
        }
    }

    /**
     * removeCard card from the current player's hand then add it to the play area later on
     *
     * @param indexOfPlayer
     * @param indexOfCard
     * @return
     */
//    public Card removeCardFromHand(int indexOfPlayer, int indexOfCard) {
//        return players.get(indexOfPlayer).getHand().removeCard(indexOfCard);
//    }

    /**
     * merges two sets
     *
     * @param setToKeep
     * @param setToAppend
     */
    public void mergeSetAppend(CardSet setToKeep, CardSet setToAppend) {
        setToKeep.getCards().addAll(setToAppend.getCards());
        table.getCardSets().remove(setToAppend);
    }

    /**
     * @param cardSet
     * @param i
     * @return
     */
    public void splitSet(CardSet cardSet, int i) {

        ArrayList<Card> list1 = new ArrayList<>();
        ArrayList<Card> list2 = new ArrayList<>();
        CardSet result1 = new CardSet(list1);
        CardSet result2 = new CardSet(list2);

        AtomicInteger count = new AtomicInteger();
        for (Card next : cardSet.getCards()) {
            int index = count.getAndIncrement();
            if (index < i) {
                result1.getCards().add(next);
            } else {
                result2.getCards().add(next);
            }
        }
        ArrayList<CardSet> listOfCardSets = new ArrayList<>();
        listOfCardSets.add(result1);
        listOfCardSets.add(result2);
        table.setCardSets(listOfCardSets);
    }

    /**
     * @param cardSet
     * @param i
     */
    public void removeCard(CardSet cardSet, int i) {
        ArrayList<Card> list1 = new ArrayList<>();
        ArrayList<Card> list2 = new ArrayList<>();
        CardSet result1 = new CardSet(list1);
        CardSet result2 = new CardSet(list2);

        AtomicInteger count = new AtomicInteger();
        for (Card next : cardSet.getCards()) {
            int index = count.getAndIncrement();
            if (index < i) {
                result1.getCards().add(next);
            } else {
                result2.getCards().add(next);
            }
        }
        ArrayList<CardSet> listOfCardSets = new ArrayList<>();
        listOfCardSets.add(result1);
        listOfCardSets.add(result2);
        table.setCardSets(listOfCardSets);

        table.getCardsInPlay().add(cardSet.getCards().remove(i));
    }

    /**
     * add a card to the front
     *
     * @param cardSet
     * @param card
     */
    public void prependCard(CardSet cardSet, Card card) {
        cardSet.getCards().add(0, card);
    }

    /**
     * add a card to the back
     *
     * @param cardSet
     * @param card
     */
    public void appendCard(CardSet cardSet, Card card) {
        cardSet.getCards().add(card);
    }

    /**
     * deals fifteen random cards per player
     */
    public void dealHands(Player dealer) {
        int currPlayerID = dealer.getPlayerID();

        for (int cardCounter = 0; cardCounter < 15; cardCounter++) {

            int playerCounter = 0;

            while (playerCounter < players.size()) {

                if (currPlayerID >= players.size()) {

                    currPlayerID = 0;
                }
                players.get(currPlayerID).getHand().addCard(table.getDeck().pop());

                currPlayerID++;
                playerCounter++;
            }
        }

        StringCommand dealCommand = new StringCommand(Command.CommandNames.DEAL_HANDS);
        for (Player player : players) {
            // Let clients know their hand.
            dealCommand.setParameterValue(player.getHand() + " " + player.getSeatNumber());
            sendCommandToPlayer(dealCommand, player);
        }

        switchTurn(getSeat(dealer.getSeatNumber()));
    }

    /***************************************
     *************** PRIVATE HELPERS **************
     **************************************/
    /**
     *
     */
    private void verifyTable() {

    }

    public Seat getNextEmptySeat() {
        for (Seat seat : seats) {
            if (!seat.isTaken()) {
                return seat;
            }
        }
        return null;
    }

    public boolean isTableFull() {
        return numOfPlayers <= players.size();
    }

    public void addPlayer(ClientHandler clientHandler) {
        int playerId = players.size();
        Seat seat = getNextEmptySeat();
        String playerName = "Player" + playerId;

        Player player = new Player(playerId, playerName);
        player.setSeatNumber(seat.getSeatNumber());
        player.setClientHandler(clientHandler);

        seat.setPlayer(player);

        Command introduce = new IntroducePlayer(playerName, playerId, seat.getSeatNumber());
        Command welcome = new Welcome(playerName, playerId, seat.getSeatNumber(), numOfPlayers);

        // Welcome the new player
        sendCommandToPlayer(welcome, player);

        // Introduce the new player to others
        sendCommandToAllPlayersExcept(introduce, player);

        // Introduce others to the new player.
        for (Player p : players) {
            // Welcome the new player
            sendCommandToPlayer(new IntroducePlayer(p.getName(), p.getPlayerID(), p.getSeatNumber()), player);
        }

        players.add(player);
    }

    public void removePlayer(ClientHandler clientHandler) {
        Player player = null;
        for (Player p : players) {
            if (p.getClientHandler() == clientHandler) {
                player = p;
                break;
            }
        }
        if (player != null) {
            players.remove(player);
            sendCommandToAllPlayers(new Command("PLAYER_DROPPED " + player.getSeatNumber()));
        }
    }

    public void startGame() {
        if (!gameStarted && isTableFull()) {
            dealHands(getRandomPlayer());
            this.gameStarted = true;
        }
    }

    private void sendCommandToAllPlayers(Command command) {
        for (Player player : players) {
            sendCommandToPlayer(command, player);
        }
    }

    private void sendCommandToAllPlayersExcept(Command command, int playerSeatNumber) {
        for (Player player : players) {
            if (player.getSeatNumber() != playerSeatNumber) {
                sendCommandToPlayer(command, player);
            }
        }
    }

    private void sendCommandToAllPlayersExcept(Command command, Player exceptPlayer) {
        sendCommandToAllPlayersExcept(command, exceptPlayer.getSeatNumber());
    }

    private void sendCommandToAllPlayers(String command) {
        for (Player player : players) {
            sendCommandToPlayer(command, player);
        }
    }

    private void sendCommandToPlayer(Command command, Player player) {
        player.getClientHandler().sendCommand(command.toString());
    }

    private void sendCommandToPlayer(String command, Player player) {
        player.getClientHandler().sendCommand(command);
    }

    private boolean validateMove(int seatNumber, List<CardSet> proposedSets, CardSet playedCards) {
        // TODO: Check if the turn is at the right player (seat number)

        CardSet currentTable = table.getAllCardsInASet();
        CardSet proposedTable = new CardSet();

        // All proposed sets should be valid.
        for (CardSet set : proposedSets) {
            if (!set.isAValidMeld(3)) {
                return false;
            }
            proposedTable.join(set);
        }

        // Propose sets must contain at least all cards previously on the table.
        // aka: player can only add card to the table. Can't withdraw cards.
        if (!proposedTable.superSetOf(currentTable)) {
            return false;
        }

        // After appending the played cards to the table, the table should be equal to proposed table.
        currentTable.join(playedCards);
        if (!currentTable.equals(proposedTable)) {
            return false;
        }

        Seat seat = getSeat(seatNumber);
        Player player = seat.getPlayer();
        // Player hand should include all the cards played.
        if (!player.getHand().superSetOf(playedCards)) {
            return false;
        }

        return true;
    }

    public boolean processMove(PlayerMove playerMove) {
        if (!validateMove(playerMove.getSeatNumber(), playerMove.getTable(), playerMove.getPlayedCards())) {
            return false;
        }

        /** TODO
         * - Remove played cards from player hand.
         * - Replace the table sets with the proposed sets.
         * + Send new table to all players.
         * + Send Switch Turn to all players.
         */

        sendCommandToAllPlayersExcept(playerMove, playerMove.getSeatNumber());
        switchTurn();

        return true;
    }

    private void switchTurn(Seat seat) {
        currentSeat = seat;
        sendCommandToAllPlayers(new NumericCommand(Command.CommandNames.SWITCH_TURN, currentSeat.getSeatNumber()));
    }

    private void switchTurn() {
        switchTurn(currentSeat.getNextSeat());
    }


    private Seat getSeat(int seatNumber) {
        for (Seat seat : seats) {
            if (seat.getSeatNumber() == seatNumber) {
                return seat;
            }
        }
        return null;
    }

    public class EmptyDeckException extends Exception {
        public EmptyDeckException() {
            super("Deck is taken!");
        }
    }

    private class Seat {
        private int seatNumber;
        private Player player = null;
        private Boolean taken;
        private Seat nextSeat;

        public Seat(int seatNumber, Boolean taken) {
            this.taken = taken;
            this.seatNumber = seatNumber;
        }

        public Boolean isTaken() {
            return taken;
        }

        public void setTaken(Boolean taken) {
            this.taken = taken;
        }

        public int getSeatNumber() {
            return seatNumber;
        }

        public void setSeatNumber(int seatNumber) {
            this.seatNumber = seatNumber;
        }

        public Player getPlayer() {
            return player;
        }

        public void setPlayer(Player player) {
            this.player = player;
            this.taken = player != null;
        }

        public Seat getNextSeat() {
            return nextSeat;
        }

        public void setNextSeat(Seat nextSeat) {
            this.nextSeat = nextSeat;
        }
    }
}