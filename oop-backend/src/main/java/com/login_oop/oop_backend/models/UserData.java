package com.login_oop.oop_backend.models;

// Class สำหรับเก็บข้อมูล BMI และข้อมูลส่วนตัวของ user
public class UserData {
    private String username;
    private double weight;
    private double height;
    private double bmi;
    private double calories;

    // Constructor
    public UserData(String username, double weight, double height, double bmi, double calories) {
        this.username = username;
        this.weight = weight;
        this.height = height;
        this.bmi = bmi;
        this.calories = calories;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public double getWeight() {
        return weight;
    }

    public double getHeight() {
        return height;
    }

    public double getBmi() {
        return bmi;
    }

    public double getCalories() {
        return calories;
    }

    // Setters
    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }
}

