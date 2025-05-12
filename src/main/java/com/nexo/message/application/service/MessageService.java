package com.nexo.message.application.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import com.nexo.message.application.dto.request.MessageRequest;
import com.nexo.message.application.dto.response.MessageResponse;

public interface MessageService {

    void saveMessage(MessageRequest messageRequest);

    void setMessagesToSeen(String chatId, Authentication authentication);

    void uploadMediaMessage(String chatId, MultipartFile file, Authentication authentication);

    List<MessageResponse> findChatMessages(String chatId);
}
