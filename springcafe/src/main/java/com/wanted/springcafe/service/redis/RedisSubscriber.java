package com.wanted.springcafe.service.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.springcafe.service.SseEmitterService;
import com.wanted.springcafe.web.notification.dto.NotificationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class RedisSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;
    private final SseEmitterService sseEmitterService;

    @Override
    public void onMessage(Message message, byte[] pattern) {

        try {
            NotificationDto notificationDto = objectMapper.readValue(message.getBody(),
                    NotificationDto.class);
            sseEmitterService.sendNotificationToClient(notificationDto);
        } catch (IOException e) {
            log.error("IOException is occurred. ", e);
        }
    }
}
