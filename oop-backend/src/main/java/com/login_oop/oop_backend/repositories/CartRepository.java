package com.login_oop.oop_backend.repositories;

import com.login_oop.oop_backend.models.CartItem;
import com.login_oop.oop_backend.models.Food;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 🎯 Repository Class สำหรับจัดการข้อมูลตะกร้า
 * ใช้ OOP: Class + Methods + Data Structure (List)
 */
@Repository
public class CartRepository {
    
    // Data Structure: ใช้ List เพื่อเก็บ CartItem objects
    private final List<CartItem> cartDatabase;

    // Constructor
    public CartRepository() {
        this.cartDatabase = new ArrayList<>();
    }

    /**
     * 🎯 Method: เพิ่มรายการลงตะกร้า
     * @param username ชื่อผู้ใช้
     * @param food อาหารที่ต้องการเพิ่ม
     */
    public void addToCart(String username, Food food) {
        CartItem cartItem = new CartItem(username, food);
        cartDatabase.add(cartItem);
    }

    /**
     * 🎯 Method: ดึงตะกร้าของผู้ใช้
     * @param username ชื่อผู้ใช้
     * @return List ของ CartItem objects
     */
    public List<CartItem> findByUsername(String username) {
        // ใช้ Stream API (Java 8+) เพื่อกรองข้อมูล
        return cartDatabase.stream()
                .filter(item -> item.getUsername().equals(username))
                .collect(Collectors.toList());
    }

    /**
     * 🎯 Method: ลบรายการออกจากตะกร้า
     * @param username ชื่อผู้ใช้
     * @param foodName ชื่ออาหารที่ต้องการลบ
     * @return true ถ้าลบสำเร็จ, false ถ้าไม่พบ
     */
    public boolean removeFromCart(String username, String foodName) {
        // ค้นหาและลบรายการแรกที่เจอ
        for (int i = 0; i < cartDatabase.size(); i++) {
            CartItem item = cartDatabase.get(i);
            if (item.getUsername().equals(username) && 
                item.getFood().getName().equals(foodName)) {
                cartDatabase.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * 🎯 Method: ล้างตะกร้าของผู้ใช้
     * @param username ชื่อผู้ใช้
     */
    public void clearCart(String username) {
        cartDatabase.removeIf(item -> item.getUsername().equals(username));
    }

    /**
     * 🎯 Method: นับจำนวนรายการในตะกร้า
     * @param username ชื่อผู้ใช้
     * @return จำนวนรายการ
     */
    public int countByUsername(String username) {
        return (int) cartDatabase.stream()
                .filter(item -> item.getUsername().equals(username))
                .count();
    }
}

