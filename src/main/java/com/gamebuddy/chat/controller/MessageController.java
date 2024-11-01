package com.gamebuddy.chat.controller;

import com.gamebuddy.chat.dto.MessageCreateDTO;
import com.gamebuddy.chat.dto.MessageViewDTO;
import com.gamebuddy.chat.exception.results.DataResult;
import com.gamebuddy.chat.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<DataResult<MessageViewDTO>> sendMessage(@RequestBody MessageCreateDTO messageCreateDTO) {
        return new ResponseEntity<>(messageService.sendMessage(messageCreateDTO), HttpStatus.OK);
    }

    @GetMapping("/{conversationId}")
    public ResponseEntity<DataResult<List<MessageViewDTO>>> getMessagesByConversation(@PathVariable String conversationId) {
        return new ResponseEntity<>(messageService.getMessagesByConversation(conversationId), HttpStatus.OK);
    }
}