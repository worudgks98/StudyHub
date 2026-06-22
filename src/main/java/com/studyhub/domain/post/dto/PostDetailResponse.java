package com.studyhub.domain.post.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostDetailResponse {

    private Long id;

    private String title;

    private String content;

    private String nickname;

    private String category;

    private Integer maxMember;
}
