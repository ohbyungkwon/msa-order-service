package com.msa.ordermicroservice.domain;


import com.msa.ordermicroservice.dto.OrderDto;
import com.msa.ordermicroservice.dto.OrderItemDto;
import lombok.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table(name = "orders")
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(value = {AuditingEntityListener.class})
public class Order {

    @Id
    @GeneratedValue
    private Long orderId;

    @Column(nullable = false, unique = true)
    private String orderNumber;

    @Column(nullable = false)
    private Boolean isCancel;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private Long totAmt;

    @Column(nullable = false)
    private Integer totCnt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<OrderItem> orderItems = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

    public static Order create(OrderDto.create create, JSONArray products){
        String orderNum = UUID.randomUUID().toString();

        long totAmt = 0;
        for(int i = 0; i < products.length(); i++){
            JSONObject jsonObject = products.getJSONObject(i);
            totAmt += jsonObject.getLong("price");
        }

        return Order.builder()
                .orderNumber(orderNum)
                .isCancel(Boolean.FALSE)
                .userId(create.getUserId())
                .totAmt(totAmt)
                .totCnt(products.length())
                .orderItems(new ArrayList<>())
                .build();
    }
    public Order addOrderItems(List<Long> productsId) {
        for (Long id : productsId) {
            OrderItem orderItem = OrderItem.create(id);
            orderItem.joinOrder(this);
        }
        return this;
    }

    public OrderDto.show convertDto(){
        List<Long> productIds = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            productIds.add(orderItem.getProductId());
        }

        return OrderDto.show.builder()
                .orderNumber(orderNumber)
                .isCancel(isCancel)
                .totAmt(totAmt)
                .totCnt(totCnt)
                .productIds(productIds)
                .build();
    }
}
