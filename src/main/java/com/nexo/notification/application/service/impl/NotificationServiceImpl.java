package com.nexo.notification.application.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.nexo.notification.application.service.NotificationService;
import com.nexo.notification.domain.model.Notification;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    public NotificationServiceImpl(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public void sendNotification(UUID userId, Notification notification) {
        simpMessagingTemplate.convertAndSendToUser(
                userId.toString(),
                "/chat",
                notification);
    }

}
