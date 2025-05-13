package com.nexo.message.domain.persistence;

import java.util.UUID;

import com.nexo.chat.domain.persistence.Chat;
import com.nexo.common.domain.model.AuditableUserEntity;
import com.nexo.message.domain.util.MessageState;
import com.nexo.message.domain.util.MessageType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "messages")
@Getter
@Setter
public class Message extends AuditableUserEntity {
    /**
     * Identificador único de cada mensaje.
     * 
     * Se utiliza una estrategia de generación basada en secuencia, adecuada para
     * bases de datos como PostgreSQL.
     * - @SequenceGenerator define un generador de secuencia llamado "msg_seq",
     * asociado a una secuencia en la base de datos.
     * - allocationSize = 1 asegura que los IDs se generen de uno en uno, sin
     * reservar bloques (importante para consistencia en sistemas distribuidos).
     * - @GeneratedValue usa la estrategia SEQUENCE con el generador definido, lo
     * que permite a JPA delegar la generación del ID a la base de datos.
     */
    @Id
    @SequenceGenerator(name = "msg_seq", sequenceName = "msg_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "msg_seq")
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String content;
    @Enumerated(EnumType.STRING)
    private MessageState state;
    @Enumerated(EnumType.STRING)
    private MessageType type;
    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;
    @Column(name = "sender_id", nullable = false)
    private UUID senderId;
    @Column(name = "receiver_id", nullable = false)
    private UUID receiverId;
    private String mediaFilePath;

}
