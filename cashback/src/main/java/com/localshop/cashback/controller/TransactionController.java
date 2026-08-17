package com.localshop.cashback.controller;

import com.localshop.cashback.dto.TransactionRequest;
import com.localshop.cashback.entity.Transaction;
import com.localshop.cashback.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<?> addTransaction(@RequestBody TransactionRequest request) {
        try {
            Transaction txn = transactionService.addTransaction(request);
            return ResponseEntity.ok(txn);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Transaction>> getUserTransactions(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") double minAmount) {
        return ResponseEntity.ok(transactionService.getUserTransactions(userId, minAmount));
    }
}