package com.login_oop.oop_backend.controllers;

import com.login_oop.oop_backend.dto.FoodRequest;
import com.login_oop.oop_backend.filtering.FilterFactory;
import com.login_oop.oop_backend.filtering.FoodSpecification;
import com.login_oop.oop_backend.models.Food;
import com.login_oop.oop_backend.services.FoodFilterService;
import com.login_oop.oop_backend.services.FoodService;
import com.login_oop.oop_backend.sorting.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Controller สำหรับจัดการข้อมูลอาหาร
@RestController
@CrossOrigin(origins = "*")
public class FoodController {

    // ต้องใช้ FoodService และ FoodFilterService
    private final FoodService foodService;
    private final FoodFilterService foodFilterService;

    // Constructor รับ Services เข้ามา
    public FoodController(FoodService foodService, FoodFilterService foodFilterService) {
        this.foodService = foodService;
        this.foodFilterService = foodFilterService;
    }

    /**
     * API สำหรับดึงรายการอาหารทั้งหมด
     * เรียกผ่าน GET /api/foods
     * 
     * Query Parameters (Optional):
     * - filter: "lowCalorie", "highCalorie", "lowSugar", "highSugar", "lowFat", "highFat", "lowSodium", "highSodium"
     *           สามารถส่งหลายค่าได้โดยคั่นด้วย comma เช่น "lowCalorie,lowSugar"
     * - calorieMin, calorieMax: ช่วงแคลอรี่ (min, max)
     * - sugarMin, sugarMax: ช่วงน้ำตาล (min, max)
     * - fatMin, fatMax: ช่วงไขมัน (min, max)
     * - sodiumMin, sodiumMax: ช่วงโซเดียม (min, max)
     * - sortBy: "calorie", "fat", "sugar", "sodium"
     * - sortOrder: "asc" (น้อยไปมาก), "desc" (มากไปน้อย)
     */
    @GetMapping("/api/foods")
    public List<Food> getAllFoods(
            @RequestParam(required = false) String filter,
            @RequestParam(required = false) Double calorieMin,
            @RequestParam(required = false) Double calorieMax,
            @RequestParam(required = false) Double sugarMin,
            @RequestParam(required = false) Double sugarMax,
            @RequestParam(required = false) Double fatMin,
            @RequestParam(required = false) Double fatMax,
            @RequestParam(required = false) Double sodiumMin,
            @RequestParam(required = false) Double sodiumMax,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortOrder) {
        
        // สร้าง Filter จาก range parameters
        FoodSpecification rangeFilter = createRangeFilter(
            calorieMin, calorieMax,
            sugarMin, sugarMax,
            fatMin, fatMax,
            sodiumMin, sodiumMax
        );
        
        // สร้าง Filter จาก filter string (สำหรับ backward compatibility)
        FoodSpecification stringFilter = createFilter(filter);
        
        // รวม filters ด้วย AND logic
        FoodSpecification combinedFilter = null;
        if (rangeFilter != null && stringFilter != null) {
            combinedFilter = rangeFilter.and(stringFilter);
        } else if (rangeFilter != null) {
            combinedFilter = rangeFilter;
        } else if (stringFilter != null) {
            combinedFilter = stringFilter;
        }
        
        // สร้าง Sort Strategy (ถ้ามี)
        FoodSortStrategy sortStrategy = createSortStrategy(sortBy, sortOrder);
        
        // กรองและเรียงลำดับ
        if (combinedFilter != null || sortStrategy != null) {
            return foodFilterService.filterAndSort(combinedFilter, sortStrategy);
        }
        
        // ถ้าไม่มี filter และ sort ให้ใช้ service เดิม
        return foodService.getAllFoods();
    }
    
    /**
     * สร้าง Range Filter จาก min/max parameters
     */
    private FoodSpecification createRangeFilter(
            Double calorieMin, Double calorieMax,
            Double sugarMin, Double sugarMax,
            Double fatMin, Double fatMax,
            Double sodiumMin, Double sodiumMax) {
        
        List<FoodSpecification> specs = new ArrayList<>();
        
        if (calorieMin != null || calorieMax != null) {
            specs.add(com.login_oop.oop_backend.filtering.impl.RangeFilter.calorieRange(
                calorieMin != null ? calorieMin : -1,
                calorieMax != null ? calorieMax : -1
            ));
        }
        
        if (sugarMin != null || sugarMax != null) {
            specs.add(com.login_oop.oop_backend.filtering.impl.RangeFilter.sugarRange(
                sugarMin != null ? sugarMin : -1,
                sugarMax != null ? sugarMax : -1
            ));
        }
        
        if (fatMin != null || fatMax != null) {
            specs.add(com.login_oop.oop_backend.filtering.impl.RangeFilter.fatRange(
                fatMin != null ? fatMin : -1,
                fatMax != null ? fatMax : -1
            ));
        }
        
        if (sodiumMin != null || sodiumMax != null) {
            specs.add(com.login_oop.oop_backend.filtering.impl.RangeFilter.sodiumRange(
                sodiumMin != null ? sodiumMin : -1,
                sodiumMax != null ? sodiumMax : -1
            ));
        }
        
        if (specs.isEmpty()) {
            return null;
        }
        
        // รวม filters ด้วย AND logic
        FoodSpecification combined = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            combined = combined.and(specs.get(i));
        }
        return combined;
    }
    
    /**
     * สร้าง Filter Specification จาก filter string
     * รองรับหลาย filter โดยคั่นด้วย comma และใช้ AND logic
     */
    private FoodSpecification createFilter(String filter) {
        if (filter == null || filter.isEmpty()) {
            return null;
        }
        
        // แยก filter types ด้วย comma
        String[] filterTypes = filter.split(",");
        List<FoodSpecification> specifications = new ArrayList<>();
        
        for (String filterType : filterTypes) {
            FoodSpecification spec = FilterFactory.createFilter(filterType.trim());
            if (spec != null) {
                specifications.add(spec);
            }
        }
        
        if (specifications.isEmpty()) {
            return null;
        }
        
        // ถ้ามี filter เดียว ให้คืนค่าเดียว
        if (specifications.size() == 1) {
            return specifications.get(0);
        }
        
        // ถ้ามีหลาย filter ให้ใช้ AND logic
        FoodSpecification combined = specifications.get(0);
        for (int i = 1; i < specifications.size(); i++) {
            combined = combined.and(specifications.get(i));
        }
        return combined;
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
    
    /**
     * สร้าง Sort Strategy ตาม sortBy และ sortOrder
     */
    private FoodSortStrategy createSortStrategy(String sortBy, String sortOrder) {
        if (sortBy == null || sortBy.isEmpty()) {
            return null;
        }
        
        boolean ascending = !"desc".equalsIgnoreCase(sortOrder);
        
        switch (sortBy.toLowerCase()) {
            case "calorie":
            case "calories":
            case "kcal":
                return ascending ? CalorieSortStrategy.ascending() : CalorieSortStrategy.descending();
            case "fat":
                return ascending ? FatSortStrategy.ascending() : FatSortStrategy.descending();
            case "sugar":
                return ascending ? SugarSortStrategy.ascending() : SugarSortStrategy.descending();
            case "sodium":
                return ascending ? SodiumSortStrategy.ascending() : SodiumSortStrategy.descending();
            default:
                return null;
        }
    }

    /**
     * API สำหรับเพิ่มอาหารใหม่
     * เรียกผ่าน POST /api/foods/add
     */
    @PostMapping("/api/foods/add")
    public Map<String, String> addFood(@RequestBody(required = false) FoodRequest request) {
        // เช็คว่ามี request body หรือเปล่า
        if (request == null) {
            return Map.of("status", "failed", "message", "Request body is missing");
        }
        
        // เช็คข้อมูล
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            return Map.of("status", "failed", "message", "ชื่อเมนูต้องไม่ว่าง");
        }
        
        if (request.getKcal() < 0 || request.getFat() < 0 || request.getSugar() < 0 || request.getSodium() < 0) {
            return Map.of("status", "failed", "message", "ค่าสารอาหารไม่สามารถเป็นจำนวนลบได้");
        }
        
        // สร้าง Food object
        Food newFood = new Food(
            request.getName().trim(),
            request.getKcal(),
            request.getFat(),
            request.getSugar(),
            request.getSodium()
        );
        
        // เพิ่มอาหารผ่าน service
        boolean success = foodService.addFood(newFood);
        if (success) {
            return Map.of("status", "success", "message", "เพิ่มเมนูอาหารเรียบร้อย");
        }
        return Map.of("status", "failed", "message", "มีเมนูชื่อนี้อยู่แล้ว");
    }

    /**
     * API สำหรับลบอาหาร
     * เรียกผ่าน DELETE /api/foods/{name}
     */
    @DeleteMapping("/api/foods/{name}")
    public Map<String, String> deleteFood(@PathVariable String name) {
        // ลบอาหารผ่าน service
        boolean success = foodService.deleteFood(name);
        if (success) {
            return Map.of("status", "success", "message", "ลบเมนูอาหารเรียบร้อย");
        }
        return Map.of("status", "failed", "message", "ไม่พบเมนูที่ต้องการลบ");
    }
}
