package com.chatapp.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
import com.chatapp.config.LoggingConfig;

public class ChatServer {
    private final int port;
    private final Logger logger = LoggingConfig.getLogger();
    private final ExecutorService exec = Executors.newCachedThreadPool();
    private volatile boolean running = true;

    public ChatServer(int port) { this.port = port; }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("ChatServer started on port " + port);
            while (running) {
                try {
                    Socket client = serverSocket.accept();
                    logger.info("Accepted connection from " + client.getRemoteSocketAddress());
                    ClientHandler handler = new ClientHandler(client);
                    exec.submit(handler);
                } catch (IOException e) {
                    logger.warning("Accept failed: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            logger.severe("Server failed: " + e.getMessage());
        } finally {
            exec.shutdownNow();
        }
    }
}