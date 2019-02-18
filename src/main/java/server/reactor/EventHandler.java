package server.reactor;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public interface EventHandler<T> {
    /**
     * Invoked when a specific event of the type for which this handler is
     * registered happens.
     *
     * @param event the event which occurred
     */
    void handleEvent(SelectionKey event) throws IOException;
}

