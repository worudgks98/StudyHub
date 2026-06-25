package com.studyhub.domain.chat.controller;

import com.studyhub.domain.chat.dto.ChatMessageRequest;
import com.studyhub.domain.chat.dto.ChatMessageResponse;
import com.studyhub.domain.chat.entity.ChatMessage;
import com.studyhub.domain.chat.service.ChatService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/{roomId}/messages")
    public String sendMessage(
            @PathVariable Long roomId,
            @RequestBody ChatMessageRequest request,
            Authentication authentication
    ) {

        Long memberId =
                (Long) authentication.getPrincipal();

        chatService.sendMessage(
                roomId,
                memberId,
                request.getMessage()
        );

        return "메시지 전송 완료";
    }

    @GetMapping("/{roomId}/messages")
    public List<ChatMessageResponse> getMessages(@PathVariable Long roomId) {

        return chatService.getMessages(roomId);
    }
}