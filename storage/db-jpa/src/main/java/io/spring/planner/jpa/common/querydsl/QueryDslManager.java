package io.spring.planner.jpa.common.querydsl;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class QueryDslManager {

    public OrderSpecifier createOrderSpecifier(Path<?> entity, Sort.Order sortOrder) {
        Order direction = sortOrder.isAscending() ? Order.ASC : Order.DESC;
        Path<Object> fieldPath = Expressions.path(Object.class, entity, sortOrder.getProperty());
        return new OrderSpecifier(direction, fieldPath);
    }
}
