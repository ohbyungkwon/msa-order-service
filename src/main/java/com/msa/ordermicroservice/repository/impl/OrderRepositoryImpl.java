package com.msa.ordermicroservice.repository.impl;

import static com.msa.ordermicroservice.domain.QOrder.order;
import static com.msa.ordermicroservice.domain.QOrderItem.orderItem;

import com.msa.ordermicroservice.domain.Order;
import com.msa.ordermicroservice.dto.OrderDto;
import com.msa.ordermicroservice.repository.custom.OrderRepositoryCustom;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;


@Repository
public class OrderRepositoryImpl implements OrderRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public OrderRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<OrderDto.show> findOrderWithProductsByUser(String userId, Pageable pageable){
        QueryResults<Order> queryResults = jpaQueryFactory
                .selectFrom(order)
                .join(order.orderItems, orderItem).fetchJoin()
                .where(order.userId.eq(userId))
                .orderBy(order.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .distinct()
                .fetchResults();

        List<OrderDto.show> list = queryResults.getResults().stream()
                .map(Order::convertDto)
                .collect(Collectors.toList());

        long totalCnt = queryResults.getTotal();
        return new PageImpl<OrderDto.show>(list, pageable, totalCnt);
    }
}
