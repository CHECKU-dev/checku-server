package dev.checku.checkuserver.domain.subject.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMySubject is a Querydsl query type for MySubject
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMySubject extends EntityPathBase<MySubject> {

    private static final long serialVersionUID = 462185579L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMySubject mySubject = new QMySubject("mySubject");

    public final dev.checku.checkuserver.domain.common.QBaseTimeEntity _super = new dev.checku.checkuserver.domain.common.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createAt = _super.createAt;

    public final NumberPath<Long> mySubjectId = createNumber("mySubjectId", Long.class);

    public final StringPath subjectNumber = createString("subjectNumber");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final dev.checku.checkuserver.domain.user.entity.QUser user;

    public QMySubject(String variable) {
        this(MySubject.class, forVariable(variable), INITS);
    }

    public QMySubject(Path<? extends MySubject> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMySubject(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMySubject(PathMetadata metadata, PathInits inits) {
        this(MySubject.class, metadata, inits);
    }

    public QMySubject(Class<? extends MySubject> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new dev.checku.checkuserver.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

