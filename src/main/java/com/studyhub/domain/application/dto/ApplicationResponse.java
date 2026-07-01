package com.studyhub.domain.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApplicationResponse {

    private Long applicationId;
    private Long memberId;
    private String nickname;
    private String status;
}
