package com.login_oop.oop_backend.dto;

import com.login_oop.oop_backend.models.BMIRecord;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO สำหรับส่ง BMI History ไปยัง Frontend
 * รวมข้อมูลที่จำเป็นสำหรับ Plot Graph
 */
public class BMIHistoryResponse {
    
    private final List<BMIDataPoint> dataPoints;
    private final String username;
    
    public BMIHistoryResponse(String username, List<BMIDataPoint> dataPoints) {
        this.username = username;
        this.dataPoints = dataPoints;
    }
    
    public String getUsername() { 
        return username; 
    }
    
    public List<BMIDataPoint> getDataPoints() { 
        return dataPoints; 
    }
    
    /**
     * สร้าง BMIHistoryResponse จาก List<BMIRecord>
     */
    public static BMIHistoryResponse fromBMIRecords(String username, List<BMIRecord> records) {
        List<BMIDataPoint> dataPoints = records.stream()
                .map(BMIDataPoint::fromBMIRecord)
                .collect(Collectors.toList());
        return new BMIHistoryResponse(username, dataPoints);
    }
    
    /**
     * Inner class สำหรับเก็บข้อมูลแต่ละจุดใน Graph
     */
    public static class BMIDataPoint {
        private final String date;        // วันที่ (format: "YYYY-MM-DD")
        private final String timestamp;   // เวลาเต็ม (format: "YYYY-MM-DD HH:mm:ss")
        private final double bmi;         // ค่า BMI
        private final double weight;      // น้ำหนัก
        private final double height;      // ส่วนสูง
        private final String category;    // หมวดหมู่
        private final double recommendedCalories; // แคลอรี่ที่แนะนำ
        
        public BMIDataPoint(String date, String timestamp, double bmi, 
                           double weight, double height, String category, 
                           double recommendedCalories) {
            this.date = date;
            this.timestamp = timestamp;
            this.bmi = bmi;
            this.weight = weight;
            this.height = height;
            this.category = category;
            this.recommendedCalories = recommendedCalories;
        }
        
        // Getters
        public String getDate() { 
            return date; 
        }
        
        public String getTimestamp() { 
            return timestamp; 
        }
        
        public double getBmi() { 
            return bmi; 
        }
        
        public double getWeight() { 
            return weight; 
        }
        
        public double getHeight() { 
            return height; 
        }
        
        public String getCategory() { 
            return category; 
        }
        
        public double getRecommendedCalories() { 
            return recommendedCalories; 
        }
        
        /**
         * สร้าง BMIDataPoint จาก BMIRecord
         */
        public static BMIDataPoint fromBMIRecord(BMIRecord record) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timestampFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            
            return new BMIDataPoint(
                record.getDate().format(dateFormatter),
                record.getTimestamp().format(timestampFormatter),
                record.getBmi(),
                record.getWeight(),
                record.getHeight(),
                record.getCategory(),
                record.getRecommendedCalories()
            );
        }
    }
}


