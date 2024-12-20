package com.sparta.newsfeed.newsfeed.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNewsfeed is a Querydsl query type for Newsfeed
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNewsfeed extends EntityPathBase<Newsfeed> {

    private static final long serialVersionUID = 1444879094L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNewsfeed newsfeed = new QNewsfeed("newsfeed");

    public final com.sparta.newsfeed.QBaseEntity _super = new com.sparta.newsfeed.QBaseEntity(this);

    public final StringPath contents = createString("contents");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.sparta.newsfeed.user.entity.QUser user;

    public QNewsfeed(String variable) {
        this(Newsfeed.class, forVariable(variable), INITS);
    }

    public QNewsfeed(Path<? extends Newsfeed> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNewsfeed(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNewsfeed(PathMetadata metadata, PathInits inits) {
        this(Newsfeed.class, metadata, inits);
    }

    public QNewsfeed(Class<? extends Newsfeed> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.sparta.newsfeed.user.entity.QUser(forProperty("user")) : null;
    }

}

