package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "posts")
@Builder
@Getter
@Setter
@Document(indexName = "posts")
public class Post  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    @Field(type = FieldType.Text)
    private String id;

    @Column(name = "title")
    @Field(type = FieldType.Text)
    private String title;

    @Column(name = "body")
    @Field(type = FieldType.Text)
    private String body;

    @Column(name = "kind")
    private String kind;

    @Column(name = "vote_up")
    private int upVoted;

    @Column(name = "vote_down")
    private int downVoted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", kind='" + kind + '\'' +
                ", upVoted=" + upVoted +
                ", downVoted=" + downVoted +
                '}';
    }
}
