package server.models;

import commands.BasicCommand;
import commands.Command;
import commands.client.*;
import commands.server.PlayerMove;
import server.ClientHandler;
import server.models.cards.Card;
import server.models.cards.HiddenCard;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

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
    private ArrayList<Seat> seats;
    private Seat currentSeat;

    /**
     * PRIVATE STATICS
     */
    private static Machiavelli ourInstance = new Machiavelli();

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
     * @return table object
     */
    public Table getTable() {
        return table;
    }

    /**
     * @return players ArrayList
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

    /**
     * @param players
     */
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    /**
     * @param numOfPlayers
     */
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


    /***************************************
     *************** PUBLIC MODIFIERS **************
     **************************************/

    /**
     * @param player
     */
    public Card drawCardFromDeck(Player player) throws EmptyDeckException {
        try {
            Card card = table.getDeck().pop();
            player.getHand().addCard(card);
            return card;
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

        for (Player player : players) {
            DealHands dealHandsCommand = new DealHands(player.getSeatNumber(), player.getHand());
            // Let clients know their hand.
            sendCommandToPlayer(dealHandsCommand, player);
        }

        switchTurn(getSeat(dealer.getSeatNumber()));
    }

    /***************************************
     *************** PRIVATE HELPERS **************
     **************************************/

    /**
     * @return
     */
    public Seat getNextEmptySeat() {
        for (Seat seat : seats) {
            if (!seat.isTaken()) {
                return seat;
            }
        }
        return null;
    }

    /**
     * @return
     */
    public boolean isTableFull() {
        return numOfPlayers <= players.size();
    }


    /**
     * @param playerName
     * @param player
     */
    public void playerLogin(String playerName, Player player) {
        player.setName(playerName);

        Command introduce = new IntroducePlayer(player.getName(), player.getPlayerID(), player.getSeatNumber());
        // Let all players know this player's name.
        sendCommandToAllPlayers(introduce);
    }

    /**
     * @param clientHandler
     */
    public void addPlayer(ClientHandler clientHandler) {
        int playerId = players.size();
        Seat seat = getNextEmptySeat();
        String playerName = "Player" + playerId;

        Player player = new Player(playerId, playerName);
        player.setSeatNumber(seat.getSeatNumber());
        player.setClientHandler(clientHandler);
        clientHandler.setPlayer(player);

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

    /**
     * @param clientHandler
     */
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
            sendCommandToAllPlayers(new BasicCommand("PLAYER_DROPPED " + player.getSeatNumber()));
        }
    }

    /**
     *
     */
    public void startGame() {
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
        player.getClientHandler().sendCommand(command.toString());
    }

    /**
     * @param command
     * @param player
     */
    private void sendCommandToPlayer(String command, Player player) {
        player.getClientHandler().sendCommand(command);
    }

    /**
     * @param seatNumber
     * @param proposedSets
     * @param playedCards
     * @return
     */
    private boolean validateMove(int seatNumber, List<CardSet> proposedSets, CardSet playedCards) {


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

    /**
     * @param playerMove
     * @return
     */
    public boolean processMove(PlayerMove playerMove) {
        if (!validateMove(playerMove.getSeatNumber(), playerMove.getTable(), playerMove.getPlayedCards())) {
            return false;
        }



        Player player = currentSeat.getPlayer();
        player.getHand().removeCards(playerMove.getPlayedCards());

        table.setCardSets(playerMove.getTable());

        sendCommandToAllPlayersExcept(playerMove, playerMove.getSeatNumber());
        switchTurn();

        return true;
    }

    /**
     *
     */
    public void passTurn() {
        try {
            Card card = drawCardFromDeck(currentSeat.getPlayer());
            Command cmdDrawOpenCard = new DrawCard(currentSeat.getSeatNumber(), card.toString());
            sendCommandToPlayer(cmdDrawOpenCard, currentSeat.getPlayer());

            Command cmdDrawClosedCard = new DrawCard(currentSeat.getSeatNumber(), HiddenCard.getInstance().toString());
            sendCommandToAllPlayersExcept(cmdDrawClosedCard, currentSeat.seatNumber);

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
     *
     */
    public class EmptyDeckException extends Exception {
        public EmptyDeckException() {
            super("Deck is taken!");
        }
    }

    /**
     *
     */
    private class Seat {
        /**
         * PRIVATES
         */
        private int seatNumber;
        private Player player = null;
        private Boolean taken;
        private Seat nextSeat;

        /**
         * CONSTRUCTOR
         *
         * @param seatNumber
         * @param taken
         */
        public Seat(int seatNumber, Boolean taken) {
            this.taken = taken;
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
        public Seat getNextSeat() {
            return nextSeat;
        }

        /**
         * sets the boolean "taken"
         *
         * @param taken
         */
        public void setTaken(Boolean taken) {
            this.taken = taken;
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
            this.taken = player != null;
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
            return taken;
        }
    }
}