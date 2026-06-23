package com.studyhub.domain.member.service;

import com.studyhub.domain.member.dto.LoginRequest;
import com.studyhub.domain.member.dto.SignupRequest;
import com.studyhub.domain.member.entity.Member;
import com.studyhub.domain.member.repository.MemberRepository;
import com.studyhub.global.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void signup(SignupRequest request) {

        if(memberRepository.existsByEmail(request.getEmail())){
            throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
        }

        if(memberRepository.existsByNickname(request.getNickname())){
            throw new IllegalArgumentException("이미 사용중인 닉네임입니다.");
        }

        Member member = Member.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .build();

        memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public String login(LoginRequest request) {

        Member member = memberRepository
                .findByEmail(request.getEmail())
                .orElseThrow(()->
                        new IllegalArgumentException("회원 없음"));

        if(!passwordEncoder.matches(
                request.getPassword(),
                member.getPassword())) {

            throw new IllegalArgumentException("비밀번호 불일치");
        }

        return jwtUtil.createToken(member.getId());

    }
}
