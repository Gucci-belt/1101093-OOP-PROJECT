package com.login_oop.oop_backend.dto;

// DTO สำหรับรับข้อมูลน้ำหนักและส่วนสูงจาก frontend
public class BMIRequest {
    private String username;
    private double weight;
    private double height;

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

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}

