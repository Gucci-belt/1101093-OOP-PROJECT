package com.login_oop.oop_backend.controllers;

import com.login_oop.oop_backend.models.Food;
import com.login_oop.oop_backend.services.FoodService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 🎯 Controller Class สำหรับจัดการ HTTP Requests เกี่ยวกับอาหาร
 * ใช้ OOP: Class + Methods + REST API
 */
@RestController
@CrossOrigin(origins = "*")
public class FoodController {

    // Dependency Injection: FoodController ต้องใช้ FoodService
    private final FoodService foodService;

    // Constructor Injection
    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    /**
     * 🎯 API Endpoint: ดึงรายการอาหารทั้งหมด
     * GET /api/foods
     * @return List ของ Food objects
     */
    @GetMapping("/api/foods")
    public List<Food> getAllFoods() {
        return foodService.getAllFoods();
    }

    /**
     * 🎯 API Endpoint: ค้นหาอาหารด้วยชื่อ
     * GET /api/foods/{name}
     * @param name ชื่ออาหาร
     * @return Food object หรือ error message
     */
    @GetMapping("/api/foods/{name}")
    public Object getFoodByName(@PathVariable String name) {
        Food food = foodService.getFoodByName(name);
        if (food != null) {
            return food;
        }
        return Map.of("error", "ไม่พบอาหารที่ชื่อ: " + name);
    }
}

