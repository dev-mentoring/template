package com.wanted.springcafe.service;

import com.wanted.springcafe.web.notification.dto.NotificationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class SseEmitterService {

    private static final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter createEmitter(String emitterKey) {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        emitters.put(emitterKey, sseEmitter);
        return sseEmitter;
    }

    public void deleteEmitter(String emitterKey) {
        emitters.remove(emitterKey);
    }

    public void sendNotificationToClient(NotificationDto notificationDto) {
        String emitterKey = String.valueOf(notificationDto.getUserId());
        SseEmitter sseEmitter = emitters.get(emitterKey);
        send(notificationDto, emitterKey, sseEmitter);
    }

    public void send(Object data, String emitterKey, SseEmitter sseEmitter) {
        try {
            sseEmitter.send(SseEmitter.event()
                    .id(emitterKey)
                    .data(data, MediaType.APPLICATION_JSON));

            log.info("메시지 전송");
        } catch (IOException | IllegalStateException e) {
            log.error("IOException | IllegalStateException is occurred. ", e);
            emitters.remove(emitterKey);
        }
    }
}
