package com.nexo.notification.application.service;

import java.util.UUID;

import com.nexo.notification.domain.model.Notification;

public interface NotificationService {
    void sendNotification(UUID userId, Notification notification);
}