package com.login_oop.oop_backend.filtering.impl;

import com.login_oop.oop_backend.filtering.FoodSpecification;
import com.login_oop.oop_backend.models.Food;

/**
 * Specification for High Fat foods
 * Default threshold: >= 20g
 */
public class HighFatSpecification implements FoodSpecification {
    private final double minFat;
    
    public HighFatSpecification(double minFat) {
        this.minFat = minFat;
    }
    
    @Override
    public boolean isSatisfiedBy(Food food) {
        return food.getFat() >= minFat;
    }
    
    public static HighFatSpecification standard() {
        return new HighFatSpecification(20.0);
    }
    
    public static HighFatSpecification custom(double minFat) {
        return new HighFatSpecification(minFat);
    }
}

