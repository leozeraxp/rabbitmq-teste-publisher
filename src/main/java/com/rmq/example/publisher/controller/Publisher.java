package com.rmq.example.publisher.controller;


import com.rmq.example.publisher.service.PublisherService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class Publisher {

    private final PublisherService publisherService;

    @Value("${rabbitmq.queuename}")
    private String queueName;

    public Publisher(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @PostMapping("/publish/text")
    public void publishText(@RequestBody String text) {
        System.out.println("Publish text: " + text);
        publisherService.publishTextMessage(text, queueName);
        return ;
    }

    @PostMapping("/publish/json")
    public void publishJson(@RequestBody String text) {
        System.out.println("Publish text: " + text);
        publisherService.publishJsonMessage(text, queueName);
        return ;
    }
}
