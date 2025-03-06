package com.example.demo.entity;

import lombok.*;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostResponse {
    private String id;
    private String title;
    private String body;
    private String username;
    private String kind;
    private Instant createdAt;
    private int upVoted;
    private int downVoted;
}
