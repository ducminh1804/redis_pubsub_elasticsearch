package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

@Service
public class ElasticsearchService {
    @Autowired
    private ElasticsearchOperations operations;

    public Object save(Object object){
        return operations.save(object);
    }
}
