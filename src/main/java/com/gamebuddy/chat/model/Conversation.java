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
public class Conversation {
    @Id
    @Column(updatable = false, nullable = false)
    private String conversationId;

    private String user1Id;
    private String user2Id;
    private String lastMessage;
    private LocalDateTime lastMessageTime;
}
