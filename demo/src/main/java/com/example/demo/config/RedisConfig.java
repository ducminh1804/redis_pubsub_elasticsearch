package com.example.demo.config;

import com.example.demo.entity.Topic;
import com.example.demo.service.SubscriberMessage;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
public class RedisConfig {
//
//    @Autowired
//    private RedisConnectionFactory connectionFactory;

    @Autowired
    private SubscriberMessage subscriberMessage;

//    @Bean
//    ObjectMapper objectMapper() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        objectMapper.activateDefaultTyping(
//                objectMapper.getPolymorphicTypeValidator(),
//                ObjectMapper.DefaultTyping.NON_FINAL
//        );
//    return objectMapper;
//    }

    // dinh nghia template, 1 impl cua redisoperation
    //jackson khac voi genericjackson o cho: json dc tao ra k co kieu du lieu,
    // jackson: {'id':123} ---- genericjackson: ['@class':'com.entity.post',{'id':123}]
    @Bean
    @Primary
    RedisTemplate<String, String> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());//luu key dang string

//        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(objectMapper());

        template.setValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));//luu value dang json
        return template;
    }

    // cau hinh caching
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(1))// Hết hạn sau 60 giây
                .computePrefixWith(cacheName -> "redis::"+cacheName)
                .disableCachingNullValues(); // Không cache giá trị null

        return  RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(cacheConfiguration)
                .transactionAware()
                .build();
    }


    // bo dieu phoi tin nhan
    @Bean
    RedisMessageListenerContainer redisContainer(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(messageListener(), topic());
        return container;
    }


    // chua 1 implement cua interface MessageListener : o day la RedisMessageSubcriber
//    @Bean
//    MessageListenerAdapter messageListener() {
//        return new MessageListenerAdapter(new SubscriberMessage());
//    }
    @Bean
    MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(subscriberMessage);
    }

    @Bean
    ChannelTopic topic() {
        return new ChannelTopic(Topic.Post.name());
    }

}
