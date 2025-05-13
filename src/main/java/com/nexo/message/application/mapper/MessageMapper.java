package com.nexo.message.application.mapper;

import org.springframework.stereotype.Service;

import com.nexo.chat.domain.persistence.Chat;
import com.nexo.file.application.util.FileUtils;
import com.nexo.message.application.dto.request.MessageRequest;
import com.nexo.message.application.dto.response.MessageResponse;
import com.nexo.message.domain.persistence.Message;
import com.nexo.message.domain.util.MessageState;

@Service
public class MessageMapper {
    public MessageResponse toMessageResponse(Message message) {
        return MessageResponse.builder()
                .id(message.getId())
                .content(message.getContent())
                .senderId(message.getSenderId())
                .receiverId(message.getReceiverId())
                .type(message.getType())
                .state(message.getState())
                .createdAt(message.getCreatedDate())
                .media(FileUtils.readFileFromLocation(message.getMediaFilePath()))
                .build();
    }

    public Message toMessage(MessageRequest messageRequest, Chat chat) {
        return Message.builder()
                .type(messageRequest.getType())
                .content(messageRequest.getContent())
                .chat(chat)
                .senderId(messageRequest.getSenderId())
                .receiverId(messageRequest.getReceiverId())
                .state(MessageState.SEEN)
                .build();
    }
}
