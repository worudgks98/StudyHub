package com.studyhub.domain.application.controller;

import com.studyhub.domain.application.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/test")
    public String test(){
        return "success";
    }
    @GetMapping("/whoami")
    public Object whoami(Authentication authentication) {

        if(authentication == null){
            return "authentication null";
        }

        return authentication.getPrincipal();
    }
}