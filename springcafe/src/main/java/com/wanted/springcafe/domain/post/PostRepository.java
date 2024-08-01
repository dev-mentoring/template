package com.wanted.springcafe.domain.post;

import org.hibernate.annotations.BatchSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    @Query("SELECT p.postId FROM PostEntity p WHERE p.deletedAt = :deletedAt")
    List<Long> findIdByDeletedAt(LocalDate deletedAt);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("DELETE FROM PostEntity p WHERE p.postId in :ids")
    void deleteAllByInQuery(@Param("ids") List<Long> ids);

    @Query("select distinct p from PostEntity p " +
            "left join p.user u " +
            "left join p.comments c " +
            "left join c.user cu " +
            "left join c.children ch " +
            "left join ch.user chu " +
//            "left join " + //이미지파일까지 같이
            "where p.postId = :postId and p.isDeleted = :isDeleted")
    PostEntity getPost(@Param("postId") Long postId, @Param("isDeleted") boolean isDeleted);

    @Query("select distinct p from PostEntity p " +
            "left join p.user u " +
            "left join p.comments c " +
            "left join c.user cu " +
            "left join c.children ch " +
            "left join ch.user chu " +
            "where p.isDeleted = :isDeleted")
    List<PostEntity> findAllByIsDeleted(@Param("isDeleted") boolean isDeleted);
}
