package com.localshop.cashback.service;

import com.localshop.cashback.dto.ShopRequest;
import com.localshop.cashback.entity.Shop;
import com.localshop.cashback.entity.User;
import com.localshop.cashback.repository.ShopRepository;
import com.localshop.cashback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopService {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private UserRepository userRepository;

    public Shop registerShop(ShopRequest request) {
        User owner = userRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        Shop shop = new Shop();
        shop.setName(request.getName());
        shop.setCategory(request.getCategory());
        shop.setAddress(request.getAddress());
        shop.setOwner(owner);

        return shopRepository.save(shop);
    }

    public List<Shop> getAllShops() {
        return shopRepository.findAll();
    }
}