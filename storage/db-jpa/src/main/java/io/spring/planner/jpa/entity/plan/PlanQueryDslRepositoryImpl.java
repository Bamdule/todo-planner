package io.spring.planner.jpa.entity.plan;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.spring.planner.domain.common.exception.UnsupportedSortPropertyException;
import io.spring.planner.jpa.common.querydsl.QueryDslManager;
import io.spring.planner.usecase.plan.findlist.PlanFindListQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static io.spring.planner.jpa.entity.plan.QPlanEntity.planEntity;

@RequiredArgsConstructor
public class PlanQueryDslRepositoryImpl implements PlanQueryDslRepository {

    private final JPAQueryFactory queryFactory;
    private final QueryDslManager queryDslManager;

    @Override
    public Page<PlanEntity> findList(PlanFindListQuery query, Pageable pageable) {

        BooleanBuilder conditionBuilder = new BooleanBuilder()
            .and(planEntity.memberId.eq(query.memberId()));

        OrderSpecifier[] orderSpecifiers = createOrderSpecifiers(pageable);


        List<PlanEntity> contents = queryFactory
            .select(planEntity)
            .from(planEntity)
            .where(conditionBuilder)
            .orderBy(orderSpecifiers)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy()
            .fetch();

        Long count = Optional.ofNullable(
            queryFactory
                .select(planEntity.count())
                .from(planEntity)
                .where(conditionBuilder)
                .fetchOne()
        ).orElse(0L);


        return new PageImpl<>(contents, pageable, count);
    }

    private OrderSpecifier[] createOrderSpecifiers(Pageable pageable) {
        if (pageable.getSort().isUnsorted()) {
            return createDefaultOrderSpecifiers();
        }


        return pageable.getSort().stream()
            .map(this::createOrderSpecifier)
            .toArray(OrderSpecifier[]::new);
    }

    private OrderSpecifier createOrderSpecifier(Sort.Order order) {
        switch (order.getProperty()) {
            case "title":
            case "id":
                return queryDslManager.createOrderSpecifier(planEntity, order);
            default:
                throw new UnsupportedSortPropertyException(order.getProperty());
        }
    }

    private OrderSpecifier[] createDefaultOrderSpecifiers() {
        return new OrderSpecifier[]{
            createOrderSpecifier(new Sort.Order(Sort.Direction.DESC, "id"))
        };
    }
}
