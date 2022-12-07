package com.msa.ordermicroservice.dto;

import lombok.*;

import java.util.List;

public class OrderItemDto {

    @Setter
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class show{
        private String name;
        private String price;
    }
}
