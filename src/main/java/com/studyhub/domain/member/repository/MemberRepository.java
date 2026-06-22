package com.studyhub.domain.member.repository;

import com.studyhub.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);
}
