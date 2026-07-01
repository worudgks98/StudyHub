package com.studyhub.domain.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyApplicationResponse {

    private Long applicationId;
    private Long postId;
    private String title;
    private String status;
}
