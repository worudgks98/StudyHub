package com.studyhub.domain.chat.repository;

import com.studyhub.domain.chat.entity.ChatMember;
import org.springframework.data.repository.CrudRepository;

public interface ChatMemberRepository extends CrudRepository<ChatMember, Long> {
}
