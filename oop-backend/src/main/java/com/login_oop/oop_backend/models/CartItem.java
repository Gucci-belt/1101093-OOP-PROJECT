package com.login_oop.oop_backend.models;

/**
 * 🎯 Model Class สำหรับรายการในตะกร้า (CartItem)
 * ใช้ OOP: Encapsulation + Composition (ใช้ Food object)
 */
public class CartItem {
    // Fields - Encapsulation
    private final String username; // เก็บว่าเป็นตะกร้าของใคร
    private final Food food; // Composition: CartItem มี Food
    
    // Constructor
    public CartItem(String username, Food food) {
        this.username = username;
        this.food = food;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public Food getFood() {
        return food;
    }
}

