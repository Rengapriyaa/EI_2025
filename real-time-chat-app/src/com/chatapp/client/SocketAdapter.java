package com.chatapp.client;

import com.chatapp.protocol.Protocol;
import com.chatapp.config.LoggingConfig;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

public class SocketAdapter {
    private final Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;
    private final Scanner scanner;
    private final PrintStream consoleOut;
    private final AtomicBoolean running = new AtomicBoolean(true);
    private final Logger logger = LoggingConfig.getLogger();

    public SocketAdapter(Socket socket, InputStream userIn, PrintStream consoleOut) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.scanner = new Scanner(userIn);
        this.consoleOut = consoleOut;
    }

    public void startInteractive() {
        // start listener thread
        Thread t = new Thread(this::listenLoop);
        t.setDaemon(true);
        t.start();

        consoleOut.println("Commands:");
        consoleOut.println("JOIN <room>|<username>");
        consoleOut.println("LEAVE");
        consoleOut.println("MSG <text>");
        consoleOut.println("PM <target>|<text>");
        consoleOut.println("LIST");
        consoleOut.println("HISTORY");
        consoleOut.println("QUIT");

        while (running.get() && scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line == null) break;
            line = line.trim();
            if (line.isEmpty()) continue;
            try {
                send(line);
                if (line.equalsIgnoreCase("QUIT")) { running.set(false); break; }
            } catch (IOException e) {
                logger.warning("Failed to send: " + e.getMessage());
                consoleOut.println("Failed to send: " + e.getMessage());
            }
        }

        try { socket.close(); } catch (IOException ignored) {}
    }

    private void listenLoop() {
        try {
            String line;
            while ((line = in.readLine()) != null) {
                consoleOut.println(line);
            }
        } catch (IOException e) {
            if (running.get()) logger.warning("Listener error: " + e.getMessage());
        } finally {
            running.set(false);
        }
    }

    private synchronized void send(String s) throws IOException {
        out.write(s);
        out.write("\n");
        out.flush();
    }
}