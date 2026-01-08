package com.login_oop.oop_backend.controllers;

import com.login_oop.oop_backend.dto.BMIHistoryResponse;
import com.login_oop.oop_backend.models.BMIRecord;
import com.login_oop.oop_backend.services.BMIHistoryService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Controller สำหรับจัดการ BMI History
 */
@RestController
@CrossOrigin(origins = "*")
public class BMIHistoryController {
    
    private final BMIHistoryService bmiHistoryService;
    
    public BMIHistoryController(BMIHistoryService bmiHistoryService) {
        this.bmiHistoryService = bmiHistoryService;
    }
    
    /**
     * บันทึก BMI Record ใหม่
     * POST /api/bmi/history/record
     */
    @PostMapping("/api/bmi/history/record")
    public Map<String, Object> recordBMI(@RequestBody Map<String, Object> request) {
        try {
            String username = (String) request.get("username");
            double weight = ((Number) request.get("weight")).doubleValue();
            double height = ((Number) request.get("height")).doubleValue();
            
            BMIRecord record = bmiHistoryService.recordBMI(username, weight, height);
            
            return Map.of(
                "status", "success",
                "message", "บันทึก BMI สำเร็จ",
                "bmi", record.getBmi(),
                "category", record.getCategory(),
                "date", record.getDate().toString()
            );
        } catch (Exception e) {
            return Map.of(
                "status", "failed",
                "message", "เกิดข้อผิดพลาด: " + e.getMessage()
            );
        }
    }
    
    /**
     * ดึงประวัติ BMI ทั้งหมด
     * GET /api/bmi/history/{username}
     */
    @GetMapping("/api/bmi/history/{username}")
    public BMIHistoryResponse getBMIHistory(@PathVariable String username) {
        List<BMIRecord> records = bmiHistoryService.getBMIHistory(username);
        return BMIHistoryResponse.fromBMIRecords(username, records);
    }
    
    /**
     * ดึงประวัติ BMI ตามช่วงวันที่
     * GET /api/bmi/history/{username}/range?startDate=2024-01-01&endDate=2024-01-31
     */
    @GetMapping("/api/bmi/history/{username}/range")
    public BMIHistoryResponse getBMIHistoryByRange(
            @PathVariable String username,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        
        List<BMIRecord> records = bmiHistoryService.getBMIHistoryByDateRange(username, start, end);
        return BMIHistoryResponse.fromBMIRecords(username, records);
    }
    
    /**
     * ดึง BMI ล่าสุด
     * GET /api/bmi/history/{username}/latest
     */
    @GetMapping("/api/bmi/history/{username}/latest")
    public Object getLatestBMI(@PathVariable String username) {
        BMIRecord record = bmiHistoryService.getLatestBMI(username);
        if (record == null) {
            return Map.of("status", "not_found", "message", "ไม่พบข้อมูล BMI");
        }
        
        BMIHistoryResponse.BMIDataPoint dataPoint = 
            BMIHistoryResponse.BMIDataPoint.fromBMIRecord(record);
        
        return Map.of(
            "status", "success",
            "data", dataPoint
        );
    }
}


