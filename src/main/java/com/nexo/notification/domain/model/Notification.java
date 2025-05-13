package com.nexo.notification.domain.model;

import java.util.UUID;

import com.nexo.message.domain.util.MessageType;
import com.nexo.notification.domain.util.NotificationType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {
    private UUID chatId;
    private String content;
    private UUID senderId;
    private UUID receiverId;
    private String chatName;
    private MessageType messageType;
    private NotificationType type;
    private byte[] media;

}
