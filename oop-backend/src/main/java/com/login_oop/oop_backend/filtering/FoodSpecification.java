package com.login_oop.oop_backend.filtering;

import com.login_oop.oop_backend.models.Food;
import java.util.function.Predicate;

/**
 * Specification Pattern for Food Filtering
 * 
 * Design Pattern: Specification Pattern
 * - Allows building complex filtering criteria
 * - Supports composition with AND, OR, NOT operations
 * - Makes filtering logic reusable and testable
 */
public interface FoodSpecification {
    /**
     * Test if a food item matches this specification
     * @param food The food item to test
     * @return true if the food matches the specification
     */
    boolean isSatisfiedBy(Food food);
    
    /**
     * Convert to Java Predicate for use with Streams API
     */
    default Predicate<Food> toPredicate() {
        return this::isSatisfiedBy;
    }
    
    /**
     * Combine two specifications with AND logic
     */
    default FoodSpecification and(FoodSpecification other) {
        return food -> this.isSatisfiedBy(food) && other.isSatisfiedBy(food);
    }
    
    /**
     * Combine two specifications with OR logic
     */
    default FoodSpecification or(FoodSpecification other) {
        return food -> this.isSatisfiedBy(food) || other.isSatisfiedBy(food);
    }
    
    /**
     * Negate this specification (NOT logic)
     */
    default FoodSpecification not() {
        return food -> !this.isSatisfiedBy(food);
    }
}

