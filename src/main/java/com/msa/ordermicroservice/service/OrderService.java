package com.msa.ordermicroservice.service;

import com.msa.ordermicroservice.config.CustomRestTemplate;
import com.msa.ordermicroservice.domain.Order;
import com.msa.ordermicroservice.domain.OrderItem;
import com.msa.ordermicroservice.dto.OrderDto;
import com.msa.ordermicroservice.dto.ResponseComDto;
import com.msa.ordermicroservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomRestTemplate restTemplate;

    public Page<OrderDto.show> searchOrderWithProductVerSimple(String userId, Pageable pageable) throws Exception {
        return orderRepository.findOrderWithProductsByUser(userId, pageable);
    }

    @Transactional
    public void createOrder(OrderDto.create create) throws Exception {
        List<Long> productsId = create.getProductId();
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productsId);

        JSONArray products = (JSONArray) restTemplate.requestGet("http://localhost:17070",
                "/products", map);

        log.info(products.toString());
        Order order = Order.create(create, products);
        order.addOrderItems(productsId);
        orderRepository.save(order);
    }
}