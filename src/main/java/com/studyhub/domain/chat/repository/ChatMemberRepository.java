package com.studyhub.domain.chat.repository;

import com.studyhub.domain.chat.entity.ChatMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ChatMemberRepository extends JpaRepository<ChatMember, Long> {

    boolean existsByChatRoomIdAndMemberId(Long roomId, Long memberId);

    List<ChatMember> findByMemberId(Long memberId);
}
