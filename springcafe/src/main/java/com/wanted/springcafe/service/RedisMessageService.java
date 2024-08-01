package com.wanted.springcafe.service;

import com.wanted.springcafe.service.redis.RedisSubscriber;
import com.wanted.springcafe.web.notification.dto.NotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisMessageService {

    private final RedisTemplate<String, NotificationDto>  redisTemplate;
    private final RedisSubscriber subscriber;
    private final RedisMessageListenerContainer container;

    public void subscribe(String channel) {
        container.addMessageListener(subscriber, ChannelTopic.of(getChannelName(channel)));
    }

    public void publish(String userId, NotificationDto notificationDto) {
        redisTemplate.convertAndSend(getChannelName(userId), notificationDto);
    }

    public void removeSubscribe(String channel) {
        container.removeMessageListener(subscriber, ChannelTopic.of(getChannelName(channel)));
    }

    private String getChannelName(String id) {
        return "topics: " + id;
    }
}
