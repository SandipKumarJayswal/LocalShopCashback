package com.localshop.cashback.service;

import com.localshop.cashback.dto.TransactionRequest;
import com.localshop.cashback.entity.Shop;
import com.localshop.cashback.entity.Transaction;
import com.localshop.cashback.entity.User;
import com.localshop.cashback.entity.Wallet;
import com.localshop.cashback.repository.ShopRepository;
import com.localshop.cashback.repository.TransactionRepository;
import com.localshop.cashback.repository.UserRepository;
import com.localshop.cashback.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired private TransactionRepository transactionRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private ShopRepository shopRepository;
    @Autowired private WalletRepository walletRepository;
    @Autowired private CashbackService cashbackService;

    public Transaction addTransaction(TransactionRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Shop shop = shopRepository.findById(request.getShopId())
                .orElseThrow(() -> new RuntimeException("Shop not found"));

        double cashback = cashbackService.calculateCashback(request.getAmount());

        Transaction txn = new Transaction();
        txn.setUser(user);
        txn.setShop(shop);
        txn.setAmount(request.getAmount());
        txn.setCashbackEarned(cashback);
        Transaction savedTxn = transactionRepository.save(txn);

        Wallet wallet = walletRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
        wallet.setBalance(wallet.getBalance() + cashback);
        walletRepository.save(wallet);

        return savedTxn;
    }

    public List<Transaction> getUserTransactions(Long userId, double minAmount) {
        List<Transaction> all = transactionRepository.findByUserIdOrderByDateDesc(userId);

        return all.stream()
                .filter(t -> t.getAmount() >= minAmount)
                .sorted(Comparator.comparing(Transaction::getDate).reversed())
                .collect(Collectors.toList());
    }
}