package com.chatapp.server;

import com.chatapp.config.LoggingConfig;
import java.util.logging.Logger;

public class ChatServerMain {
    public static void main(String[] args) {
        Logger logger = LoggingConfig.getLogger();
        int port = 9000;
        if (args.length > 0) {
            try { port = Integer.parseInt(args[0]); } catch (NumberFormatException ignored) {}
        }
        logger.info("Starting ChatServer on port " + port);
        ChatServer server = new ChatServer(port);
        server.start();
    }
}