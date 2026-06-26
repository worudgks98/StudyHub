package com.studyhub.domain.chat.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatRoomResponse {

    private Long roomId;

    private String postTitle;
}
