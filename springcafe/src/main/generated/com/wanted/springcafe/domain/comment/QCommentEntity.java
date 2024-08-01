package com.wanted.springcafe.domain.comment;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommentEntity is a Querydsl query type for CommentEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommentEntity extends EntityPathBase<CommentEntity> {

    private static final long serialVersionUID = -523323877L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommentEntity commentEntity = new QCommentEntity("commentEntity");

    public final com.wanted.springcafe.domain.QBaseEntity _super = new com.wanted.springcafe.domain.QBaseEntity(this);

    public final ListPath<CommentEntity, QCommentEntity> children = this.<CommentEntity, QCommentEntity>createList("children", CommentEntity.class, QCommentEntity.class, PathInits.DIRECT2);

    public final NumberPath<Long> commentId = createNumber("commentId", Long.class);

    public final StringPath content = createString("content");

    public final DatePath<java.time.LocalDate> deletedAt = createDate("deletedAt", java.time.LocalDate.class);

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    //inherited
    public final DatePath<java.time.LocalDate> lastModifiedDate = _super.lastModifiedDate;

    public final QCommentEntity parent;

    public final com.wanted.springcafe.domain.post.QPostEntity post;

    //inherited
    public final DatePath<java.time.LocalDate> publishDate = _super.publishDate;

    public final com.wanted.springcafe.domain.user.QUserEntity user;

    public QCommentEntity(String variable) {
        this(CommentEntity.class, forVariable(variable), INITS);
    }

    public QCommentEntity(Path<? extends CommentEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommentEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommentEntity(PathMetadata metadata, PathInits inits) {
        this(CommentEntity.class, metadata, inits);
    }

    public QCommentEntity(Class<? extends CommentEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.parent = inits.isInitialized("parent") ? new QCommentEntity(forProperty("parent"), inits.get("parent")) : null;
        this.post = inits.isInitialized("post") ? new com.wanted.springcafe.domain.post.QPostEntity(forProperty("post"), inits.get("post")) : null;
        this.user = inits.isInitialized("user") ? new com.wanted.springcafe.domain.user.QUserEntity(forProperty("user")) : null;
    }

}

