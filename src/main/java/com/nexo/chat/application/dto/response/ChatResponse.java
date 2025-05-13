package com.nexo.chat.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatResponse {

    private UUID id;
    private String name;
    private long unreadCount;
    private String lastMessage;
    private LocalDateTime lastMessageTime;
    private boolean isRecipientOnline;
    private UUID senderId;
    private UUID receiverId;
}