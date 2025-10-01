package com.chatapp.config;

import java.io.IOException;
import java.util.logging.*;

public final class LoggingConfig {
    private static final String DEFAULT_LOG_FILE = "chatapp.log";
    private static Logger logger;

    private LoggingConfig() {}

    public static Logger getLogger() {
        if (logger == null) {
            logger = Logger.getLogger("ChatAppLogger");
            logger.setLevel(Level.INFO);

            try {
                FileHandler fh = new FileHandler(DEFAULT_LOG_FILE, true);
                fh.setFormatter(new SimpleFormatter());
                logger.addHandler(fh);
            } catch (IOException e) {
                // fallback to console
                logger.log(Level.WARNING, "Failed to create file handler", e);
            }
        }
        return logger;
    }
}
