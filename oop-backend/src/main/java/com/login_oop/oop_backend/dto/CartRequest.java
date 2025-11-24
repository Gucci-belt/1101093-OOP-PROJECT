package com.login_oop.oop_backend.dto;

/**
 * 🎯 DTO Class สำหรับรับข้อมูลจาก Frontend
 * ใช้ OOP: Encapsulation (private fields + getters/setters)
 */
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

    // Setters (จำเป็นสำหรับ Spring เพื่อ deserialize JSON)
    public void setUsername(String username) {
        this.username = username;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }
}

