package com.nexo.message.application.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.nexo.message.domain.util.MessageState;
import com.nexo.message.domain.util.MessageType;

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
public class MessageResponse {
    private Long id;
    private String content;
    private MessageType type;
    private MessageState state;
    private UUID senderId;
    private UUID receiverId;
    private LocalDateTime createdAt;
    private byte[] media;
}
