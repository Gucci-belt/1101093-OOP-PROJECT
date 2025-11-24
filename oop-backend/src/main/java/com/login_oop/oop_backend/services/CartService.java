package com.login_oop.oop_backend.services;

import com.login_oop.oop_backend.models.CartItem;
import com.login_oop.oop_backend.models.Food;
import com.login_oop.oop_backend.repositories.CartRepository;
import com.login_oop.oop_backend.repositories.FoodRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 🎯 Service Class สำหรับจัดการ Business Logic ของตะกร้า
 * ใช้ OOP: Class + Methods + Dependency Injection
 */
@Service
public class CartService {
    
    // Dependency Injection: CartService ต้องใช้ CartRepository และ FoodRepository
    private final CartRepository cartRepository;
    private final FoodRepository foodRepository;

    // Constructor Injection (OOP: Dependency Injection)
    public CartService(CartRepository cartRepository, FoodRepository foodRepository) {
        this.cartRepository = cartRepository;
        this.foodRepository = foodRepository;
    }

    /**
     * 🎯 Method: เพิ่มอาหารลงตะกร้า
     * @param username ชื่อผู้ใช้
     * @param foodName ชื่ออาหาร
     * @return true ถ้าสำเร็จ, false ถ้าไม่พบอาหาร
     */
    public boolean addToCart(String username, String foodName) {
        Food food = foodRepository.findByName(foodName);
        if (food != null) {
            cartRepository.addToCart(username, food);
            return true;
        }
        return false;
    }

    /**
     * 🎯 Method: ดึงตะกร้าของผู้ใช้
     * @param username ชื่อผู้ใช้
     * @return List ของ CartItem objects
     */
    public List<CartItem> getCart(String username) {
        return cartRepository.findByUsername(username);
    }

    /**
     * 🎯 Method: ลบรายการออกจากตะกร้า
     * @param username ชื่อผู้ใช้
     * @param foodName ชื่ออาหาร
     * @return true ถ้าสำเร็จ, false ถ้าไม่พบ
     */
    public boolean removeFromCart(String username, String foodName) {
        return cartRepository.removeFromCart(username, foodName);
    }

    /**
     * 🎯 Method: ล้างตะกร้า
     * @param username ชื่อผู้ใช้
     */
    public void clearCart(String username) {
        cartRepository.clearCart(username);
    }
}

