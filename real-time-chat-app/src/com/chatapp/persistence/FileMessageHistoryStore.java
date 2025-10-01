package com.chatapp.persistence;

import com.chatapp.model.Message;
import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.chatapp.config.LoggingConfig;

public class FileMessageHistoryStore implements MessageHistoryStore {
    private final File file;
    private final Logger logger = LoggingConfig.getLogger();

    public FileMessageHistoryStore(String roomId) {
        File dir = new File("data");
        if (!dir.exists()) dir.mkdirs();
        this.file = new File(dir, roomId + ".log");
    }

    @Override
    public synchronized void append(Message message) throws Exception {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            // write: ISO_INSTANT|sender|content
            String line = message.getTimestamp().toString() + "|" + message.getSender() + "|" + message.getContent().replaceAll("\n", " ");
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            logger.log(Level.WARNING, "Failed to append history", e);
            throw e;
        }
    }

    @Override
    public synchronized List<Message> load() {
        List<Message> out = new ArrayList<>();
        if (!file.exists()) return out;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|", 3);
                if (parts.length < 3) continue;
                try {
                    Instant ts = Instant.parse(parts[0]);
                    out.add(new Message(parts[1], parts[2], ts));
                } catch (Exception e) {
                    logger.log(Level.WARNING, "Skipping malformed history line", e);
                }
            }
        } catch (IOException e) {
            logger.log(Level.WARNING, "Failed to load history file", e);
        }
        return out;
    }
}