package com.rmq.example.publisher.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rmq.example.publisher.model.QueueMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class PublisherService {

    private final RabbitTemplate rabbitTemplate;

    public PublisherService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishTextMessage(String message, String queueName) {
        System.out.println("Publish message: " + message);
        rabbitTemplate.convertAndSend(queueName, message);
    }

    public void publishJsonMessage(String message, String queueName) {
        QueueMessage msgObject = (QueueMessage) jsonToObject(message, QueueMessage.class);
        rabbitTemplate.convertAndSend(queueName, objectToJsonNode(msgObject));
    }

    private JsonNode objectToJsonNode(Object object) {
        return new ObjectMapper().valueToTree(object);
    }

    private Object jsonToObject(String json, Class<?> classSerializable) {
        try {
            return new ObjectMapper().readValue(json, classSerializable);
        }catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }
}
