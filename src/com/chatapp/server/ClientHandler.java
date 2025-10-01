package com.chatapp.server;

import com.chatapp.core.ChatRoom;
import com.chatapp.core.ChatRoomManager;
import com.chatapp.config.LoggingConfig;

import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class ClientHandler implements Runnable, ChatRoom.ClientNotifier {
    private final Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private String username;
    private ChatRoom currentRoom;
    private final Logger logger = LoggingConfig.getLogger();

    public ClientHandler(Socket socket) { this.socket = socket; }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            sendLine("INFO Welcome to RealTimeChatApp Server");
            String line;
            while ((line = in.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                try {
                    if (line.startsWith("JOIN ")) {
                        handleJoin(line.substring(5));
                    } else if (line.equals("LEAVE")) {
                        handleLeave();
                    } else if (line.startsWith("MSG ")) {
                        handleMsg(line.substring(4));
                    } else if (line.startsWith("PM ")) {
                        handlePm(line.substring(3));
                    } else if (line.equals("LIST")) {
                        handleList();
                    } else if (line.equals("HISTORY")) {
                        handleHistory();
                    } else if (line.equals("QUIT")) {
                        break;
                    } else {
                        sendLine("ERROR Unknown command");
                    }
                } catch (Exception e) {
                    logger.warning("Handler error: " + e.getMessage());
                    sendLine("ERROR " + e.getMessage());
                }
            }
        } catch (IOException e) {
            logger.warning("IO error: " + e.getMessage());
        } finally {
            cleanup();
        }
    }

    private void handleJoin(String payload) {
        // payload format: roomId|username
        String[] p = payload.split("\\|", 2);
        if (p.length < 2) { sendLine("ERROR JOIN requires room|username"); return; }
        String roomId = p[0];
        String user = p[1];
        this.username = user;
        ChatRoom room = ChatRoomManager.getInstance().getOrCreateRoom(roomId);
        room.join(user, this);
        this.currentRoom = room;
        sendLine("INFO Joined " + roomId);
    }

    private void handleLeave() {
        if (currentRoom != null && username != null) {
            currentRoom.leave(username);
            sendLine("INFO Left " + currentRoom.getRoomId());
            this.currentRoom = null;
        } else sendLine("ERROR Not in a room");
    }

    private void handleMsg(String text) {
        if (currentRoom == null || username == null) { sendLine("ERROR Join a room first"); return; }
        currentRoom.broadcast(username, text);
    }

    private void handlePm(String payload) {
        if (currentRoom == null || username == null) { sendLine("ERROR Join a room first"); return; }
        String[] p = payload.split("\\|", 2);
        if (p.length < 2) { sendLine("ERROR PM requires target|message"); return; }
        String target = p[0];
        String msg = p[1];
        currentRoom.sendPrivate(username, target, msg);
    }

    private void handleList() {
        if (currentRoom == null || username == null) { sendLine("ERROR Join a room first"); return; }
        currentRoom.sendActiveUsersTo(username);
    }

    private void handleHistory() {
        if (currentRoom == null || username == null) { sendLine("ERROR Join a room first"); return; }
        currentRoom.printHistoryTo(username);
    }

    @Override
    public void notify(String line) {
        sendLine(line);
    }

    private synchronized void sendLine(String s) {
        try {
            out.write(s);
            out.write("\n");
            out.flush();
        } catch (IOException e) {
            logger.warning("Failed to send to client: " + e.getMessage());
        }
    }

    private void cleanup() {
        try {
            if (currentRoom != null && username != null) currentRoom.leave(username);
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException ignored) {}
        logger.info("Connection closed for " + username);
    }
}