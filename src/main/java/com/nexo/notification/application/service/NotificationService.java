package com.nexo.notification.application.service;

import javax.management.Notification;

public interface NotificationService {
    void sendNotification(String userId, Notification notification);
}