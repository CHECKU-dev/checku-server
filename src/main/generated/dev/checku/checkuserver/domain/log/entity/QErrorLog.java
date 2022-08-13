package dev.checku.checkuserver.domain.log.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QErrorLog is a Querydsl query type for ErrorLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QErrorLog extends EntityPathBase<ErrorLog> {

    private static final long serialVersionUID = -26426295L;

    public static final QErrorLog errorLog = new QErrorLog("errorLog");

    public final dev.checku.checkuserver.domain.common.QBaseTimeEntity _super = new dev.checku.checkuserver.domain.common.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createAt = _super.createAt;

    public final StringPath errorMessage = createString("errorMessage");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QErrorLog(String variable) {
        super(ErrorLog.class, forVariable(variable));
    }

    public QErrorLog(Path<? extends ErrorLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QErrorLog(PathMetadata metadata) {
        super(ErrorLog.class, metadata);
    }

}

