package com.login_oop.oop_backend.dto;

/**
 * DTO สำหรับรับ request body จาก frontend เมื่อเพิ่มอาหารใหม่
 */
public class FoodRequest {
    private String name;
    private double kcal;
    private double fat;
    private double sugar;
    private double sodium;
    private String imageUrl; // สำหรับเก็บ URL รูปภาพ (optional)

    // Constructor
    public FoodRequest() {
    }

    public FoodRequest(String name, double kcal, double fat, double sugar, double sodium, String imageUrl) {
        this.name = name;
        this.kcal = kcal;
        this.fat = fat;
        this.sugar = sugar;
        this.sodium = sodium;
        this.imageUrl = imageUrl;
    }

    // Getter and Setter methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
