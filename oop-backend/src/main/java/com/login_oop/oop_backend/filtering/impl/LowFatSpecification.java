package com.login_oop.oop_backend.filtering.impl;

import com.login_oop.oop_backend.filtering.FoodSpecification;
import com.login_oop.oop_backend.models.Food;

/**
 * Specification for Low Fat foods
 * Default threshold: < 10g
 */
public class LowFatSpecification implements FoodSpecification {
    private final double maxFat;
    
    public LowFatSpecification(double maxFat) {
        this.maxFat = maxFat;
    }
    
    @Override
    public boolean isSatisfiedBy(Food food) {
        return food.getFat() < maxFat;
    }
    
    public static LowFatSpecification standard() {
        return new LowFatSpecification(10.0);
    }
    
    public static LowFatSpecification custom(double maxFat) {
        return new LowFatSpecification(maxFat);
    }
}

