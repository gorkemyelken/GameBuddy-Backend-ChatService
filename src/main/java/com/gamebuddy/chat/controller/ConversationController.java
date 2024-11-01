package com.gamebuddy.chat.controller;

import com.gamebuddy.chat.dto.ConversationCreateDTO;
import com.gamebuddy.chat.dto.ConversationViewDTO;
import com.gamebuddy.chat.exception.results.DataResult;
import com.gamebuddy.chat.service.ConversationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {

    private final ConversationService conversationService;

    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @PostMapping
    public ResponseEntity<DataResult<ConversationViewDTO>> startConversation(@RequestBody ConversationCreateDTO conversationCreateDTO) {
        return new ResponseEntity<>(conversationService.startConversation(conversationCreateDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<DataResult<List<ConversationViewDTO>>> getConversationsByUser(@PathVariable String userId) {
        return new ResponseEntity<>(conversationService.getConversationsByUser(userId), HttpStatus.OK);
    }
}