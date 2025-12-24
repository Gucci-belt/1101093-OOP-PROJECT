package com.login_oop.oop_backend.services;

import com.login_oop.oop_backend.models.Food;
import com.login_oop.oop_backend.repositories.FoodRepository;
import org.springframework.stereotype.Service;

import java.util.List;

// Service สำหรับจัดการข้อมูลอาหาร
@Service
public class FoodService {

    // ต้องใช้ FoodRepository เพื่อดึงข้อมูลอาหาร
    private final FoodRepository foodRepository;

    // Constructor รับ FoodRepository เข้ามา
    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    /**
     * ดึงรายการอาหารทั้งหมด
     */
    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    /**
     * ค้นหาอาหารด้วยชื่อ
     */
    public Food getFoodByName(String name) {
        return foodRepository.findByName(name);
    }
}
