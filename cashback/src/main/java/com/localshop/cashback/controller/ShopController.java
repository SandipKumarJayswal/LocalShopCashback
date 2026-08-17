package com.localshop.cashback.controller;

import com.localshop.cashback.dto.ShopRequest;
import com.localshop.cashback.entity.Shop;
import com.localshop.cashback.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shops")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @PostMapping
    public ResponseEntity<?> registerShop(@RequestBody ShopRequest request) {
        try {
            Shop shop = shopService.registerShop(request);
            return ResponseEntity.ok(shop);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Shop>> getAllShops() {
        return ResponseEntity.ok(shopService.getAllShops());
    }
}