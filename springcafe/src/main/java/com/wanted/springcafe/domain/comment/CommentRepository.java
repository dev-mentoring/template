package com.wanted.springcafe.domain.comment;


import com.wanted.springcafe.domain.post.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findAllByPost_PostId(Long postId);

    @Query("select distinct c from CommentEntity c " +
            "left join c.parent " +
            "left join c.children " +
            "left join c.user " +
            "left join c.post p " +
            "where p.postId = :postId")
    List<CommentEntity> findCommentsByPostId(Long postId);


    @Query("select distinct p from PostEntity p " +
            "left join p.user u " +
            "left join p.comments c " +
            "left join c.user cu " +
            "left join c.children ch " +
            "left join ch.user chu " +
            "where p.postId = :postId and p.isDeleted = :isDeleted")
    PostEntity getPost(@Param("postId") Long postId, @Param("isDeleted") boolean isDeleted);
}
