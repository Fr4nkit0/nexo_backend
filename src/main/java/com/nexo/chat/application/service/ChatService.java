package com.nexo.chat.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.Authentication;

import com.nexo.chat.application.dto.request.CreateChatRequest;
import com.nexo.chat.application.dto.response.ChatResponse;

public interface ChatService {

    List<ChatResponse> findAllChatsByReceiverId(Authentication currentUser);

    UUID createChat(CreateChatRequest createChatRequest);

}
