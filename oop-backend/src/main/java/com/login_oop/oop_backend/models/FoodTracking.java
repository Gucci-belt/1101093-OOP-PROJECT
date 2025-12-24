package com.login_oop.oop_backend.models;

import java.time.LocalDateTime;

// Class สำหรับเก็บข้อมูลการติดตามอาหารที่ทาน
public class FoodTracking {
    private final String username;
    private final String foodName;
    private final double kcal;
    private final double fat;
    private final double sugar;
    private final double sodium;
    private final LocalDateTime timestamp;
    private final String dateKey; // YYYY-MM-DD format

    // Constructor
    public FoodTracking(String username, String foodName, double kcal, double fat, 
                       double sugar, double sodium, LocalDateTime timestamp) {
        this.username = username;
        this.foodName = foodName;
        this.kcal = kcal;
        this.fat = fat;
        this.sugar = sugar;
        this.sodium = sodium;
        this.timestamp = timestamp;
        this.dateKey = timestamp.toLocalDate().toString(); // YYYY-MM-DD
    }

    // Getter methods
    public String getUsername() {
        return username;
    }

    public String getFoodName() {
        return foodName;
    }

    public double getKcal() {
        return kcal;
    }

    public double getFat() {
        return fat;
    }

    public double getSugar() {
        return sugar;
    }

    public double getSodium() {
        return sodium;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getDateKey() {
        return dateKey;
    }
}

