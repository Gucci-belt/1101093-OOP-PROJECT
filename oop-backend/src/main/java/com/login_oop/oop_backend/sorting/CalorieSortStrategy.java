package com.login_oop.oop_backend.sorting;

import com.login_oop.oop_backend.models.Food;
import java.util.ArrayList;
import java.util.List;

/**
 * เรียงลำดับอาหารตามแคลอรี่
 */
public class CalorieSortStrategy implements FoodSortStrategy {
    
    private final boolean ascending; // true = น้อยไปมาก, false = มากไปน้อย
    
    /**
     * @param ascending true = เรียงน้อยไปมาก, false = เรียงมากไปน้อย
     */
    public CalorieSortStrategy(boolean ascending) {
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
        int result = Double.compare(a.getKcal(), b.getKcal());
        return ascending ? result : -result; // ถ้าไม่ ascending ให้กลับเครื่องหมาย
    }
    
    /**
     * Factory methods
     */
    public static CalorieSortStrategy ascending() {
        return new CalorieSortStrategy(true);
    }
    
    public static CalorieSortStrategy descending() {
        return new CalorieSortStrategy(false);
    }
}


