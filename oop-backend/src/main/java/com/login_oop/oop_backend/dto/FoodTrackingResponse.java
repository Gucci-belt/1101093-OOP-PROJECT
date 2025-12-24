package com.login_oop.oop_backend.dto;

import com.login_oop.oop_backend.models.FoodTracking;

import java.util.List;
import java.util.stream.Collectors;

// DTO สำหรับส่ง response กลับไป
public class FoodTrackingResponse {
    private String username;
    private String foodName;
    private double kcal;
    private double fat;
    private double sugar;
    private double sodium;
    private String timestamp;
    private String dateKey;

    // Constructor
    public FoodTrackingResponse() {
    }

    public FoodTrackingResponse(String username, String foodName, double kcal, double fat, 
                               double sugar, double sodium, String timestamp, String dateKey) {
        this.username = username;
        this.foodName = foodName;
        this.kcal = kcal;
        this.fat = fat;
        this.sugar = sugar;
        this.sodium = sodium;
        this.timestamp = timestamp;
        this.dateKey = dateKey;
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

    public double getKcal() {
        return kcal;
    }

    public void setKcal(double kcal) {
        this.kcal = kcal;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getSugar() {
        return sugar;
    }

    public void setSugar(double sugar) {
        this.sugar = sugar;
    }

    public double getSodium() {
        return sodium;
    }

    public void setSodium(double sodium) {
        this.sodium = sodium;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDateKey() {
        return dateKey;
    }

    public void setDateKey(String dateKey) {
        this.dateKey = dateKey;
    }

    /**
     * แปลงจาก FoodTracking เป็น FoodTrackingResponse
     */
    public static FoodTrackingResponse fromTracking(FoodTracking tracking) {
        return new FoodTrackingResponse(
            tracking.getUsername(),
            tracking.getFoodName(),
            tracking.getKcal(),
            tracking.getFat(),
            tracking.getSugar(),
            tracking.getSodium(),
            tracking.getTimestamp().toString(),
            tracking.getDateKey()
        );
    }

    /**
     * แปลง List<FoodTracking> เป็น List<FoodTrackingResponse>
     */
    public static List<FoodTrackingResponse> fromTrackingList(List<FoodTracking> trackingList) {
        return trackingList.stream()
                .map(FoodTrackingResponse::fromTracking)
                .collect(Collectors.toList());
    }
}

