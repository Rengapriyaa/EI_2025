package com.chatapp.client;

import com.chatapp.config.LoggingConfig;
import com.chatapp.protocol.Protocol;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

public class ChatClientMain {
    public static void main(String[] args) throws Exception {
        String host = "localhost";
        int port = 9000;
        if (args.length >= 1) host = args[0];
        if (args.length >= 2) port = Integer.parseInt(args[1]);
        Logger logger = LoggingConfig.getLogger();
        try (Socket socket = new Socket(host, port)) {
            System.out.println("Connected to server " + host + ":" + port);
            SocketAdapter adapter = new SocketAdapter(socket, System.in, System.out);
            adapter.startInteractive();
        } catch (IOException e) {
            logger.severe("Failed to connect: " + e.getMessage());
            System.err.println("Connection failed: " + e.getMessage());
        }
    }
}