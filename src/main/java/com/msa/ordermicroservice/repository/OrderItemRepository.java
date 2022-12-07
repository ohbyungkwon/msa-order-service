package com.msa.ordermicroservice.repository;

import com.msa.ordermicroservice.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<Order, Long> {
}