package com.login_oop.oop_backend.sorting;

import com.login_oop.oop_backend.models.Food;
import java.util.ArrayList;
import java.util.List;

/**
 * เรียงลำดับอาหารตามไขมัน
 */
public class FatSortStrategy implements FoodSortStrategy {
    
    private final boolean ascending;
    
    public FatSortStrategy(boolean ascending) {
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
        int result = Double.compare(a.getFat(), b.getFat());
        return ascending ? result : -result;
    }
    
    public static FatSortStrategy ascending() {
        return new FatSortStrategy(true);
    }
    
    public static FatSortStrategy descending() {
        return new FatSortStrategy(false);
    }
}


