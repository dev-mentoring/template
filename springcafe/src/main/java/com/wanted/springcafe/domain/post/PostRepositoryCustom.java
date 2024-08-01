package com.wanted.springcafe.domain.post;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.springcafe.web.post.dto.response.PostListDto;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.wanted.springcafe.domain.post.QPostEntity.postEntity;

@Repository
public class PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PostRepositoryCustom(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    public List<PostListDto> search(Long postId, String title, int pageSize) {

        List<PostListDto> results = queryFactory
                .select(Projections.fields(PostListDto.class,
                        postEntity.postId.as("postId"),
                        postEntity.title,
                        postEntity.contents))
                .from(postEntity)
                .join(postEntity.user).fetchJoin()
                .where(ltPostId(postId),
                        postEntity.title.like("%" + title + "%"))
                .orderBy(postEntity.publishDate.desc())
                .limit(pageSize)
                .fetch();
        if (results.isEmpty()) {
            results = queryFactory
                    .select(Projections.fields(PostListDto.class,
                            postEntity.postId.as("postId"),
                            postEntity.title,
                            postEntity.contents))
                    .from(postEntity)
                    .join(postEntity.user).fetchJoin()
                    .orderBy(postEntity.publishDate.desc())
                    .limit(pageSize)
                    .fetch();
        }

        return results;
    }

    private BooleanExpression ltPostId(Long postId) {
        if (postId == null) {
            return null; // BooleanExpression 자리에 null이 반환되면 조건문에서 자동으로 제거된다
        }

        return postEntity.postId.lt(postId);
    }
}
