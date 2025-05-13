package com.nexo.message.application.dto.request;

import java.util.UUID;

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
public class MessageRequest {
    private String content;
    private UUID senderId;
    private UUID receiverId;
    private MessageType type;
    private UUID chatId;

}
