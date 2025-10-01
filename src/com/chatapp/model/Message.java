package com.chatapp.model;

import java.time.Instant;

public final class Message {
    private final String sender;
    private final String content;
    private final Instant timestamp;

    public Message(String sender, String content, Instant timestamp) {
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getSender() { return sender; }
    public String getContent() { return content; }
    public Instant getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return "[" + timestamp.toString() + "] " + sender + ": " + content;
    }
}