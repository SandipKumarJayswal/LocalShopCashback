package com.localshop.cashback.dto;

import lombok.Data;

@Data
public class ShopRequest {
    private String name;
    private String category;
    private String address;
    private Long ownerId;
}