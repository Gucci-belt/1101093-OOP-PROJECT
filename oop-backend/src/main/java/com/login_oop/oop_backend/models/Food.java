package com.login_oop.oop_backend.models;

// Class สำหรับเก็บข้อมูลอาหาร
public class Food {
    // ข้อมูลของอาหาร
    private final String name;
    private final double kcal;
    private final double fat;
    private final double sugar;
    private final double sodium;

    // Constructor สำหรับสร้าง Food
    public Food(String name, double kcal, double fat, double sugar, double sodium) {
        this.name = name;
        this.kcal = kcal;
        this.fat = fat;
        this.sugar = sugar;
        this.sodium = sodium;
    }

    // Getter methods
    public String getName() {
        return name;
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
}

