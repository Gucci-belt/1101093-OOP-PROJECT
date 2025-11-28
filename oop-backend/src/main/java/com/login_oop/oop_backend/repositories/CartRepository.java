package com.login_oop.oop_backend.repositories;

import com.login_oop.oop_backend.models.CartItem;
import com.login_oop.oop_backend.models.Food;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Repository สำหรับจัดการข้อมูลตะกร้า
// เก็บรายการในตะกร้าไว้ใน memory
@Repository
public class CartRepository {
    
    // เก็บรายการในตะกร้าทั้งหมด
    private final List<CartItem> cartDatabase;

    // Constructor
    public CartRepository() {
        this.cartDatabase = new ArrayList<>();
    }

    /**
     * เพิ่มรายการลงตะกร้า
     */
    public void addToCart(String username, Food food) {
        CartItem cartItem = new CartItem(username, food);
        cartDatabase.add(cartItem);
    }

    /**
     * ดึงตะกร้าของผู้ใช้
     * กรองเฉพาะรายการที่ username ตรงกัน
     */
    public List<CartItem> findByUsername(String username) {
        return cartDatabase.stream()
                .filter(item -> item.getUsername().equals(username))
                .collect(Collectors.toList());
    }

    /**
     * ลบรายการออกจากตะกร้า
     * ลบรายการแรกที่เจอ
     */
    public boolean removeFromCart(String username, String foodName) {
        for (int i = 0; i < cartDatabase.size(); i++) {
            CartItem item = cartDatabase.get(i);
            if (item.getUsername().equals(username) && 
                item.getFood().getName().equals(foodName)) {
                cartDatabase.remove(i);
                return true;
            }
        }
        return false; // ไม่เจอ
    }

    /**
     * ล้างตะกร้าของผู้ใช้
     * ลบรายการทั้งหมดที่ username ตรงกัน
     */
    public void clearCart(String username) {
        cartDatabase.removeIf(item -> item.getUsername().equals(username));
    }

    /**
     * นับจำนวนรายการในตะกร้า
     */
    public int countByUsername(String username) {
        return (int) cartDatabase.stream()
                .filter(item -> item.getUsername().equals(username))
                .count();
    }
}

