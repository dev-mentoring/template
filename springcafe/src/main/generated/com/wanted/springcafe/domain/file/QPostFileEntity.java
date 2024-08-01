package com.wanted.springcafe.domain.file;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostFileEntity is a Querydsl query type for PostFileEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostFileEntity extends EntityPathBase<PostFileEntity> {

    private static final long serialVersionUID = 1875237497L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostFileEntity postFileEntity = new QPostFileEntity("postFileEntity");

    public final StringPath imageUrl = createString("imageUrl");

    public final com.wanted.springcafe.domain.post.QPostEntity post;

    public final NumberPath<Long> postFileId = createNumber("postFileId", Long.class);

    public QPostFileEntity(String variable) {
        this(PostFileEntity.class, forVariable(variable), INITS);
    }

    public QPostFileEntity(Path<? extends PostFileEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostFileEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostFileEntity(PathMetadata metadata, PathInits inits) {
        this(PostFileEntity.class, metadata, inits);
    }

    public QPostFileEntity(Class<? extends PostFileEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.post = inits.isInitialized("post") ? new com.wanted.springcafe.domain.post.QPostEntity(forProperty("post"), inits.get("post")) : null;
    }

}

