package com.msa.ordermicroservice.repository.custom;

import com.msa.ordermicroservice.domain.Order;
import com.msa.ordermicroservice.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepositoryCustom {
    Page<OrderDto.show> findOrderWithProductsByUser(String userId, Pageable pageable);
}
