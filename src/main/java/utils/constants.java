package utils;

public class constants {

    public static final String TITLE = "Machiavelli";

    //Card Events Constants

    public static final String CARD_ADDED = "CARD_ADDED";
    public static final String CARD_REMOVED = "CARD_REMOVED";
    public static final String CARD_SELECTED = "CARD_SELECTED";

    //View Constants

    public static final String HOME_BACKGROUND_FILENAME = "game.jpg";
    public static final String GREY_BTN_STYLE = "-fx-base: orange; -fx-text-fill: black;";

    //Client Manager Constants

    public static final String PORT_ERROR_MESSAGE = "A game server is already started at port ";

    public static final int HAND_SIZE = 15;

    public static final int MINIMUM_SET_SIZE = 3;

    public enum GameMode {
        REACTIVE,
        PROACTIVE
    }
}
