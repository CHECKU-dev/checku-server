package dev.checku.checkuserver.domain.topic.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTopic is a Querydsl query type for Topic
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTopic extends EntityPathBase<Topic> {

    private static final long serialVersionUID = -502514921L;

    public static final QTopic topic = new QTopic("topic");

    public final StringPath subjectNumber = createString("subjectNumber");

    public final NumberPath<Long> topicId = createNumber("topicId", Long.class);

    public QTopic(String variable) {
        super(Topic.class, forVariable(variable));
    }

    public QTopic(Path<? extends Topic> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTopic(PathMetadata metadata) {
        super(Topic.class, metadata);
    }

}

