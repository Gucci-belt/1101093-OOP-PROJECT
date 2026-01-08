package com.login_oop.oop_backend.filtering.impl;

import com.login_oop.oop_backend.filtering.FoodSpecification;
import com.login_oop.oop_backend.models.Food;

/**
 * Specification for High Sugar foods
 * Default threshold: >= 10g
 */
public class HighSugarSpecification implements FoodSpecification {
    private final double minSugar;
    
    public HighSugarSpecification(double minSugar) {
        this.minSugar = minSugar;
    }
    
    @Override
    public boolean isSatisfiedBy(Food food) {
        return food.getSugar() >= minSugar;
    }
    
    public static HighSugarSpecification standard() {
        return new HighSugarSpecification(10.0);
    }
    
    public static HighSugarSpecification custom(double minSugar) {
        return new HighSugarSpecification(minSugar);
    }
}

