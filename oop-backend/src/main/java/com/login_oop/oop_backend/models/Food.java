package com.login_oop.oop_backend.models;

/**
 * 🎯 Model Class สำหรับอาหาร (Food)
 * ใช้ OOP: Encapsulation (private fields + getters)
 */
public class Food {
    // Fields - Encapsulation: ใช้ private เพื่อป้องกันการเข้าถึงโดยตรง
    private final String name;
    private final double kcal;
    private final double fat;
    private final double sugar;
    private final double sodium;

    // Constructor
    public Food(String name, double kcal, double fat, double sugar, double sodium) {
        this.name = name;
        this.kcal = kcal;
        this.fat = fat;
        this.sugar = sugar;
        this.sodium = sodium;
    }

    // Getters (Encapsulation: ให้เข้าถึงข้อมูลผ่าน methods)
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

