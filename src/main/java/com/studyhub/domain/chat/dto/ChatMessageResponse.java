package com.studyhub.domain.chat.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatMessageResponse {

    private Long memberId;

    private String sender;

    private String message;
}
