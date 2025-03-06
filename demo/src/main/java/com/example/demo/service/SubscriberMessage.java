package com.example.demo.service;

import com.example.demo.entity.MessageType;
import com.example.demo.entity.Post;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class SubscriberMessage implements MessageListener {
    @Autowired
    private ElasticsearchService  elasticsearchService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            MessageType<Post> messageType = objectMapper.readValue(message.getBody(), new TypeReference<MessageType<Post>>() {});
            String type = messageType.getType();
            Object value = messageType.getValue();
            Post post = messageType.getValue();
            log.info("type {}", type);
            log.info("value {}", value);
           switch (type) {
               case "Post":
                   elasticsearchService.save(post);
                   log.info("success {}", "insert to es: "+ value);
               default:
                   break;
           }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
