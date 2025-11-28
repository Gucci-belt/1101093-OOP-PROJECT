package com.login_oop.oop_backend.services;

import com.login_oop.oop_backend.models.CartItem;
import com.login_oop.oop_backend.models.Food;
import com.login_oop.oop_backend.repositories.CartRepository;
import com.login_oop.oop_backend.repositories.FoodRepository;
import org.springframework.stereotype.Service;

import java.util.List;

// Service สำหรับจัดการตะกร้าสินค้า
@Service
public class CartService {
    
    // ต้องใช้ CartRepository และ FoodRepository
    private final CartRepository cartRepository;
    private final FoodRepository foodRepository;

    // Constructor รับ dependencies เข้ามา
    public CartService(CartRepository cartRepository, FoodRepository foodRepository) {
        this.cartRepository = cartRepository;
        this.foodRepository = foodRepository;
    }

    /**
     * เพิ่มอาหารลงตะกร้า
     * หาอาหารจากชื่อก่อน ถ้าเจอค่อยเพิ่มลงตะกร้า
     */
    public boolean addToCart(String username, String foodName) {
        // หาอาหารจากชื่อ
        Food food = foodRepository.findByName(foodName);
        if (food != null) {
            // เจอแล้ว เพิ่มลงตะกร้า
            cartRepository.addToCart(username, food);
            return true;
        }
        // ไม่เจออาหาร
        return false;
    }

    /**
     * ดึงตะกร้าของผู้ใช้
     */
    public List<CartItem> getCart(String username) {
        return cartRepository.findByUsername(username);
    }

    /**
     * ลบรายการออกจากตะกร้า
     */
    public boolean removeFromCart(String username, String foodName) {
        return cartRepository.removeFromCart(username, foodName);
    }

    /**
     * ล้างตะกร้าทั้งหมด
     */
    public void clearCart(String username) {
        cartRepository.clearCart(username);
    }
}

