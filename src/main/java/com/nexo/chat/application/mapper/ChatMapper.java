package com.nexo.chat.application.mapper;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.nexo.chat.application.dto.response.ChatResponse;
import com.nexo.chat.domain.persistence.Chat;

@Service
public class ChatMapper {

    public ChatResponse toChatResponse(Chat chat, UUID senderId) {
        return ChatResponse.builder()
                .id(chat.getId())
                .name(chat.getChatName(senderId))
                .unreadCount(chat.getUnreadMessages(senderId))
                .lastMessage(chat.getLastMessage())
                .lastMessageTime(chat.getLastMessageTime())
                .isRecipientOnline(chat.getRecipient().isUserOnline())
                .senderId(chat.getSender().getId())
                .receiverId(chat.getRecipient().getId())
                .build();
    }
}
