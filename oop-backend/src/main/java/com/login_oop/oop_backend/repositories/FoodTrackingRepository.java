package com.login_oop.oop_backend.repositories;

import com.login_oop.oop_backend.models.FoodTracking;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Repository สำหรับจัดการข้อมูลการติดตามอาหาร
@Repository
public class FoodTrackingRepository {
    
    // เก็บข้อมูล tracking ทั้งหมด
    private final List<FoodTracking> trackingDatabase;

    // Constructor
    public FoodTrackingRepository() {
        this.trackingDatabase = new ArrayList<>();
    }

    /**
     * เพิ่มข้อมูล tracking
     */
    public void addTracking(FoodTracking tracking) {
        trackingDatabase.add(tracking);
    }

    /**
     * ดึง tracking ของผู้ใช้ตาม username
     */
    public List<FoodTracking> findByUsername(String username) {
        return trackingDatabase.stream()
                .filter(tracking -> tracking.getUsername().equals(username))
                .collect(Collectors.toList());
    }

    /**
     * ดึง tracking ของผู้ใช้ตาม username และวันที่
     */
    public List<FoodTracking> findByUsernameAndDate(String username, String dateKey) {
        return trackingDatabase.stream()
                .filter(tracking -> tracking.getUsername().equals(username))
                .filter(tracking -> tracking.getDateKey().equals(dateKey))
                .collect(Collectors.toList());
    }

    /**
     * ดึง tracking ของผู้ใช้ตาม username และช่วงวันที่
     */
    public List<FoodTracking> findByUsernameAndDateRange(String username, String startDate, String endDate) {
        return trackingDatabase.stream()
                .filter(tracking -> tracking.getUsername().equals(username))
                .filter(tracking -> {
                    String dateKey = tracking.getDateKey();
                    return dateKey.compareTo(startDate) >= 0 && dateKey.compareTo(endDate) <= 0;
                })
                .collect(Collectors.toList());
    }

    /**
     * ลบ tracking ของผู้ใช้ตามวันที่
     */
    public void deleteByUsernameAndDate(String username, String dateKey) {
        trackingDatabase.removeIf(tracking -> 
            tracking.getUsername().equals(username) && 
            tracking.getDateKey().equals(dateKey)
        );
    }

    /**
     * นับจำนวน tracking ของผู้ใช้ตามวันที่
     */
    public int countByUsernameAndDate(String username, String dateKey) {
        return (int) trackingDatabase.stream()
                .filter(tracking -> tracking.getUsername().equals(username))
                .filter(tracking -> tracking.getDateKey().equals(dateKey))
                .count();
    }
}

