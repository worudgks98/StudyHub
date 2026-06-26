package com.studyhub.domain.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatSocketMessage {

    private Long roomId;

    private Long memberId;

    private String sender;

    private String message;
}
