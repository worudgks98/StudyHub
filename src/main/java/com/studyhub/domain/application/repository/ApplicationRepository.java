package com.studyhub.domain.application.repository;

import com.studyhub.domain.application.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application,Long> {

    boolean existsByMemberIdAndPostId(Long memberId, Long postId);


}
