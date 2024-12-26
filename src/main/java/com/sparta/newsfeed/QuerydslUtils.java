package com.sparta.newsfeed;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.sparta.newsfeed.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class QuerydslUtils {

    public static <T> JPQLQuery<T> pagination(
            JPQLQuery<T> query,
            EntityPathBase<?> from,
            Pageable pageable
    ) {
        return query
                .orderBy(toOrderSpecifier(pageable.getSort(), from))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());
    }

    public static OrderSpecifier<?>[] toOrderSpecifier(Sort sort, EntityPathBase<?> from) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        PathBuilder<Object> pathBuilder = new PathBuilder<>(Object.class, from.getMetadata());

        sort.forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String property = order.getProperty();

            try {
                orderSpecifiers.add(new OrderSpecifier<>(direction, pathBuilder.get(property, Comparable.class)));
            } catch (IllegalArgumentException e) {
                throw new CustomException.BadRequestException("요청된 이름에 해당되는 정렬 기준이 존재하지 않습니다.");
            }
        });
        return orderSpecifiers.toArray(new OrderSpecifier[0]);
    }

    public static <T> Page<T> fetchPage(
            JPQLQuery<T> query,
            EntityPathBase<?> from,
            Pageable pageable
    ) {
        com.querydsl.core.QueryResults<T> results = pagination(query, from, pageable).fetchResults();
        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }
}
