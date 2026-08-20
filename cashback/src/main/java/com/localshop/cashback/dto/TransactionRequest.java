package com.localshop.cashback.dto;

import lombok.Data;

@Data
public class TransactionRequest {
    private Long userId;
    private Long shopId;
    private Long productId;
    private Double amount;
}