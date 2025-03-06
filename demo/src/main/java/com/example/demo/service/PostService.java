package com.example.demo.service;

import com.example.demo.entity.MessageType;
import com.example.demo.entity.Post;
import com.example.demo.repository.jpa.PostJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;



@Service
@Slf4j
@EnableCaching
public class PostService {
    @Autowired
    private PostJpaRepository postRepository;

    @Autowired
    private PublisherMessage publisherMessage;

    @Cacheable(value = "users",key = "#postId")
    public Post getPostsById(String postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        log.info("createAt {}", post.getId());
        return post;
    }

    public Post save(Post request) {
        Post post = null;
        try {
             post= postRepository.save(request);
            MessageType<Post> messageType = MessageType.<Post>builder()
                    .type("Post")
                    .value(post)
                    .build();

            publisherMessage.send(messageType);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

}

