package com.wanted.springcafe.web.notification;

import com.wanted.springcafe.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

@RequiredArgsConstructor
@RequestMapping("/notification")
@RestController
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping(value = "/connect", produces = TEXT_EVENT_STREAM_VALUE)
    public String subscribe(
            @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId
            ){
//       notificationService.subscribe();
       return "연결이 완료됐습니다.";

    }

}
