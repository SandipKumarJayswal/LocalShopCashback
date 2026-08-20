package com.localshop.cashback.dto;

import lombok.Data;

@Data
public class PurchaseIntentRequest {
    private Long customerId;
    private Long productId;
}