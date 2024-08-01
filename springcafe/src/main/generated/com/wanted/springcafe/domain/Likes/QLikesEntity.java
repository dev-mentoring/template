package com.wanted.springcafe.domain.Likes;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLikesEntity is a Querydsl query type for LikesEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLikesEntity extends EntityPathBase<LikesEntity> {

    private static final long serialVersionUID = 1783375797L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLikesEntity likesEntity = new QLikesEntity("likesEntity");

    public final NumberPath<Long> likesId = createNumber("likesId", Long.class);

    public final com.wanted.springcafe.domain.post.QPostEntity post;

    public final com.wanted.springcafe.domain.user.QUserEntity user;

    public QLikesEntity(String variable) {
        this(LikesEntity.class, forVariable(variable), INITS);
    }

    public QLikesEntity(Path<? extends LikesEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLikesEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLikesEntity(PathMetadata metadata, PathInits inits) {
        this(LikesEntity.class, metadata, inits);
    }

    public QLikesEntity(Class<? extends LikesEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.post = inits.isInitialized("post") ? new com.wanted.springcafe.domain.post.QPostEntity(forProperty("post"), inits.get("post")) : null;
        this.user = inits.isInitialized("user") ? new com.wanted.springcafe.domain.user.QUserEntity(forProperty("user")) : null;
    }

}

