package server.proactor;

import server.ServerModeRunner;

public class Proactor implements ServerModeRunner {

    @Override
    public void run() {
        // Sleep indefinitely since otherwise the JVM would terminate
        while (true) {
            try {
                Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
