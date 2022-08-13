package dev.checku.checkuserver.domain.subject.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSubject is a Querydsl query type for Subject
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSubject extends EntityPathBase<Subject> {

    private static final long serialVersionUID = 808876663L;

    public static final QSubject subject = new QSubject("subject");

    public final NumberPath<Long> subjectId = createNumber("subjectId", Long.class);

    public final StringPath subjectName = createString("subjectName");

    public final StringPath subjectNumber = createString("subjectNumber");

    public final EnumPath<dev.checku.checkuserver.domain.model.SubjectType> subjectType = createEnum("subjectType", dev.checku.checkuserver.domain.model.SubjectType.class);

    public QSubject(String variable) {
        super(Subject.class, forVariable(variable));
    }

    public QSubject(Path<? extends Subject> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSubject(PathMetadata metadata) {
        super(Subject.class, metadata);
    }

}

