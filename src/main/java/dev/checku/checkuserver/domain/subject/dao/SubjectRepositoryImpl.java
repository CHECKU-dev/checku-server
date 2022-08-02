package dev.checku.checkuserver.domain.subject.dao;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.checku.checkuserver.domain.subject.dto.GetSearchSubjectDto;
import dev.checku.checkuserver.domain.subject.entity.QSubject;
import dev.checku.checkuserver.domain.subject.entity.Subject;
import org.bouncycastle.asn1.dvcs.ServiceType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static dev.checku.checkuserver.domain.subject.entity.QSubject.subject;


@Repository
public class SubjectRepositoryImpl implements SubjectRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public SubjectRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Subject> findSubjectBySearch(String searchQuery, Pageable pageable) {

        List<Subject> results = queryFactory
                .selectFrom(subject)
                .where(
                        searchByLike(searchQuery)
                )
                .orderBy(subject.subjectNumber.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        return results;

    }

    private BooleanExpression searchByLike(String searchQuery) {
        return subject.subjectName.like("%" + searchQuery + "%");

    }


}
