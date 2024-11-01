package com.gamebuddy.chat.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class MessageViewDTO {
    private String messageId;
    private String conversationId;
    private String senderId;
    private String recipientId;
    private String content;
    private LocalDateTime timestamp;
    private String status;
}
