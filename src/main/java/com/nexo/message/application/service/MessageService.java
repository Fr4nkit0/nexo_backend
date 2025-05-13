package com.nexo.message.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import com.nexo.message.application.dto.request.MessageRequest;
import com.nexo.message.application.dto.response.MessageResponse;

public interface MessageService {

    void saveMessage(MessageRequest messageRequest);

    void setMessagesToSeen(UUID chatId, Authentication authentication);

    void uploadMediaMessage(UUID chatId, MultipartFile file, Authentication authentication);

    List<MessageResponse> findChatMessages(UUID chatId);
}
