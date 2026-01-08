package com.login_oop.oop_backend.services;

import com.login_oop.oop_backend.filtering.FoodSpecification;
import com.login_oop.oop_backend.models.Food;
import com.login_oop.oop_backend.repositories.FoodRepository;
import com.login_oop.oop_backend.sorting.FoodSortStrategy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service สำหรับจัดการการกรองและเรียงลำดับอาหาร
 */
@Service
public class FoodFilterService {
    
    private final FoodRepository foodRepository;
    
    public FoodFilterService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }
    
    /**
     * กรองและเรียงลำดับอาหาร
     * @param filter Filter specification (null = ไม่กรอง)
     * @param sortStrategy Sorting strategy (null = ไม่เรียง)
     * @return รายการอาหารที่กรองและเรียงลำดับแล้ว
     */
    public List<Food> filterAndSort(FoodSpecification filter, FoodSortStrategy sortStrategy) {
        List<Food> foods = foodRepository.findAll();
        
        // กรองอาหาร
        if (filter != null) {
            foods = foods.stream()
                    .filter(filter::isSatisfiedBy)
                    .collect(Collectors.toList());
        }
        
        // เรียงลำดับ
        if (sortStrategy != null) {
            foods = sortStrategy.sort(foods);
        }
        
        return foods;
    }
    
    /**
     * เรียงลำดับอาหาร (ไม่กรอง)
     * @param sortStrategy Strategy สำหรับเรียงลำดับ
     * @return รายการอาหารที่เรียงลำดับแล้ว
     */
    public List<Food> sort(FoodSortStrategy sortStrategy) {
        return filterAndSort(null, sortStrategy);
    }
    
    /**
     * กรองอาหาร (ไม่เรียงลำดับ)
     * @param filter Filter specification
     * @return รายการอาหารที่กรองแล้ว
     */
    public List<Food> filter(FoodSpecification filter) {
        return filterAndSort(filter, null);
    }
}


