package com.studyhub.domain.post.service;

import com.studyhub.domain.application.repository.ApplicationRepository;
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
    private final ApplicationRepository applicationRepository;
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
    public Page<PostListResponse> getPosts(
            String keyword,
            String category,
            org.springframework.data.domain.Pageable pageable) {

        Page<Post> posts;

        if ((keyword == null || keyword.isBlank())
                && (category == null || category.isBlank())) {

            posts = postRepository.findAll(pageable);

        } else if (category == null || category.isBlank()) {

            posts = postRepository.findByTitleContaining(
                    keyword,
                    pageable
            );

        } else {

            posts = postRepository.findByCategoryAndTitleContaining(
                    category,
                    keyword,
                    pageable
            );

        }

        return posts.map(post -> {

            Long approvedCount =
                    applicationRepository.countByPostIdAndStatus(
                            post.getId(),
                            "APPROVED"
                    );

            return PostListResponse.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .nickname(post.getMember().getNickname())
                    .category(post.getCategory())
                    .closed(post.isClosed())
                    .maxMember(post.getMaxMember())
                    .approvedCount(approvedCount)
                    .build();

        });
    }

    @Transactional(readOnly = true)
    public PostDetailResponse getPost(Long postId){

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

        Long approvedCount =
                applicationRepository.countByPostIdAndStatus(
                        postId,
                        "APPROVED"
                );

        return PostDetailResponse.builder()
                .id(post.getId())
                .memberId(post.getMember().getId())
                .title(post.getTitle())
                .content(post.getContent())
                .nickname(post.getMember().getNickname())
                .category(post.getCategory())
                .maxMember(post.getMaxMember())
                .approvedCount(approvedCount)
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
    public List<MyPostResponse> getMyPosts(Long memberId) {

        List<Post> posts = postRepository.findByMemberId(memberId);

        return posts.stream()
                .map(post -> {

                    Long currentMember =
                            applicationRepository.countByPostIdAndStatus(
                                    post.getId(),
                                    "APPROVED"
                            );

                    return MyPostResponse.builder()
                            .id(post.getId())
                            .title(post.getTitle())
                            .category(post.getCategory())
                            .maxMember(post.getMaxMember())
                            .currentMember(currentMember)
                            .closed(post.isClosed())
                            .createdAt(post.getCreatedAt())
                            .build();
                })
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
