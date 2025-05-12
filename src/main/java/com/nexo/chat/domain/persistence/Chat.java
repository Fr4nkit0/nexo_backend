package com.nexo.chat.domain.persistence;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.nexo.common.domain.model.AuditableDateEntity;
import com.nexo.message.domain.persistence.Message;
import com.nexo.message.domain.util.MessageState;
import com.nexo.message.domain.util.MessageType;
import com.nexo.user.domain.persistence.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "chats")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Chat extends AuditableDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToOne
    @Column(name = "sender_id", nullable = false)
    private User sender;
    @ManyToOne
    @Column(name = "recipient_id", nullable = false)
    private User recipient;
    @OneToMany(mappedBy = "chat", fetch = FetchType.EAGER)
    @OrderBy("createdAt DESC")
    private List<Message> messages;

    @Transient
    public String getChatName(String senderId) {
        if (recipient.getId().equals(UUID.fromString(senderId))) {
            return sender.getFirstName() + " " + sender.getLastName();
        }
        return recipient.getFirstName() + " " + recipient.getLastName();
    }

    @Transient
    public String getTargetChatName(String senderId) {
        if (sender.getId().equals(UUID.fromString(senderId))) {
            return sender.getFirstName() + " " + sender.getLastName();
        }
        return recipient.getFirstName() + " " + recipient.getLastName();
    }

    @Transient
    public long getUnreadMessages(String senderId) {
        return this.messages
                .stream()
                .filter(m -> m.getSenderId().equals(senderId))
                .filter(m -> MessageState.SENT == m.getState())
                .count();
    }

    @Transient
    public String getLastMessage() {
        if (messages != null && !messages.isEmpty()) {
            if (messages.get(0).getType() != MessageType.TEXT) {
                return "Attachment";
            }
            return messages.get(0).getContent();
        }
        return null; // No messages available
    }

    @Transient
    public LocalDateTime getLastMessageTime() {
        if (messages != null && !messages.isEmpty()) {
            return messages.get(0).getCreatedDate();
        }
        return null;
    }
}
