package com.studyhub.domain.chat.controller;

import com.studyhub.domain.chat.dto.ChatSocketMessage;
import com.studyhub.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatService chatService;

    @MessageMapping("/chat")
    public void sendMessage(
            ChatSocketMessage message
    ) {

        chatService.sendMessage(
                message.getRoomId(),
                message.getMemberId(),
                message.getMessage()
        );

        messagingTemplate.convertAndSend(
                "/topic/chat/" + message.getRoomId(),
                message
        );
    }
}