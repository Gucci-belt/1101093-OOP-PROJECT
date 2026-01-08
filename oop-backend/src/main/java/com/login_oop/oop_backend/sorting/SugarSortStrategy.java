package com.login_oop.oop_backend.sorting;

import com.login_oop.oop_backend.models.Food;
import java.util.ArrayList;
import java.util.List;

/**
 * เรียงลำดับอาหารตามน้ำตาล
 */
public class SugarSortStrategy implements FoodSortStrategy {
    
    private final boolean ascending;
    
    public SugarSortStrategy(boolean ascending) {
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
        int result = Double.compare(a.getSugar(), b.getSugar());
        return ascending ? result : -result;
    }
    
    public static SugarSortStrategy ascending() {
        return new SugarSortStrategy(true);
    }
    
    public static SugarSortStrategy descending() {
        return new SugarSortStrategy(false);
    }
}


