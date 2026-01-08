package com.login_oop.oop_backend.sorting;

import com.login_oop.oop_backend.models.Food;
import java.util.ArrayList;
import java.util.List;

/**
 * เรียงลำดับอาหารตามโซเดียม
 */
public class SodiumSortStrategy implements FoodSortStrategy {
    
    private final boolean ascending;
    
    public SodiumSortStrategy(boolean ascending) {
        this.ascending = ascending;
    }
    
    @Override
    public List<Food> sort(List<Food> foods) {
        List<Food> sorted = new ArrayList<>(foods);
        sorted.sort(this::compare);
        return sorted;
    }
    
    @Override
    public int compare(Food a, Food b) {
        int result = Double.compare(a.getSodium(), b.getSodium());
        return ascending ? result : -result;
    }
    
    public static SodiumSortStrategy ascending() {
        return new SodiumSortStrategy(true);
    }
    
    public static SodiumSortStrategy descending() {
        return new SodiumSortStrategy(false);
    }
}


