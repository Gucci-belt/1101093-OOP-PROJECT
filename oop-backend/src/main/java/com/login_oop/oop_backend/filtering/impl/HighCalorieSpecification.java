package com.login_oop.oop_backend.filtering.impl;

import com.login_oop.oop_backend.filtering.FoodSpecification;
import com.login_oop.oop_backend.models.Food;

/**
 * Specification for High Calorie foods
 * Default threshold: >= 500 kcal
 */
public class HighCalorieSpecification implements FoodSpecification {
    private final double minCalories;
    
    public HighCalorieSpecification(double minCalories) {
        this.minCalories = minCalories;
    }
    
    @Override
    public boolean isSatisfiedBy(Food food) {
        return food.getKcal() >= minCalories;
    }
    
    public static HighCalorieSpecification standard() {
        return new HighCalorieSpecification(500.0);
    }
    
    public static HighCalorieSpecification custom(double minCalories) {
        return new HighCalorieSpecification(minCalories);
    }
}

