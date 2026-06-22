package com.studyhub.domain.member.controller;

import com.studyhub.domain.member.dto.SignupRequest;
import com.studyhub.domain.member.entity.Member;
import com.studyhub.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequest request) {

        memberService.signup(request);

        return "회원가입 성공";
    }

    @GetMapping("/test")
    public String test() {
        return "success";
    }
}
