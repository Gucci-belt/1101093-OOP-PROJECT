package com.login_oop.oop_backend.sorting;

import com.login_oop.oop_backend.models.Food;
import java.util.List;

/**
 * Interface สำหรับการเรียงลำดับอาหาร
 * ใช้ Strategy Pattern เพื่อให้สามารถเพิ่มวิธีเรียงลำดับได้ง่าย
 */
public interface FoodSortStrategy {
    /**
     * เรียงลำดับอาหาร
     * @param foods รายการอาหารที่ต้องการเรียงลำดับ
     * @return รายการอาหารที่เรียงลำดับแล้ว
     */
    List<Food> sort(List<Food> foods);
    
    /**
     * เปรียบเทียบอาหาร 2 รายการ
     * @param a อาหารรายการแรก
     * @param b อาหารรายการที่สอง
     * @return ค่า < 0 ถ้า a < b, > 0 ถ้า a > b, 0 ถ้า a == b
     */
    int compare(Food a, Food b);
}


