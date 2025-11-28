package com.login_oop.oop_backend.models;

// Class สำหรับเก็บรายการในตะกร้า
// แต่ละ CartItem จะมี username (ของใคร) และ food (อาหารอะไร)
public class CartItem {
    // ข้อมูลของรายการในตะกร้า
    private final String username;
    private final Food food;
    
    // Constructor สำหรับสร้าง CartItem
    public CartItem(String username, Food food) {
        this.username = username;
        this.food = food;
    }

    // Getter methods
    public String getUsername() {
        return username;
    }

    public Food getFood() {
        return food;
    }
}

