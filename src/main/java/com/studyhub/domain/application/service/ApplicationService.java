package com.studyhub.domain.application.service;

import com.studyhub.domain.application.dto.ApplicationResponse;
import com.studyhub.domain.application.entity.Application;
import com.studyhub.domain.application.repository.ApplicationRepository;
import com.studyhub.domain.member.entity.Member;
import com.studyhub.domain.member.repository.MemberRepository;
import com.studyhub.domain.post.entity.Post;
import com.studyhub.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Transactional
    public void apply(Long memberId, Long postId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        if(applicationRepository.existsByMemberIdAndPostId(memberId, postId)) {
            throw new IllegalArgumentException("이미 지원한 게시물입니다.");
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

        Application application = Application.builder()
                .member(member)
                .post(post)
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .build();

        applicationRepository.save(application);
    }

    @Transactional(readOnly = true)
    public List<ApplicationResponse> getApplications(Long postId) {

        return applicationRepository.findByPostId(postId)
                .stream()
                .map(application ->
                        ApplicationResponse.builder()
                                .memberId(
                                        application.getMember().getId()
                                )
                                .nickname(
                                        application.getMember().getNickname()
                                )
                                .status(
                                        application.getStatus()
                                )
                                .build()
                )
                .toList();
    }

    @Transactional
    public void approve(Long applicationId,Long memberId) {

        System.out.println("approve 진입");
        System.out.println("memberId = " + memberId);

        Application application =
                applicationRepository.findById(applicationId)
                        .orElseThrow(()-> new IllegalArgumentException("지원 내역 없음"));

        if(!application.getPost()
                .getMember()
                .getId()
                .equals(memberId)) {

            throw new IllegalArgumentException("작성자만 승인 가능합니다.");
        }

        application.approve();

    }

    @Transactional
    public void reject(Long applicationId,Long memberId) {

        Application application =
                applicationRepository.findById(applicationId)
                        .orElseThrow(()-> new IllegalArgumentException("지원 내역 없음"));

        if(!application.getPost()
                .getMember()
                .getId()
                .equals(memberId)) {

            throw new IllegalArgumentException("작성자만 거절 가능합니다.");
        }

        application.reject();

    }
}