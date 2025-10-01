package com.chatapp.core;

import java.util.concurrent.ConcurrentHashMap;

public class ChatRoomManager {
    private static final ChatRoomManager INSTANCE = new ChatRoomManager();
    private final ConcurrentHashMap<String, ChatRoom> rooms = new ConcurrentHashMap<>();

    private ChatRoomManager() {}

    public static ChatRoomManager getInstance() { return INSTANCE; }

    public ChatRoom getOrCreateRoom(String roomId) {
        return rooms.computeIfAbsent(roomId, ChatRoom::new);
    }
}