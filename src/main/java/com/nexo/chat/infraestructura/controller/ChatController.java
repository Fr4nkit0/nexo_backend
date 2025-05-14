package com.nexo.chat.infraestructura.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nexo.chat.application.dto.request.CreateChatRequest;
import com.nexo.chat.application.dto.response.ChatResponse;
import com.nexo.common.infraestructura.dto.StringResponse;

public interface ChatController {
    @PostMapping
    ResponseEntity<StringResponse> createChat(@RequestBody CreateChatRequest createChatRequest);

    @GetMapping
    ResponseEntity<List<ChatResponse>> findChatsByReceiver(Authentication authentication);
}
