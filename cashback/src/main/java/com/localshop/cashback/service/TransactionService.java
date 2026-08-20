package com.localshop.cashback.service;

import com.localshop.cashback.dto.TransactionRequest;
import com.localshop.cashback.entity.*;
import com.localshop.cashback.repository.*;
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
    @Autowired private ProductRepository productRepository;
    @Autowired private PurchaseIntentRepository purchaseIntentRepository;

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

        if (request.getProductId() != null) {
            Product product = productRepository.findById(request.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            txn.setProduct(product);

            // agar customer ne pehle "interest confirm" kiya tha to use COMPLETED mark karo
            purchaseIntentRepository.findByCustomerId(user.getId()).stream()
                    .filter(pi -> pi.getProduct().getId().equals(product.getId())
                            && pi.getStatus() == PurchaseIntent.Status.PENDING)
                    .findFirst()
                    .ifPresent(pi -> {
                        pi.setStatus(PurchaseIntent.Status.COMPLETED);
                        purchaseIntentRepository.save(pi);
                    });
        }

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