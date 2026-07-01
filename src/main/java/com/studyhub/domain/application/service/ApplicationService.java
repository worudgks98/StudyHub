package com.studyhub.domain.application.service;

import com.studyhub.domain.application.dto.ApplicationResponse;
import com.studyhub.domain.application.dto.MyApplicationResponse;
import com.studyhub.domain.application.entity.Application;
import com.studyhub.domain.application.repository.ApplicationRepository;
import com.studyhub.domain.chat.service.ChatService;
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
    private final ChatService chatService;

    @Transactional
    public void apply(Long memberId, Long postId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        if(applicationRepository.existsByMemberIdAndPostId(memberId, postId)) {
            throw new IllegalArgumentException("이미 지원한 게시물입니다.");
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

        if(post.isClosed()){
            throw new IllegalArgumentException("모집이 마감되었습니다.");
        }

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
                                .applicationId(
                                        application.getId())
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

        chatService.createChatRoom(application.getPost());

        chatService.addMemberToChatRoom(application.getPost(),application.getMember());

        long approvedCount =
                applicationRepository
                        .countByPostIdAndStatus(
                                application.getPost().getId(),
                                "APPROVED"
                        );

        System.out.println("승인수 =" + approvedCount);
        System.out.println("정원 =" + application.getPost().getMaxMember());

        if(approvedCount >=
                application.getPost().getMaxMember()) {

            System.out.println("모집 마감 실행");

            application.getPost().close();
        }

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

    @Transactional
    public void cancel(Long applicationId,Long memberId){

        Application application =
                applicationRepository.findById(applicationId)
                        .orElseThrow(() -> new IllegalArgumentException("지원 내역 없음"));

        if(!application.getMember()
                .getId().equals(memberId)) {

            throw new IllegalArgumentException("본인 지원만 취소가능합니다.");
        }

        applicationRepository.delete(application);

    }

    @Transactional(readOnly = true)
    public List<MyApplicationResponse> getMyApplications(
            Long memberId) {

        return applicationRepository.findByMemberId(memberId)
                .stream()
                .map(application ->
                        MyApplicationResponse.builder()
                                .applicationId(
                                        application.getId())
                                .postId(
                                        application.getPost().getId()
                                )
                                .title(
                                        application.getPost().getTitle()
                                )
                                .status(
                                        application.getStatus()
                                )
                                .build()
                )
                .toList();
    }

    @Transactional(readOnly = true)
    public boolean checkApplication(Long memberId, Long postId) {

        return applicationRepository.existsByMemberIdAndPostId(
                memberId,
                postId
        );
    }
}