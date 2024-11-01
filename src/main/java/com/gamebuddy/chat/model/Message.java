package com.gamebuddy.chat.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
    @Column(updatable = false, nullable = false)
    private String messageId;

    private String conversationId;
    private String senderId;
    private String recipientId;
    private String content;
    private LocalDateTime timestamp;
    private String status;
}
