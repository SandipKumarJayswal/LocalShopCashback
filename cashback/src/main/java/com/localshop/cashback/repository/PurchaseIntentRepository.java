package com.localshop.cashback.repository;

import com.localshop.cashback.entity.PurchaseIntent;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PurchaseIntentRepository extends JpaRepository<PurchaseIntent, Long> {
    List<PurchaseIntent> findByCustomerId(Long customerId);
    List<PurchaseIntent> findByProductShopId(Long shopId);
}