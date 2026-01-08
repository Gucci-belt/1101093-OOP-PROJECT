package com.login_oop.oop_backend.filtering.impl;

import com.login_oop.oop_backend.filtering.FoodSpecification;
import com.login_oop.oop_backend.models.Food;

/**
 * Generic Range Filter Specification
 * Supports filtering by minimum and/or maximum values
 */
public class RangeFilter implements FoodSpecification {
    private final double minValue;
    private final double maxValue;
    private final RangeExtractor extractor;
    
    @FunctionalInterface
    public interface RangeExtractor {
        double extract(Food food);
    }
    
    public RangeFilter(double minValue, double maxValue, RangeExtractor extractor) {
        // ใช้ -1 แทน null/undefined สำหรับค่า default
        this.minValue = minValue < 0 ? -1 : minValue;
        this.maxValue = maxValue < 0 ? -1 : maxValue;
        this.extractor = extractor;
    }
    
    @Override
    public boolean isSatisfiedBy(Food food) {
        double value = extractor.extract(food);
        
        // ถ้ามี minValue (>= 0) ต้องมากกว่าหรือเท่ากับ minValue
        if (minValue >= 0 && value < minValue) {
            return false;
        }
        
        // ถ้ามี maxValue (>= 0) ต้องน้อยกว่าหรือเท่ากับ maxValue
        if (maxValue >= 0 && value > maxValue) {
            return false;
        }
        
        return true;
    }
    
    /**
     * สร้าง Range Filter สำหรับแคลอรี่
     */
    public static RangeFilter calorieRange(double min, double max) {
        return new RangeFilter(min, max, Food::getKcal);
    }
    
    /**
     * สร้าง Range Filter สำหรับน้ำตาล
     */
    public static RangeFilter sugarRange(double min, double max) {
        return new RangeFilter(min, max, Food::getSugar);
    }
    
    /**
     * สร้าง Range Filter สำหรับไขมัน
     */
    public static RangeFilter fatRange(double min, double max) {
        return new RangeFilter(min, max, Food::getFat);
    }
    
    /**
     * สร้าง Range Filter สำหรับโซเดียม
     */
    public static RangeFilter sodiumRange(double min, double max) {
        return new RangeFilter(min, max, Food::getSodium);
    }
}

