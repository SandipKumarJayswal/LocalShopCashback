package com.localshop.cashback.service;

import com.localshop.cashback.dto.PurchaseIntentRequest;
import com.localshop.cashback.entity.Product;
import com.localshop.cashback.entity.PurchaseIntent;
import com.localshop.cashback.entity.User;
import com.localshop.cashback.repository.ProductRepository;
import com.localshop.cashback.repository.PurchaseIntentRepository;
import com.localshop.cashback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseIntentService {

    @Autowired private PurchaseIntentRepository purchaseIntentRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private ProductRepository productRepository;

    public PurchaseIntent confirmInterest(PurchaseIntentRequest request) {
        User customer = userRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        PurchaseIntent intent = new PurchaseIntent();
        intent.setCustomer(customer);
        intent.setProduct(product);
        return purchaseIntentRepository.save(intent);
    }

    public List<PurchaseIntent> getByShop(Long shopId) {
        return purchaseIntentRepository.findByProductShopId(shopId);
    }

    public List<PurchaseIntent> getByCustomer(Long customerId) {
        return purchaseIntentRepository.findByCustomerId(customerId);
    }
}