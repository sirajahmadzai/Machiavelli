package server.models;

import commands.Command;
import commands.client.*;
import commands.server.PlayerMove;
import commands.server.WinnerCommand;
import server.ClientMessageSender;
import server.models.cards.Card;
import server.models.cards.HiddenCard;
import utils.constants;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Random;

import static commands.Command.CommandNames.TABLE_IS_FULL;

/**
 *
 */
public class Machiavelli {
    /********************************
     ******** PRIVATES **************
     ********************************/
    private Table table;
    private ArrayList<Player> players;
    private int numOfPlayers;
    private boolean gameStarted = false;
    private Seat currentSeat;
    private TableSeats tableSeats = null;

    /**
     * PRIVATE STATICS
     */
    private static Machiavelli ourInstance = new Machiavelli();
    private constants.GameMode gameMode;

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
     * gets a random player from players ArrayList
     *
     * @return
     */
    private Player getRandomPlayer() {
        int currPlayerID = (new Random()).nextInt(players.size());

        return players.get(currPlayerID);
    }

    /**
     * @param numOfPlayers
     */
    public void initialize(int numOfPlayers) {
        players = new ArrayList<>();
        table = new Table();
        this.numOfPlayers = numOfPlayers;
        this.tableSeats = new TableSeats(numOfPlayers);
    }


    /***************************************
     *************** PUBLIC MODIFIERS **************
     **************************************/

    /**
     * @param player
     */
    private Card drawCardFromDeck(Player player) throws EmptyDeckException {
        try {
            Card card = table.getDeck().pop();
            player.getHand().addCard(card);
            return card;
        } catch (EmptyStackException e) {
            throw new EmptyDeckException();

        }
    }

    /**
     * deals fifteen random cards per player
     */
    private void dealHands(Player dealer) {
        int currPlayerID = dealer.getPlayerID();

        for (int cardCounter = 0; cardCounter < constants.HAND_SIZE; cardCounter++) {

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

        for (Player player : players) {
            DealHands dealHandsCommand = new DealHands(player.getSeatNumber(), player.getHand());
            // Let clients know their hand.
            sendCommandToPlayer(dealHandsCommand, player);
        }

        switchTurn(tableSeats.getSeat(dealer.getSeatNumber()));
    }

    /***************************************
     *************** PRIVATE HELPERS **************
     **************************************/

    /**
     * @return
     */
    public synchronized boolean isTableFull() {
        return numOfPlayers <= players.size();
    }

    /**
     * @param playerName
     * @param player
     */
    public synchronized void playerLogin(String playerName, Player player) {
        if (isTableFull()) {
            sendCommandToPlayer(TABLE_IS_FULL.toString(), player);
            return;
        }
        player.setName(playerName);

        // Reintroduce the player with it's new name.
        Command introduce = new IntroducePlayer(player.getName(), player.getPlayerID(), player.getSeatNumber());
        // Let all players know this player's name.
        sendCommandToAllPlayers(introduce);
    }

    public synchronized Player addPlayer() {
        // Create new player
        int playerId = players.size();
        String playerName = "Player" + playerId;
        Player player = new Player(playerId, playerName);
        players.add(player);

        // Place the player in the next empty seat
        tableSeats.seatNewPlayer(player);

        return player;
    }

    public synchronized void introducePlayer(Player player, constants.GameMode gameMode) {
        this.gameMode = gameMode;
        Command introduce = new IntroducePlayer(player.getName(), player.getPlayerID(), player.getSeatNumber());
        Command welcome = new Welcome(player.getName(), player.getPlayerID(), player.getSeatNumber(), numOfPlayers, gameMode);

        // Welcome the new player
        sendCommandToPlayer(welcome, player);

        // Introduce the new player to others
        sendCommandToAllPlayersExcept(introduce, player);

        // Introduce others to the new player.
        for (Player p : players) {
            // Welcome the new player
            sendCommandToPlayer(new IntroducePlayer(p.getName(), p.getPlayerID(), p.getSeatNumber()), player);
        }

        // Request player's name. Once player responds we'll introduce him tto the table again.
        sendCommandToPlayer(Command.CommandNames.WHO_ARE_YOU.toString(), player);
    }

    /**
     * @param player
     */
    public synchronized void playerLeftTheGame(Player player) {
        if (player != null) {
            players.remove(player);
            tableSeats.emptySeat(player.getSeatNumber());
            gameStarted = false;

            sendCommandToAllPlayers(new RemovePlayer(player.getSeatNumber()));
            resetGame();
        }
    }

    private void resetGame() {
        table.initMachiavelliDeck();
        for (Player player : players) {
            player.getHand().removeAllCards();
        }
    }

    /**
     *
     */
    public synchronized void startGame() {
        if (!gameStarted && isTableFull()) {
            dealHands(getRandomPlayer());
            this.gameStarted = true;
        }
    }

    /**
     * @param command
     */
    private void sendCommandToAllPlayers(Command command) {
        for (Player player : players) {
            sendCommandToPlayer(command, player);
        }
    }

    /**
     * @param command
     * @param playerSeatNumber
     */
    private void sendCommandToAllPlayersExcept(Command command, int playerSeatNumber) {
        for (Player player : players) {
            if (player.getSeatNumber() != playerSeatNumber) {
                sendCommandToPlayer(command, player);
            }
        }
    }

    /**
     * @param command
     * @param exceptPlayer
     */
    private void sendCommandToAllPlayersExcept(Command command, Player exceptPlayer) {
        sendCommandToAllPlayersExcept(command, exceptPlayer.getSeatNumber());
    }

    /**
     * @param command
     */
    private void sendCommandToAllPlayers(String command) {
        for (Player player : players) {
            sendCommandToPlayer(command, player);
        }
    }

    /**
     * @param command
     * @param player
     */
    private void sendCommandToPlayer(Command command, Player player) {
        sendCommandToPlayer(command.toString(), player);
    }

    /**
     * @param command
     * @param player
     */
    private void sendCommandToPlayer(String command, Player player) {
        ClientMessageSender.getInstance().sendCommand(player, command);
    }

    /**
     * @param seatNumber
     * @param proposedSets
     * @param playedCards
     * @return
     */
    private synchronized String validateMove(int seatNumber, List<CardSet> proposedSets, CardSet playedCards) {
        CardSet currentTable = table.getAllCardsInASet();
        CardSet proposedTable = new CardSet();

        if(isReactive() && seatNumber != currentSeat.getSeatNumber()){
            return "Please wait for your turn.";
        }

        // All proposed sets should be valid.
        for (CardSet set : proposedSets) {
            if (!set.isAValidMeld(3)) {
                return "Proposed table contains invalid meld(s).";
            }
            proposedTable.join(set);
        }

        // Proposed sets must contain at least all cards previously on the table.
        // aka: player can only add card to the table. Can't withdraw cards.
        if (!proposedTable.superSetOf(currentTable)) {
            return "Player can only add card to the table. Can't withdraw cards.";
        }

        Player player = tableSeats.getPlayer(seatNumber);
        // Player hand should include all the cards played.
        if (!player.getHand().superSetOf(playedCards)) {
            return "Your hand doesn't contain all the cards played.!";
        }

        // After appending the played cards to the table, the table should be equal to proposed table.
        currentTable.join(playedCards);
        if (!currentTable.equals(proposedTable)) {
            return "The proposed table doesn't match, other players may have already played a move.";
        }

        return "VALID";
    }

    /**
     * @param playerMove
     * @return
     */
    public synchronized void processMove(PlayerMove playerMove) {
        Player player = tableSeats.getPlayer(playerMove.getSeatNumber());
        String validationResult = validateMove(playerMove.getSeatNumber(), playerMove.getTable(), playerMove.getPlayedCards());
        if (!validationResult.equals("VALID")) {
            sendCommandToPlayer(new ClientMessage(ClientMessage.MessageTypes.WARNING, validationResult), player);
            return;
        }

        // Remove played cards from hand.
        player.getHand().removeCards(playerMove.getPlayedCards());

        // Set new table after the move
        table.setCardSets(playerMove.getTable());

        sendCommandToAllPlayers(playerMove);
        if (player.getHand().totalCount() <= 0) {
            setWinner(player);
        } else {
            switchTurn();
        }

    }

    private void setWinner(Player winner) {
        Command setWinner = new WinnerCommand(winner.getSeatNumber());
        sendCommandToAllPlayers(setWinner);
    }

    /**
     * Pass turn means the player can not play any melds and simply draws a card.
     * In Reactive mode this passes the turn to the next player.
     * In Proactive mode since there's no turn player may continue to draw cards.
     *
     * @param player the player who send the command
     */
    public synchronized void passTurn(Player player) {
        try {
            Card card = drawCardFromDeck(player);
            Command cmdDrawOpenCard = new DrawCard(player.getSeatNumber(), card.toString());
            sendCommandToPlayer(cmdDrawOpenCard, player);

            Command cmdDrawClosedCard = new DrawCard(player.getSeatNumber(), HiddenCard.getInstance().toString());
            sendCommandToAllPlayersExcept(cmdDrawClosedCard, player.getSeatNumber());

            switchTurn();
        } catch (EmptyDeckException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param seat
     */
    private void switchTurn(Seat seat) {
        currentSeat = seat;
        sendCommandToAllPlayers(new SwitchTurn(currentSeat.getSeatNumber()));
    }

    /**
     *
     */
    private void switchTurn() {
        switchTurn(currentSeat.getNextSeat());
    }

    private boolean isReactive(){
        return gameMode == constants.GameMode.REACTIVE;
    }

    /**
     *
     */
    public class EmptyDeckException extends Exception {
        EmptyDeckException() {
            super("Deck is taken!");
        }
    }

    private class TableSeats {
        private ArrayList<Seat> seats;

        // Initialize the empty seats in a circular order.
        TableSeats(int numOfPlayers) {
            seats = new ArrayList<>(numOfPlayers);

            // Create seats.
            Seat prevSeat = null;
            for (int seatNumber = 1; seatNumber <= numOfPlayers; seatNumber++) {
                Seat seat = new Seat(seatNumber);

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
         * @param seatNumber
         * @return
         */
        private Seat getSeat(int seatNumber) {
            for (Seat seat : seats) {
                if (seat.getSeatNumber() == seatNumber) {
                    return seat;
                }
            }
            return null;
        }

        /**
         * @return
         */
        Seat getNextEmptySeat() {
            for (Seat seat : seats) {
                if (!seat.isTaken()) {
                    return seat;
                }
            }
            return null;
        }

        void emptySeat(int seatNumber) {
            Seat seat = getSeat(seatNumber);
            seat.setPlayer(null);
        }

        Player getPlayer(int seatNumber) {
            return getSeat(seatNumber).getPlayer();
        }

        void seatNewPlayer(Player player) {
            Seat seat = getNextEmptySeat();
            player.setSeatNumber(seat.getSeatNumber());
            seat.setPlayer(player);
        }
    }


    private class Seat {
        /**
         * PRIVATES
         */
        private int seatNumber;
        private Player player = null;
        private Seat nextSeat;

        /**
         * CONSTRUCTOR
         *
         * @param seatNumber
         */
        Seat(int seatNumber) {
            this.seatNumber = seatNumber;
        }

        /**
         * GETTERS
         */
        /**
         * gets the seatNumber
         *
         * @return int seatNumber
         */
        public int getSeatNumber() {
            return seatNumber;
        }

        /**
         * gets the player
         *
         * @return Player object "player"
         */
        public Player getPlayer() {
            return player;
        }

        /**
         * gets the next seat
         *
         * @return int nextSeat
         */
        Seat getNextSeat() {
            return nextSeat;
        }

        /**
         * sets the seatNumber
         *
         * @param seatNumber
         */
        public void setSeatNumber(int seatNumber) {
            this.seatNumber = seatNumber;
        }

        /**
         * sets the player
         *
         * @param player
         */
        public void setPlayer(Player player) {
            this.player = player;
        }

        /**
         * @param nextSeat
         */
        public void setNextSeat(Seat nextSeat) {
            this.nextSeat = nextSeat;
        }

        /**
         * @return
         */
        public Boolean isTaken() {
            return player != null;
        }
    }
}