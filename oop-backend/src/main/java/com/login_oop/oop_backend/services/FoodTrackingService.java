package com.login_oop.oop_backend.services;

import com.login_oop.oop_backend.models.Food;
import com.login_oop.oop_backend.models.FoodTracking;
import com.login_oop.oop_backend.repositories.FoodRepository;
import com.login_oop.oop_backend.repositories.FoodTrackingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

// Service สำหรับจัดการการติดตามอาหาร
@Service
public class FoodTrackingService {
    
    private final FoodTrackingRepository trackingRepository;
    private final FoodRepository foodRepository;

    // Constructor
    public FoodTrackingService(FoodTrackingRepository trackingRepository, FoodRepository foodRepository) {
        this.trackingRepository = trackingRepository;
        this.foodRepository = foodRepository;
    }

    /**
     * เพิ่ม tracking เมื่อผู้ใช้ทานอาหาร
     */
    public boolean addTracking(String username, String foodName) {
        // หาอาหารจากชื่อ
        Food food = foodRepository.findByName(foodName);
        if (food != null) {
            // สร้าง FoodTracking
            FoodTracking tracking = new FoodTracking(
                username,
                food.getName(),
                food.getKcal(),
                food.getFat(),
                food.getSugar(),
                food.getSodium(),
                LocalDateTime.now()
            );
            // เพิ่มลง database
            trackingRepository.addTracking(tracking);
            return true;
        }
        return false; // ไม่เจออาหาร
    }

    /**
     * ดึง tracking ของผู้ใช้ตามวันที่
     */
    public List<FoodTracking> getTrackingByDate(String username, String dateKey) {
        return trackingRepository.findByUsernameAndDate(username, dateKey);
    }

    /**
     * ดึง tracking ทั้งหมดของผู้ใช้
     */
    public List<FoodTracking> getAllTracking(String username) {
        return trackingRepository.findByUsername(username);
    }

    /**
     * ดึง tracking ของผู้ใช้ตามช่วงวันที่
     */
    public List<FoodTracking> getTrackingByDateRange(String username, String startDate, String endDate) {
        return trackingRepository.findByUsernameAndDateRange(username, startDate, endDate);
    }

    /**
     * ลบ tracking ของผู้ใช้ตามวันที่
     */
    public void deleteTrackingByDate(String username, String dateKey) {
        trackingRepository.deleteByUsernameAndDate(username, dateKey);
    }
}

