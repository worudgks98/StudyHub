package com.studyhub.domain.post.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyPostResponse {

    private Long id;

    private String title;

    private String category;
}
