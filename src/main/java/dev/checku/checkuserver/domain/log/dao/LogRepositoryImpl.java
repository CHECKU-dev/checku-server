package dev.checku.checkuserver.domain.log.dao;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dev.checku.checkuserver.domain.log.entity.Log;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static dev.checku.checkuserver.domain.log.entity.QLog.log;


@Repository
public class LogRepositoryImpl implements LogRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public LogRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Log> findLog(Pageable pageable) {
        List<Log> results = queryFactory
                .selectFrom(log)
                .orderBy(listSort(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int totalSize = queryFactory
                .selectFrom(log)
                .fetch().size();

        return new PageImpl<>(results, pageable, totalSize);
    }

    private OrderSpecifier<?> listSort(Pageable pageable) {

        if (!pageable.getSort().isEmpty()) {
            for (Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                switch (order.getProperty()) {
                    case "EXECUTION_TIME":
                        return new OrderSpecifier<>(direction, log.executionTime);
                    case "CREATE_TIME":
                        return new OrderSpecifier<>(direction, log.createAt);
                }
            }
        }
        return null;
    }
}
