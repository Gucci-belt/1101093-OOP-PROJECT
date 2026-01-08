package com.login_oop.oop_backend.filtering.impl;

import com.login_oop.oop_backend.filtering.FoodSpecification;
import com.login_oop.oop_backend.models.Food;

/**
 * Specification for Low Sodium foods
 * Default threshold: < 500mg
 */
public class LowSodiumSpecification implements FoodSpecification {
    private final double maxSodium;
    
    public LowSodiumSpecification(double maxSodium) {
        this.maxSodium = maxSodium;
    }
    
    @Override
    public boolean isSatisfiedBy(Food food) {
        return food.getSodium() < maxSodium;
    }
    
    public static LowSodiumSpecification standard() {
        return new LowSodiumSpecification(500.0);
    }
    
    public static LowSodiumSpecification custom(double maxSodium) {
        return new LowSodiumSpecification(maxSodium);
    }
}

