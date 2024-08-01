package com.wanted.springcafe.service;

import com.wanted.springcafe.domain.notification.NotificationEntity;
import com.wanted.springcafe.domain.notification.NotificationRepository;
import com.wanted.springcafe.web.notification.dto.NotificationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationService {


    private final NotificationRepository notificationRepository;
    private final SseEmitterService sseEmitterService;
    private final RedisMessageService redisMessageService;

    public List<NotificationDto> getAllNotifiactions(Long userId) {
        List<NotificationEntity> list = notificationRepository.findAllByUser_UserId(userId);
        return list
                .stream()
                .map(i -> new NotificationDto(
                        i.getNotificationId(),
                        i.getUser().getUserId(),
                        i.getUser().getUsername(),
                        i.getMessage(),
                        i.isRead(),
                        i.getPublishDate()))
                .toList();
    }

    @Transactional
    public SseEmitter subscribe(String memberKey) throws IOException {
        SseEmitter sseEmitter = sseEmitterService.createEmitter(memberKey);
        sseEmitterService.send("더미데이터", memberKey, sseEmitter);
        redisMessageService.subscribe(memberKey);
        sseEmitter.onTimeout(sseEmitter::complete);
        sseEmitter.onError((e) -> sseEmitter.complete());
        sseEmitter.onCompletion(() -> {
            sseEmitterService.deleteEmitter(memberKey);
            redisMessageService.removeSubscribe(memberKey); // 구독한 채널 삭제
        });
        return sseEmitter;
    }

    @Transactional
    public void send(NotificationEntity notification) {

        NotificationEntity notificationEntity = notificationRepository.save(notification);
        String id = String.valueOf(notification.getUserId());

        NotificationDto notificationDto = new NotificationDto(
                notificationEntity.getNotificationId(),
                notificationEntity.getUserId(),
                notificationEntity.getUser().getUsername(),
                notificationEntity.getMessage(),
                notificationEntity.isRead(),
                notificationEntity.getPublishDate());

        redisMessageService.publish(id,notificationDto);
    }
}
