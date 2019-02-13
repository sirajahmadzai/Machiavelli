package commands.client;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Server tells clients to switch the turn to the next player.
 */
public class ClientMessage extends ClientCommand {
    public enum MessageTypes {
        ERROR,
        WARNING,
        INFO
    }

    /**
     * PRIVATES
     */
    private MessageTypes messageType;
    private String messageText;


    /**
     * CONSTRUCTOR
     */
    public ClientMessage() {
        super(CommandNames.CLIENT_MESSAGE);
    }

    /**
     * CONSTRUCTOR
     *
     * @param commandStr
     */
    public ClientMessage(String commandStr) {
        super(commandStr);
    }

    /**
     * CONSTRUCTOR
     *
     * @param messageType
     * @param messageText
     */
    public ClientMessage(MessageTypes messageType, String messageText) {
        this();
        this.messageType = messageType;
        this.messageText = URLEncoder.encode(messageText);

        addParameter(this.messageType);
        addParameter(this.messageText);
    }

    /**
     * @param commandStr
     */
    @Override
    public void doParse(String commandStr) {
        this.messageType = MessageTypes.valueOf(scanner.next());
        this.messageText = scanner.next();
    }

    /**
     *
     */
    @Override
    public void doExecute() {
        manager.serverMessage(messageType, URLDecoder.decode(messageText));
    }
}
