package com.chatapp.core;

import com.chatapp.model.Message;
import com.chatapp.persistence.FileMessageHistoryStore;
import com.chatapp.persistence.MessageHistoryStore;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import com.chatapp.config.LoggingConfig;

public class ChatRoom {
    private final String roomId;
    private final Map<String, ClientNotifier> clients = new ConcurrentHashMap<>();
    private final List<Message> history = Collections.synchronizedList(new ArrayList<>());
    private final MessageHistoryStore historyStore;
    private final Logger logger = LoggingConfig.getLogger();

    // notifier interface (so core package doesn't depend on server package classes directly)
    public interface ClientNotifier { void notify(String line); }

    public ChatRoom(String roomId) {
        this.roomId = roomId;
        this.historyStore = new FileMessageHistoryStore(roomId);
        List<Message> persisted = historyStore.load();
        if (persisted != null) history.addAll(persisted);
        logger.info("ChatRoom created/loaded: " + roomId);
    }

    public String getRoomId() { return roomId; }

    public void join(String username, ClientNotifier notifier) {
        clients.put(username, notifier);
        broadcastSystem(username + " joined the room");
        logger.info(username + " joined " + roomId);
    }

    public void leave(String username) {
        clients.remove(username);
        broadcastSystem(username + " left the room");
        logger.info(username + " left " + roomId);
    }

    public List<String> getActiveUsers() { return new ArrayList<>(clients.keySet()); }

    public void broadcast(String sender, String content) {
        Message msg = new Message(sender, content, Instant.now());
        appendToHistory(msg);
        String line = "MSG " + msg.toString();
        for (ClientNotifier n : clients.values()) {
            safeNotify(n, line);
        }
    }

    public void sendPrivate(String sender, String target, String content) {
        Message msg = new Message(sender, content + " (private)", Instant.now());
        appendToHistory(msg);
        String line = "MSG " + msg.toString();
        ClientNotifier targetN = clients.get(target);
        ClientNotifier senderN = clients.get(sender);
        if (senderN != null) safeNotify(senderN, line);
        if (targetN != null) safeNotify(targetN, line);
    }

    public void printHistoryTo(String username) {
        ClientNotifier n = clients.get(username);
        if (n == null) return;
        synchronized (history) {
            for (Message m : history) safeNotify(n, "HISTORY " + m.toString());
        }
    }

    public void sendActiveUsersTo(String username) {
        ClientNotifier n = clients.get(username);
        if (n == null) return;
        String users = String.join(",", getActiveUsers());
        safeNotify(n, "USERS " + users);
    }

    private void broadcastSystem(String content) {
        String line = "SYSTEM " + content;
        for (ClientNotifier n : clients.values()) safeNotify(n, line);
    }

    private void appendToHistory(Message msg) {
        history.add(msg);
        try { historyStore.append(msg); } catch (Exception e) { logger.warning("Failed to persist message"); }
    }

    private void safeNotify(ClientNotifier n, String line) {
        try { n.notify(line); } catch (Exception ignored) {}
    }
}