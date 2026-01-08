package com.login_oop.oop_backend.services;

import com.login_oop.oop_backend.models.BMIRecord;
import com.login_oop.oop_backend.repositories.BMIHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service สำหรับจัดการ BMI History
 */
@Service
public class BMIHistoryService {
    
    private final BMIHistoryRepository bmiHistoryRepository;
    private final BMIService bmiService; // ใช้สำหรับคำนวณ BMI
    
    public BMIHistoryService(BMIHistoryRepository bmiHistoryRepository, 
                            BMIService bmiService) {
        this.bmiHistoryRepository = bmiHistoryRepository;
        this.bmiService = bmiService;
    }
    
    /**
     * บันทึก BMI Record ใหม่
     * @param username ชื่อผู้ใช้
     * @param weight น้ำหนัก (kg)
     * @param height ส่วนสูง (cm)
     * @return BMIRecord ที่สร้างใหม่
     */
    public BMIRecord recordBMI(String username, double weight, double height) {
        // คำนวณ BMI ผ่าน BMIService
        var bmiResponse = bmiService.calculateBMI(username, weight, height);
        
        // สร้าง BMIRecord
        BMIRecord record = new BMIRecord(
            username,
            weight,
            height,
            bmiResponse.getBmi(),
            bmiResponse.getCategory(),
            bmiResponse.getCalories(), // ใช้ getCalories() แทน getRecommendedCalories()
            LocalDateTime.now()
        );
        
        // บันทึกใน History
        bmiHistoryRepository.save(record);
        
        return record;
    }
    
    /**
     * ดึงประวัติ BMI ทั้งหมดของ user
     * @param username ชื่อผู้ใช้
     * @return List ของ BMIRecord เรียงตามวันที่ (เก่า→ใหม่)
     */
    public List<BMIRecord> getBMIHistory(String username) {
        return bmiHistoryRepository.findByUsername(username);
    }
    
    /**
     * ดึงประวัติ BMI ตามช่วงวันที่
     * @param username ชื่อผู้ใช้
     * @param startDate วันที่เริ่มต้น
     * @param endDate วันที่สิ้นสุด
     * @return List ของ BMIRecord ในช่วงเวลาที่กำหนด
     */
    public List<BMIRecord> getBMIHistoryByDateRange(String username, 
                                                     LocalDate startDate, 
                                                     LocalDate endDate) {
        return bmiHistoryRepository.findByUsernameAndDateRange(username, startDate, endDate);
    }
    
    /**
     * ดึง BMI ล่าสุดของ user
     * @param username ชื่อผู้ใช้
     * @return BMIRecord ล่าสุด หรือ null ถ้าไม่มี
     */
    public BMIRecord getLatestBMI(String username) {
        return bmiHistoryRepository.findLatestByUsername(username);
    }
    
    /**
     * ดึงข้อมูลสำหรับ Plot Graph
     * ส่งคืน List ของ BMI values และ Dates
     * @param username ชื่อผู้ใช้
     * @param startDate วันที่เริ่มต้น (ถ้า null = ดึงทั้งหมด)
     * @param endDate วันที่สิ้นสุด (ถ้า null = ดึงทั้งหมด)
     * @return List ของ BMIRecord สำหรับ plot
     */
    public List<BMIRecord> getBMIForGraph(String username, 
                                          LocalDate startDate, 
                                          LocalDate endDate) {
        if (startDate != null && endDate != null) {
            return getBMIHistoryByDateRange(username, startDate, endDate);
        } else {
            return getBMIHistory(username);
        }
    }
    
    /**
     * ลบประวัติ BMI ตามช่วงวันที่
     * @param username ชื่อผู้ใช้
     * @param startDate วันที่เริ่มต้น
     * @param endDate วันที่สิ้นสุด
     * @return จำนวน records ที่ลบ
     */
    public int deleteBMIHistory(String username, LocalDate startDate, LocalDate endDate) {
        return bmiHistoryRepository.deleteByUsernameAndDateRange(username, startDate, endDate);
    }
}

