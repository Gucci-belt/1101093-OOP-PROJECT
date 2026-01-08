package com.login_oop.oop_backend.filtering.impl;

import com.login_oop.oop_backend.filtering.FoodSpecification;
import com.login_oop.oop_backend.models.Food;

/**
 * Specification for High Sodium foods
 * Default threshold: >= 800mg
 */
public class HighSodiumSpecification implements FoodSpecification {
    private final double minSodium;
    
    public HighSodiumSpecification(double minSodium) {
        this.minSodium = minSodium;
    }
    
    @Override
    public boolean isSatisfiedBy(Food food) {
        return food.getSodium() >= minSodium;
    }
    
    public static HighSodiumSpecification standard() {
        return new HighSodiumSpecification(800.0);
    }
    
    public static HighSodiumSpecification custom(double minSodium) {
        return new HighSodiumSpecification(minSodium);
    }
}

