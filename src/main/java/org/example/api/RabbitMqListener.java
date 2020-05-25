package org.example.api;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@EnableRabbit
@Component
public class RabbitMqListener {
    private ArrayList<String> messages = new ArrayList<>();

    @RabbitListener(queues = "${application.queue.name}")
    public void processQueue(String message) {
        messages.add(message);
    }

    public List<String> getLast100Messages() {
        if (messages.size() <= 100) {
            return messages;
        } else {
            return messages.subList(messages.size() - 100, messages.size());
        }
    }

    public List<String> getMessages(int offset, int limit) {
        return messages.subList(offset * limit, limit * (offset + 1));
    }

    public void clearMessages() {
        messages = new ArrayList<>();
    }
}
