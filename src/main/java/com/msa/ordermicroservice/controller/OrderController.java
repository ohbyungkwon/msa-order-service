package com.msa.ordermicroservice.controller;

import com.msa.ordermicroservice.config.CustomRestTemplate;
import com.msa.ordermicroservice.dto.OrderDto;
import com.msa.ordermicroservice.dto.ResponseComDto;
import com.msa.ordermicroservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/order-service")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/order")
    public ResponseEntity<ResponseComDto> searchOrder(@RequestParam String userId, Pageable pageable) throws Exception{
        Page<OrderDto.show> show = orderService.searchOrderWithProductVerSimple(userId, pageable);
        return new ResponseEntity<ResponseComDto>(
                ResponseComDto.builder()
                        .resultMsg("주문 목록 조회")
                        .resultObj(show)
                        .build(), HttpStatus.OK);
    }


    @PostMapping("/order")
    public ResponseEntity<ResponseComDto> createOrder(@RequestBody OrderDto.create create) throws Exception{
        orderService.createOrder(create);
        return new ResponseEntity<ResponseComDto>(
                ResponseComDto.builder()
                        .resultMsg("주문 완료")
                        .resultObj(null)
                        .build(), HttpStatus.OK);
    }
}
