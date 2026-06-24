package com.studyhub.domain.application.repository;

import com.studyhub.domain.application.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application,Long> {

    boolean existsByMemberIdAndPostId(Long memberId, Long postId);

    List<Application> findByPostId(Long postId);

}
