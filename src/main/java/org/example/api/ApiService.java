package org.example.api;

import org.example.CreateMessageRequest;
import org.example.User;

import java.util.List;

public interface ApiService {

    void sendMessage(CreateMessageRequest request);

    List<String> getLast100Messages();

    List<String> getMessages(int offset, int limit);

    void clearMessages();
}
