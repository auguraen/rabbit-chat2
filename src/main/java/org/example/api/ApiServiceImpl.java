package org.example.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.CreateMessageRequest;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@EnableRabbit
@Service
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService {
    private final AmqpTemplate template;
    private final RabbitMqListener mqListener;

    @Value("${application.queue.name}")
    private String queueName;

    @Override
    public void sendMessage(CreateMessageRequest request) {
        template.convertAndSend(queueName, request.getUser().getUserName() + ": " + request.getMessage());
    }

    @Override
    public List<String> getLast100Messages() {
        return mqListener.getLast100Messages();
    }

    @Override
    public List<String> getMessages(int offset, int limit) {
        return mqListener.getMessages(offset, limit);
    }

    @Override
    public void clearMessages() {
        mqListener.clearMessages();
    }

}
