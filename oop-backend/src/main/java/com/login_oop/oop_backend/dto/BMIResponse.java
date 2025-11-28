package com.login_oop.oop_backend.dto;

// DTO สำหรับส่งข้อมูล BMI กลับไป frontend
public class BMIResponse {
    private double bmi;
    private double calories;
    private String category;
    private String categoryRange;

    // Constructor
    public BMIResponse(double bmi, double calories, String category, String categoryRange) {
        this.bmi = bmi;
        this.calories = calories;
        this.category = category;
        this.categoryRange = categoryRange;
    }

    // Getters
    public double getBmi() {
        return bmi;
    }

    public double getCalories() {
        return calories;
    }

    public String getCategory() {
        return category;
    }

    public String getCategoryRange() {
        return categoryRange;
    }
}

