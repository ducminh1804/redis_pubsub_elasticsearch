package com.example.demo.repository.elasticsearch;

import com.example.demo.entity.Post;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

public interface PostEsRepository extends ElasticsearchRepository<Post,String> {
}
