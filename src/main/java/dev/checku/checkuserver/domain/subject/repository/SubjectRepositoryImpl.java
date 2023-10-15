package dev.checku.checkuserver.domain.subject.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.checku.checkuserver.domain.subject.entity.Subject;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static dev.checku.checkuserver.domain.subject.entity.QSubject.subject;
r
@Repository
@RequiredArgsConstructor
public class SubjectRepositoryImpl implements SubjectRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Subject> findByKeyword(String searchQuery, Pageable pageable) {

        List<Subject> results = queryFactory
                .selectFrom(subject)
                .where(
                        subjectNameLike(searchQuery)
                )
                .orderBy(subject.subjectNumber.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        return results;

    }

    private BooleanExpression subjectNameLike(String searchQuery) {
        return subject.subjectName.like("%" + searchQuery + "%");
    }
}
