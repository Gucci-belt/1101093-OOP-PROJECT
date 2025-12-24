package com.login_oop.oop_backend.controllers;

import com.login_oop.oop_backend.models.Food;
import com.login_oop.oop_backend.services.FoodService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

// Controller สำหรับจัดการข้อมูลอาหาร
@RestController
@CrossOrigin(origins = "*")
public class FoodController {

    // ต้องใช้ FoodService เพื่อดึงข้อมูลอาหาร
    private final FoodService foodService;

    // Constructor รับ FoodService เข้ามา
    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    /**
     * API สำหรับดึงรายการอาหารทั้งหมด
     * เรียกผ่าน GET /api/foods
     */
    @GetMapping("/api/foods")
    public List<Food> getAllFoods() {
        return foodService.getAllFoods();
    }

    /**
     * API สำหรับค้นหาอาหารด้วยชื่อ
     * เรียกผ่าน GET /api/foods/{name}
     */
    @GetMapping("/api/foods/{name}")
    public Object getFoodByName(@PathVariable String name) {
        Food food = foodService.getFoodByName(name);
        if (food != null) {
            return food;
        }
        // ถ้าไม่เจอ ส่ง error message กลับไป
        return Map.of("error", "ไม่พบอาหารที่ชื่อ: " + name);
    }
}
