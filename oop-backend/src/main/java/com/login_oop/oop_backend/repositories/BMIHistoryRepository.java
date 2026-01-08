package com.login_oop.oop_backend.repositories;

import com.login_oop.oop_backend.models.BMIRecord;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Repository สำหรับจัดการข้อมูล BMI History
 * เก็บข้อมูลใน memory (สามารถเปลี่ยนเป็น Database ได้ในอนาคต)
 */
@Repository
public class BMIHistoryRepository {
    
    // เก็บประวัติ BMI ทั้งหมด
    private final List<BMIRecord> bmiHistoryDatabase;
    
    public BMIHistoryRepository() {
        this.bmiHistoryDatabase = new ArrayList<>();
    }
    
    /**
     * บันทึก BMI Record ใหม่
     * @param record BMIRecord ที่ต้องการบันทึก
     */
    public void save(BMIRecord record) {
        bmiHistoryDatabase.add(record);
    }
    
    /**
     * ดึงประวัติ BMI ทั้งหมดของ user
     * @param username ชื่อผู้ใช้
     * @return List ของ BMIRecord เรียงตามวันที่ (เก่า→ใหม่)
     */
    public List<BMIRecord> findByUsername(String username) {
        return bmiHistoryDatabase.stream()
                .filter(record -> record.getUsername().equals(username))
                .sorted(Comparator.comparing(BMIRecord::getTimestamp))
                .collect(Collectors.toList());
    }
    
    /**
     * ดึงประวัติ BMI ตามช่วงวันที่
     * @param username ชื่อผู้ใช้
     * @param startDate วันที่เริ่มต้น (รวม)
     * @param endDate วันที่สิ้นสุด (รวม)
     * @return List ของ BMIRecord ในช่วงเวลาที่กำหนด
     */
    public List<BMIRecord> findByUsernameAndDateRange(String username, 
                                                      LocalDate startDate, 
                                                      LocalDate endDate) {
        return bmiHistoryDatabase.stream()
                .filter(record -> record.getUsername().equals(username))
                .filter(record -> {
                    LocalDate recordDate = record.getDate();
                    return !recordDate.isBefore(startDate) && !recordDate.isAfter(endDate);
                })
                .sorted(Comparator.comparing(BMIRecord::getTimestamp))
                .collect(Collectors.toList());
    }
    
    /**
     * ดึงประวัติ BMI ตามวันที่เฉพาะ
     * @param username ชื่อผู้ใช้
     * @param date วันที่ที่ต้องการ
     * @return List ของ BMIRecord ในวันนั้น
     */
    public List<BMIRecord> findByUsernameAndDate(String username, LocalDate date) {
        return bmiHistoryDatabase.stream()
                .filter(record -> record.getUsername().equals(username))
                .filter(record -> record.getDate().equals(date))
                .sorted(Comparator.comparing(BMIRecord::getTimestamp))
                .collect(Collectors.toList());
    }
    
    /**
     * ดึง BMI Record ล่าสุดของ user
     * @param username ชื่อผู้ใช้
     * @return BMIRecord ล่าสุด หรือ null ถ้าไม่มี
     */
    public BMIRecord findLatestByUsername(String username) {
        return bmiHistoryDatabase.stream()
                .filter(record -> record.getUsername().equals(username))
                .max(Comparator.comparing(BMIRecord::getTimestamp))
                .orElse(null);
    }
    
    /**
     * ลบประวัติ BMI ตามช่วงวันที่
     * @param username ชื่อผู้ใช้
     * @param startDate วันที่เริ่มต้น
     * @param endDate วันที่สิ้นสุด
     * @return จำนวน records ที่ลบ
     */
    public int deleteByUsernameAndDateRange(String username, 
                                            LocalDate startDate, 
                                            LocalDate endDate) {
        int initialSize = bmiHistoryDatabase.size();
        bmiHistoryDatabase.removeIf(record -> 
            record.getUsername().equals(username) &&
            !record.getDate().isBefore(startDate) &&
            !record.getDate().isAfter(endDate)
        );
        return initialSize - bmiHistoryDatabase.size();
    }
    
    /**
     * ดึงจำนวน records ทั้งหมด (สำหรับ debugging)
     */
    public int getTotalRecords() {
        return bmiHistoryDatabase.size();
    }
}


