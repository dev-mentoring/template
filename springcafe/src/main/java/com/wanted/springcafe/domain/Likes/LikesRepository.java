package com.wanted.springcafe.domain.Likes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<LikesEntity,Long> {

    @Transactional
    Optional<LikesEntity> findByPost_PostIdAndUser_UserId(Long postId, Long userId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("DELETE FROM LikesEntity l WHERE l.post.postId = :postId and l.user.userId = :userId")
    void deleteLike(@Param("postId") Long postId, @Param("userId") Long userId);
}
