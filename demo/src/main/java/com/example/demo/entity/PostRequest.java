package com.example.demo.entity;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostRequest {
    private String title;
    private String body;
    private String username;
}
