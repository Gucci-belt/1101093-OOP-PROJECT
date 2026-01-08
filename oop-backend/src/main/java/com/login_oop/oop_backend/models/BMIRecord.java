package com.login_oop.oop_backend.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Class สำหรับเก็บข้อมูล BMI แต่ละครั้ง (Value Object)
 * Immutable - ไม่สามารถแก้ไขได้หลังจากสร้าง
 */
public class BMIRecord {
    
    private final String username;
    private final double weight;        // น้ำหนัก (kg)
    private final double height;        // ส่วนสูง (cm)
    private final double bmi;           // ค่า BMI ที่คำนวณได้
    private final String category;      // หมวดหมู่ BMI (เช่น "น้ำหนักปกติ")
    private final double recommendedCalories; // แคลอรี่ที่แนะนำ
    private final LocalDateTime timestamp;    // เวลาที่บันทึก
    private final LocalDate date;             // วันที่ (สำหรับกรองตามวันที่)
    
    /**
     * Constructor
     */
    public BMIRecord(String username, double weight, double height, 
                    double bmi, String category, double recommendedCalories,
                    LocalDateTime timestamp) {
        this.username = username;
        this.weight = weight;
        this.height = height;
        this.bmi = bmi;
        this.category = category;
        this.recommendedCalories = recommendedCalories;
        this.timestamp = timestamp;
        this.date = timestamp.toLocalDate();
    }
    
    // Getters (ไม่มี Setters เพื่อให้เป็น Immutable)
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
    
    public String getCategory() { 
        return category; 
    }
    
    public double getRecommendedCalories() { 
        return recommendedCalories; 
    }
    
    public LocalDateTime getTimestamp() { 
        return timestamp; 
    }
    
    public LocalDate getDate() { 
        return date; 
    }
    
    @Override
    public String toString() {
        return String.format("BMIRecord{username='%s', bmi=%.2f, category='%s', date=%s}",
                username, bmi, category, date);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BMIRecord bmiRecord = (BMIRecord) o;
        return username.equals(bmiRecord.username) && 
               timestamp.equals(bmiRecord.timestamp);
    }
    
    @Override
    public int hashCode() {
        return username.hashCode() * 31 + timestamp.hashCode();
    }
}


