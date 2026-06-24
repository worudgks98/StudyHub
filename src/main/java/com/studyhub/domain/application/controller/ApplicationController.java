package com.studyhub.domain.application.controller;

import com.studyhub.domain.application.dto.ApplicationResponse;
import com.studyhub.domain.application.service.ApplicationService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping("/{postId}")
    public String apply(
            @PathVariable Long postId,
            Authentication authentication) {

        Long memberId =
                (Long) authentication.getPrincipal();

        applicationService.apply(memberId, postId);

        return "지원 완료";
    }

    @GetMapping("/post/{postId}")
    public List<ApplicationResponse> getApplicationsByPostId(@PathVariable Long postId) {

        return applicationService.getApplications(postId);
    }

    @PatchMapping("/{applicationId}/approve")
    public String approve(@PathVariable Long applicationId,Authentication authentication) {

        Long memberId = (Long) authentication.getPrincipal();

        applicationService.approve(applicationId,memberId);

        return "승인 완료";
    }

    @PatchMapping("/{applicationId}/reject")
    public String reject(@PathVariable Long applicationId,Authentication authentication) {

        Long memberId = (Long) authentication.getPrincipal();

        applicationService.reject(applicationId,memberId);

        return "거절 완료";
    }
}