package utils;

import java.io.IOException;
import java.util.logging.*;

/**
 * Minimal logger setup: console + file handler.
 * Use this once at startup.
 */
public final class LoggerUtil {
    private LoggerUtil() {}

    public static void configureLogging() {
        Logger root = Logger.getLogger("");
        // remove default handlers to avoid duplicate messages
        for (Handler h : root.getHandlers()) {
            root.removeHandler(h);
        }

        // Console handler
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.INFO);
        consoleHandler.setFormatter(new SimpleFormatter());
        root.addHandler(consoleHandler);

        // File handler (rotating) - small size for demo
        try {
            Handler fileHandler = new FileHandler("app.log", 1_000_000, 3, true);
            fileHandler.setLevel(Level.FINE);
            fileHandler.setFormatter(new SimpleFormatter());
            root.addHandler(fileHandler);
        } catch (IOException e) {
            // If file handler fails, log to console and continue (transient handling)
            root.log(Level.WARNING, "Failed to initialize file handler: " + e.getMessage(), e);
        }

        root.setLevel(Level.FINE);
    }
}
