package com.login_oop.oop_backend.services;

import com.login_oop.oop_backend.models.Food;
import com.login_oop.oop_backend.repositories.FoodRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 🎯 Service Class สำหรับจัดการ Business Logic ของอาหาร
 * ใช้ OOP: Class + Methods + Dependency Injection
 */
@Service
public class FoodService {
    
    // Dependency Injection: FoodService ต้องใช้ FoodRepository
    private final FoodRepository foodRepository;

    // Constructor Injection (OOP: Dependency Injection)
    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    /**
     * 🎯 Method: ดึงรายการอาหารทั้งหมด
     * @return List ของ Food objects
     */
    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    /**
     * 🎯 Method: ค้นหาอาหารด้วยชื่อ
     * @param name ชื่ออาหาร
     * @return Food object หรือ null
     */
    public Food getFoodByName(String name) {
        return foodRepository.findByName(name);
    }
}

