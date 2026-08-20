package com.localshop.cashback.controller;

import com.localshop.cashback.dto.PurchaseIntentRequest;
import com.localshop.cashback.entity.PurchaseIntent;
import com.localshop.cashback.service.PurchaseIntentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-intents")
public class PurchaseIntentController {

    @Autowired
    private PurchaseIntentService purchaseIntentService;

    @PostMapping
    public ResponseEntity<?> confirmInterest(@RequestBody PurchaseIntentRequest request) {
        try {
            PurchaseIntent intent = purchaseIntentService.confirmInterest(request);
            return ResponseEntity.ok(intent);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/shop/{shopId}")
    public ResponseEntity<List<PurchaseIntent>> getByShop(@PathVariable Long shopId) {
        return ResponseEntity.ok(purchaseIntentService.getByShop(shopId));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<PurchaseIntent>> getByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(purchaseIntentService.getByCustomer(customerId));
    }
}