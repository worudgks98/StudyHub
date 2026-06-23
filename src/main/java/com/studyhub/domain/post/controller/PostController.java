package com.studyhub.domain.post.controller;

import com.studyhub.domain.post.dto.PostCreateRequest;
import com.studyhub.domain.post.dto.PostDetailResponse;
import com.studyhub.domain.post.dto.PostListResponse;
import com.studyhub.domain.post.dto.PostUpdateRequest;
import com.studyhub.domain.post.service.PostService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public String createPost(
            @RequestBody PostCreateRequest request,
            Authentication authentication){

        Long memberId = (Long) authentication.getPrincipal();

        postService.create(request, memberId);

        return "게시글 작성 완료";
    }

    @GetMapping
    public List<PostListResponse> getPost(){

        return postService.getPosts();
    }

    @GetMapping("{postId}")
    public PostDetailResponse getPost(@PathVariable("postId") Long postId){

        return postService.getPost(postId);
    }

    @PutMapping("/{postId}")
    public String updatePost(@PathVariable Long postId,
                             @RequestBody PostUpdateRequest request,
                             Authentication authentication){

        Long memberId =
                (Long) authentication.getPrincipal();

        postService.update(postId,memberId,request);

        return "게시글 수정 완료";
    }

}
