package com.localshop.cashback.service;

import org.springframework.stereotype.Service;

@Service
public class CashbackService {

    public double calculateCashback(double amount) {
        if (amount > 10000) {
            return amount * 0.10;
        } else if (amount >= 4000) {
            return amount * 0.07;
        } else {
            return amount * 0.05;
        }
    }
}