package com.studyhub.domain.post.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostListResponse {

    private Long id;
    private String title;
    private String nickname;
    private String category;
    private boolean closed;

    private Integer maxMember;
    private Long approvedCount;
}
