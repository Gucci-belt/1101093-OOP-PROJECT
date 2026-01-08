package com.login_oop.oop_backend.filtering.impl;

import com.login_oop.oop_backend.filtering.FoodSpecification;
import com.login_oop.oop_backend.models.Food;

/**
 * Specification for Low Calorie foods
 * Default threshold: < 400 kcal
 */
public class LowCalorieSpecification implements FoodSpecification {
    private final double maxCalories;
    
    public LowCalorieSpecification(double maxCalories) {
        this.maxCalories = maxCalories;
    }
    
    @Override
    public boolean isSatisfiedBy(Food food) {
        return food.getKcal() < maxCalories;
    }
    
    public static LowCalorieSpecification standard() {
        return new LowCalorieSpecification(400.0);
    }
    
    public static LowCalorieSpecification custom(double maxCalories) {
        return new LowCalorieSpecification(maxCalories);
    }
}

