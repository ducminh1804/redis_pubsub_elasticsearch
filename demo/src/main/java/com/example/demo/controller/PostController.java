package com.example.demo.controller;

import com.example.demo.entity.Post;
import com.example.demo.service.PostService;
import com.example.demo.service.PublisherMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/posts")
@Slf4j
public class PostController {
    @Autowired
    private PostService postService;
@Autowired
private PublisherMessage pubSubRedis;


    @GetMapping("/{postId}")
    public Post getPostById(@PathVariable String postId) {
        Post post = postService.getPostsById(postId);
        return post;
    }

    @PostMapping
    public Post save(@RequestBody Post request) {
        Post post = postService.save(request);
        return post;
    }


}
