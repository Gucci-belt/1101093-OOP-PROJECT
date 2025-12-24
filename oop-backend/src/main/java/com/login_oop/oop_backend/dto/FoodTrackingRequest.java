package com.login_oop.oop_backend.dto;

// DTO สำหรับรับ request ในการเพิ่ม tracking
public class FoodTrackingRequest {
    private String username;
    private String foodName;

    // Constructor
    public FoodTrackingRequest() {
    }

    public FoodTrackingRequest(String username, String foodName) {
        this.username = username;
        this.foodName = foodName;
    }

    // Getter methods
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }
}

