package com.studyhub.domain.post.dto;

import lombok.Getter;

@Getter
public class PostUpdateRequest {

    private String title;
    private String content;
    private String category;
    private Integer maxMember;
}
