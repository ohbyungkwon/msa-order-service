package com.msa.ordermicroservice.dto;

import lombok.*;

import java.util.List;

public class OrderDto {

    @Setter
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class create{
        private String userId;
        private List<Long> productId;
    }

    @Setter
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class show{
        private String orderNumber;
        private Boolean isCancel;
        private Long totAmt;
        private Integer totCnt;
        private List<Long> productIds;
    }
}
