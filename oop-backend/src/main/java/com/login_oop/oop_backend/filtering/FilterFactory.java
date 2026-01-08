package com.login_oop.oop_backend.filtering;

import com.login_oop.oop_backend.filtering.impl.*;

/**
 * Factory class for creating Filter Specifications
 * Maps filter type strings to concrete specifications
 */
public class FilterFactory {
    
    /**
     * Create a filter specification based on filter type
     * 
     * Supported filter types:
     * - lowCalorie: < 400 kcal
     * - highCalorie: >= 500 kcal
     * - lowSugar: < 5g
     * - highSugar: >= 10g
     * - lowFat: < 10g
     * - highFat: >= 20g
     * - lowSodium: < 500mg
     * - highSodium: >= 800mg
     */
    public static FoodSpecification createFilter(String filterType) {
        if (filterType == null || filterType.isEmpty()) {
            return null;
        }
        
        switch (filterType.toLowerCase()) {
            case "lowcalorie":
            case "low-calorie":
            case "calorie-low":
                return LowCalorieSpecification.standard();
                
            case "highcalorie":
            case "high-calorie":
            case "calorie-high":
                return HighCalorieSpecification.standard();
                
            case "lowsugar":
            case "low-sugar":
            case "sugar-low":
                return LowSugarSpecification.standard();
                
            case "highsugar":
            case "high-sugar":
            case "sugar-high":
                return HighSugarSpecification.standard();
                
            case "lowfat":
            case "low-fat":
            case "fat-low":
                return LowFatSpecification.standard();
                
            case "highfat":
            case "high-fat":
            case "fat-high":
                return HighFatSpecification.standard();
                
            case "lowsodium":
            case "low-sodium":
            case "sodium-low":
                return LowSodiumSpecification.standard();
                
            case "highsodium":
            case "high-sodium":
            case "sodium-high":
                return HighSodiumSpecification.standard();
                
            default:
                return null;
        }
    }
}

