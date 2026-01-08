package com.login_oop.oop_backend.filtering.impl;

import com.login_oop.oop_backend.filtering.FoodSpecification;
import com.login_oop.oop_backend.models.Food;

/**
 * Specification for Low Sugar foods
 * Default threshold: < 5g
 */
public class LowSugarSpecification implements FoodSpecification {
    private final double maxSugar;
    
    public LowSugarSpecification(double maxSugar) {
        this.maxSugar = maxSugar;
    }
    
    @Override
    public boolean isSatisfiedBy(Food food) {
        return food.getSugar() < maxSugar;
    }
    
    public static LowSugarSpecification standard() {
        return new LowSugarSpecification(5.0);
    }
    
    public static LowSugarSpecification custom(double maxSugar) {
        return new LowSugarSpecification(maxSugar);
    }
}

