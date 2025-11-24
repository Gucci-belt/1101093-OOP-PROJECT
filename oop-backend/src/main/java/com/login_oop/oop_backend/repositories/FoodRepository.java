package com.login_oop.oop_backend.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.login_oop.oop_backend.models.Food;

/**
 * 🎯 Repository Class สำหรับจัดการข้อมูลอาหาร
 * ใช้ OOP: Class + Methods + Data Structure (List)
 */
@Repository
public class FoodRepository {
    
    // Data Structure: ใช้ List เพื่อเก็บ Food objects
    private final List<Food> foodDatabase;

    // Constructor: สร้างข้อมูลอาหารเริ่มต้น
    public FoodRepository() {
        this.foodDatabase = new ArrayList<>();
        initializeFoods(); // เรียกใช้ method เพื่อสร้างข้อมูลเริ่มต้น
    }

    /**
     * 🎯 Method: สร้างข้อมูลอาหารเริ่มต้น
     * ใช้ OOP: Method ที่ทำงานภายใน class
     */
    private void initializeFoods() {
        foodDatabase.add(new Food("ข้าวมันไก่", 600, 25, 5, 700));
        foodDatabase.add(new Food("ข้าวผัดกะเพรา", 580, 22, 4, 850));
        foodDatabase.add(new Food("ข้าวขาหมู", 690, 40, 8, 900));
        foodDatabase.add(new Food("ผัดไทยกุ้งสด", 750, 30, 15, 1100));
        foodDatabase.add(new Food("สลัดอกไก่", 320, 10, 3, 400));
        foodDatabase.add(new Food("แกงเขียวหวานไก่", 450, 28, 7, 650));
        foodDatabase.add(new Food("ต้มยำกุ้ง", 350, 18, 6, 950));
        foodDatabase.add(new Food("ข้าวไข่เจียว", 400, 20, 2, 500));
        foodDatabase.add(new Food("ส้มตำไทย", 120, 2, 10, 700));
        foodDatabase.add(new Food("ลาบหมู", 250, 15, 3, 600));
        foodDatabase.add(new Food("ข้าวผัดหมู", 550, 20, 5, 750));
        foodDatabase.add(new Food("ก๋วยเตี๋ยวเรือ", 420, 12, 8, 1200));
        foodDatabase.add(new Food("ข้าวซอยไก่", 560, 30, 9, 800));
        foodDatabase.add(new Food("หมูปิ้ง (3 ไม้)", 300, 18, 10, 500));
        foodDatabase.add(new Food("ข้าวเหนียว (1 ห่อ)", 150, 1, 0, 10));
        foodDatabase.add(new Food("โจ๊กหมู", 250, 8, 2, 600));
        foodDatabase.add(new Food("ปลากะพงทอดน้ำปลา", 480, 30, 7, 900));
        foodDatabase.add(new Food("แกงจืดเต้าหู้หมูสับ", 220, 10, 3, 550));
        foodDatabase.add(new Food("ยำวุ้นเส้น", 180, 5, 8, 750));
        foodDatabase.add(new Food("น้ำเปล่า", 0, 0, 0, 5));
    }

    /**
     * 🎯 Method: ดึงรายการอาหารทั้งหมด
     * @return List ของ Food objects
     */
    public List<Food> findAll() {
        return new ArrayList<>(foodDatabase); // ส่งคืน copy เพื่อป้องกันการแก้ไขโดยตรง
    }

    /**
     * 🎯 Method: ค้นหาอาหารด้วยชื่อ
     * @param name ชื่ออาหาร
     * @return Food object หรือ null ถ้าไม่พบ
     */
    public Food findByName(String name) {
        // Linear Search: วนลูปหา food ที่มีชื่อตรงกัน
        for (Food food : foodDatabase) {
            if (food.getName().equals(name)) {
                return food;
            }
        }
        return null;
    }
}

