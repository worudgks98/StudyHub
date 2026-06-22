package com.studyhub.domain.post.controller;

import com.studyhub.domain.post.dto.PostCreateRequest;
import com.studyhub.domain.post.dto.PostDetailResponse;
import com.studyhub.domain.post.dto.PostListResponse;
import com.studyhub.domain.post.service.PostService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public String createPost(@RequestBody PostCreateRequest request) {

        postService.create(request);

        return "게시글 작성 성공";
    }

    @GetMapping
    public List<PostListResponse> getPost(){

        return postService.getPosts();
    }

    @GetMapping("{postId}")
    public PostDetailResponse getPost(@PathVariable("postId") Long postId){

        return postService.getPost(postId);
    }

}
