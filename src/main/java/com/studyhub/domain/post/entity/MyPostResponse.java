package com.studyhub.domain.post.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MyPostResponse {

    private Long id;

    private String title;

    private String category;

    private Integer maxMember;

    private Long currentMember;

    private boolean closed;

    private LocalDateTime createdAt;
}