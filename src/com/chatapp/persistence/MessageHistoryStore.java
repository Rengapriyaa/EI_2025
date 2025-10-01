package com.chatapp.persistence;

import com.chatapp.model.Message;
import java.util.List;

public interface MessageHistoryStore {
    void append(Message message) throws Exception;
    List<Message> load();
}