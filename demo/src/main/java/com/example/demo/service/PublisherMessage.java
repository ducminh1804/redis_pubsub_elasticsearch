package com.example.demo.service;

import com.example.demo.entity.MessageType;
import com.example.demo.entity.Post;
import com.example.demo.entity.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;

@Service
public class PublisherMessage {
    @Autowired
    private RedisOperations operations;

    public void send(MessageType messageType) {
        operations.convertAndSend(Topic.Post.name(),messageType);
    }
}
