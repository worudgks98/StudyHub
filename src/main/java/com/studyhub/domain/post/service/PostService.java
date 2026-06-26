package com.studyhub.domain.post.service;

import com.studyhub.domain.member.entity.Member;
import com.studyhub.domain.member.repository.MemberRepository;
import com.studyhub.domain.post.dto.PostCreateRequest;
import com.studyhub.domain.post.dto.PostDetailResponse;
import com.studyhub.domain.post.dto.PostListResponse;
import com.studyhub.domain.post.dto.PostUpdateRequest;
import com.studyhub.domain.post.entity.MyPostResponse;
import com.studyhub.domain.post.entity.Post;
import com.studyhub.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    
    public void create(PostCreateRequest request,Long memberId){
        
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));
        
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .category(request.getCategory())
                .maxMember(request.getMaxMember())
                .closed(false)
                .member(member)
                .createdAt(LocalDateTime.now())
                .build();
        
        postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public List<PostListResponse> getPosts(){

        return postRepository.findAll()
                .stream()
                .map(post -> PostListResponse.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .nickname(post.getMember().getNickname())
                        .category(post.getCategory())
                        .build())
                .toList();

    }

    @Transactional(readOnly = true)
    public PostDetailResponse getPost(Long postId){

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

        return PostDetailResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .nickname(post.getMember().getNickname())
                .category(post.getCategory())
                .maxMember(post.getMaxMember())
                .build();
    }

    @Transactional
    public void update(Long postId, Long memberId, PostUpdateRequest request){

        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new IllegalArgumentException("게시글 없음"));

        if(!post.getMember().getId().equals(memberId)){
            throw new IllegalArgumentException("작성자만 수정 가능");
        }

        post.update(
                request.getTitle(),
                request.getContent(),
                request.getCategory(),
                request.getMaxMember()
        );
    }
    @Transactional
    public void delete(Long postId,Long memberId){

        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new IllegalArgumentException("게시글 없음"));

        if(!post.getMember().getId().equals(memberId)){
            throw new IllegalArgumentException("작성자만 삭제 가능");
        }

        postRepository.delete(post);
    }

    @Transactional(readOnly = true)
    public List<MyPostResponse> getMyPosts(Long memberId){

        return postRepository.findByMemberId(memberId)
                .stream()
                .map(post ->
                        MyPostResponse.builder()
                                .id(post.getId())
                                .title(post.getTitle())
                                .category(post.getCategory())
                                .build()
                )
                .toList();
    }

    @Transactional
    public void closePost(Long postId,Long memberId){

        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new IllegalArgumentException("게시글 없음"));

        if(!post.getMember().getId().equals(memberId)){
            throw new IllegalArgumentException("작성자만 마감 가능합니다.");
        }

        post.close();
    }

    @Transactional(readOnly = true)
    public Page<PostListResponse> searchPosts(
            String keyword,
            Pageable pageable) {

        Page<Post> posts =
                postRepository.findByTitleContaining(
                        keyword,
                        pageable
                );

        return posts.map(post ->
                PostListResponse.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .nickname(post.getMember().getNickname())
                        .category(post.getCategory())
                        .build()
        );
    }


}
