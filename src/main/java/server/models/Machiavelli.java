package server.models;

import commands.Command;
import commands.ServerCommands;
import commands.StringCommand;
import server.ClientHandler;
import server.models.cards.Card;

import java.util.ArrayList;
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


    /**
     * CONSTRUCTOR
     *
     * @param numOfPlayers
     */
    public Machiavelli(int numOfPlayers) {
        players = new ArrayList<>();
        table = new Table();
        this.numOfPlayers = numOfPlayers;

        //creates players based on the numOfPlayers
//        for (int playerCounter = 0; playerCounter < numOfPlayers; playerCounter++) {
//            players.add(new Player(numOfPlayers, "Player" + playerCounter + 1));
//        }
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
            player.getHand().add(table.getDeck().remove(table.getDeck().size() - 1));
        } catch (ArrayIndexOutOfBoundsException e) {
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
    public Card removeCardFromHand(int indexOfPlayer, int indexOfCard) {
        return players.get(indexOfPlayer).getHand().remove(indexOfCard);
    }

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
                players.get(currPlayerID).getHand().add(table.getDeck().remove(table.getDeck().size() - 1));

                currPlayerID++;
                playerCounter++;
            }
        }

        StringCommand dealCommand = new StringCommand(Command.CommandNames.DEAL_HANDS);
        for (Player player : players) {
            dealCommand.setParameterValue(player.getHandAsText()+" "+ player.getPlayerID() );
            sendCommandToPlayer(dealCommand, player);
        }
    }

    /***************************************
     *************** PRIVATE HELPERS **************
     **************************************/
    /**
     *
     */
    private void verifyTable() {

    }

    public boolean isTableFull() {
        return numOfPlayers <= players.size();
    }

    public void addPlayer(ClientHandler clientHandler) {
        String playerName = "Player" + (players.size() + 1);
        Player player = new Player(players.size(), playerName);
        player.setClientHandler(clientHandler);
        players.add(player);
        sendCommandToAllPlayers(new Command(Command.CommandNames.INTRODUCE_PLAYER).addParameter(playerName));
    }

    public void startGame() {
        
        if (!gameStarted && isTableFull()) {
            sendCommandToAllPlayers(ServerCommands.SHOW_GAMEVIEW + " " + numOfPlayers);
            dealHands(players.get(0));
            this.gameStarted = true;
        }
    }

    private void sendCommandToAllPlayers(Command command) {
        for (Player player : players) {
            sendCommandToPlayer(command, player);
        }
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

    public class EmptyDeckException extends Exception {
        public EmptyDeckException() {
            super("Deck is empty!");
        }
    }
}