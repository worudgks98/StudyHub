package com.studyhub.domain.post.repository;

import com.studyhub.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByMemberId(Long memberId);

    Page<Post> findByTitleContaining(String keyword, Pageable pageable);
}
