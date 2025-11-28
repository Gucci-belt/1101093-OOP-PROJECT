package com.login_oop.oop_backend.dto;

// DTO สำหรับรับข้อมูลตะกร้าจาก frontend
// Spring จะแปลง JSON ที่ส่งมาให้เป็น object นี้
public class CartRequest {
    private String username;
    private String foodName;

    // Getters
    public String getUsername() {
        return username;
    }

    public String getFoodName() {
        return foodName;
    }

    // Setters (Spring ใช้ตอนแปลง JSON)
    public void setUsername(String username) {
        this.username = username;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }
}

