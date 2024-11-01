package com.gamebuddy.chat.service;

import com.gamebuddy.chat.dto.ConversationCreateDTO;
import com.gamebuddy.chat.dto.ConversationViewDTO;
import com.gamebuddy.chat.exception.results.DataResult;
import com.gamebuddy.chat.exception.results.SuccessDataResult;
import com.gamebuddy.chat.model.Conversation;
import com.gamebuddy.chat.repository.ConversationRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ConversationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConversationService.class);

    private final ConversationRepository conversationRepository;

    private final ModelMapper modelMapper;

    public ConversationService(ConversationRepository conversationRepository, ModelMapper modelMapper) {
        this.conversationRepository = conversationRepository;
        this.modelMapper = modelMapper;
    }

    public DataResult<ConversationViewDTO> startConversation(ConversationCreateDTO conversationCreateDTO) {
        Conversation conversation = modelMapper.map(conversationCreateDTO, Conversation.class);
        conversation.setConversationId(UUID.randomUUID().toString());
        conversation.setLastMessage("");
        conversation.setLastMessageTime(null);
        conversationRepository.save(conversation);

        ConversationViewDTO conversationViewDTO = modelMapper.map(conversation, ConversationViewDTO.class);
        return new SuccessDataResult<>(conversationViewDTO, "Conversation started successfully.");
    }

    public DataResult<List<ConversationViewDTO>> getConversationsByUser(String userId) {
        List<Conversation> conversations = conversationRepository.findByUser1IdOrUser2Id(userId, userId);
        List<ConversationViewDTO> conversationViewDTOs = conversations.stream()
                .map(conversation -> modelMapper.map(conversation, ConversationViewDTO.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<>(conversationViewDTOs, "Reviews found successfully.");
    }
}
