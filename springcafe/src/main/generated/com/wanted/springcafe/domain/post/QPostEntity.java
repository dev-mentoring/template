package com.wanted.springcafe.domain.post;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostEntity is a Querydsl query type for PostEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostEntity extends EntityPathBase<PostEntity> {

    private static final long serialVersionUID = -16854727L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostEntity postEntity = new QPostEntity("postEntity");

    public final com.wanted.springcafe.domain.QBaseEntity _super = new com.wanted.springcafe.domain.QBaseEntity(this);

    public final ListPath<com.wanted.springcafe.domain.comment.CommentEntity, com.wanted.springcafe.domain.comment.QCommentEntity> comments = this.<com.wanted.springcafe.domain.comment.CommentEntity, com.wanted.springcafe.domain.comment.QCommentEntity>createList("comments", com.wanted.springcafe.domain.comment.CommentEntity.class, com.wanted.springcafe.domain.comment.QCommentEntity.class, PathInits.DIRECT2);

    public final StringPath contents = createString("contents");

    public final DatePath<java.time.LocalDate> deletedAt = createDate("deletedAt", java.time.LocalDate.class);

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    //inherited
    public final DatePath<java.time.LocalDate> lastModifiedDate = _super.lastModifiedDate;

    public final NumberPath<Long> likeCount = createNumber("likeCount", Long.class);

    public final NumberPath<Long> postId = createNumber("postId", Long.class);

    //inherited
    public final DatePath<java.time.LocalDate> publishDate = _super.publishDate;

    public final StringPath title = createString("title");

    public final com.wanted.springcafe.domain.user.QUserEntity user;

    public final NumberPath<Long> viewCount = createNumber("viewCount", Long.class);

    public QPostEntity(String variable) {
        this(PostEntity.class, forVariable(variable), INITS);
    }

    public QPostEntity(Path<? extends PostEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostEntity(PathMetadata metadata, PathInits inits) {
        this(PostEntity.class, metadata, inits);
    }

    public QPostEntity(Class<? extends PostEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.wanted.springcafe.domain.user.QUserEntity(forProperty("user")) : null;
    }

}

