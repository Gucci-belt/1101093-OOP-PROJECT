package com.login_oop.oop_backend.repositories;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.login_oop.oop_backend.models.Food;

/**
 * Repository สำหรับจัดการข้อมูลอาหาร
 * อ่านข้อมูลจากไฟล์ CSV แทนการ hardcode
 */
@Repository
public class FoodRepository {
    
    // เก็บอาหารทั้งหมด
    private final List<Food> foodDatabase;

    /**
     * Constructor จะอ่านข้อมูลอาหารจากไฟล์ CSV
     * ใช้ File Input และ Error Handling ตาม OOP requirements
     */
    public FoodRepository() {
        this.foodDatabase = new ArrayList<>();
        loadFoodsFromCSV();
    }

    /**
     * อ่านข้อมูลอาหารจากไฟล์ foods.csv
     * ใช้ BufferedReader และ try-catch สำหรับ Error Handling
     */
    private void loadFoodsFromCSV() {
        // อ่านไฟล์จาก resources folder
        InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream("foods.csv");
        
        if (inputStream == null) {
            System.err.println("[FoodRepository] Error: Cannot find foods.csv file in resources folder");
            return;
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            
            // อ่าน header line (บรรทัดแรก) และข้ามไป
            String headerLine = reader.readLine();
            if (headerLine == null) {
                System.err.println("[FoodRepository] Error: CSV file is empty");
                return;
            }

            // อ่านข้อมูลแต่ละบรรทัด
            String line;
            int lineNumber = 2; // เริ่มจากบรรทัดที่ 2 (หลัง header)
            
            while ((line = reader.readLine()) != null) {
                // ข้ามบรรทัดว่าง
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                try {
                    // Parse CSV line
                    Food food = parseCSVLine(line, lineNumber);
                    foodDatabase.add(food);
                } catch (NumberFormatException e) {
                    System.err.println("[FoodRepository] Error parsing line " + lineNumber + 
                            ": Invalid number format - " + e.getMessage());
                    System.err.println("  Line content: " + line);
                } catch (IllegalArgumentException e) {
                    System.err.println("[FoodRepository] Error parsing line " + lineNumber + 
                            ": " + e.getMessage());
                    System.err.println("  Line content: " + line);
                }
                
                lineNumber++;
            }
            
            System.out.println("[FoodRepository] Successfully loaded " + foodDatabase.size() + " food items from CSV");
            
        } catch (IOException e) {
            System.err.println("[FoodRepository] IOException while reading foods.csv: " + e.getMessage());
            System.err.println("[FoodRepository] Stack trace: " + e.getClass().getSimpleName() + " occurred");
        } finally {
            // ปิด inputStream
            try {
                inputStream.close();
            } catch (IOException e) {
                System.err.println("[FoodRepository] Error closing input stream: " + e.getMessage());
            }
        }
    }

    /**
     * แปลงข้อมูลจาก CSV line เป็น Food object
     * @param line บรรทัดข้อมูลจาก CSV
     * @param lineNumber หมายเลขบรรทัด (สำหรับแสดง error message)
     * @return Food object
     * @throws NumberFormatException ถ้าข้อมูลตัวเลขไม่ถูกต้อง
     * @throws IllegalArgumentException ถ้าข้อมูลไม่ครบหรือไม่ถูกต้อง
     */
    private Food parseCSVLine(String line, int lineNumber) {
        // แยกข้อมูลด้วย comma
        String[] parts = line.split(",");
        
        // ต้องมี 5 columns: name, kcal, fat, sugar, sodium
        if (parts.length != 5) {
            throw new IllegalArgumentException(
                String.format("Line %d: Expected 5 columns but found %d columns. Format: name,kcal,fat,sugar,sodium", 
                    lineNumber, parts.length));
        }
        
        // Parse แต่ละ field
        String name = parts[0].trim();
        double kcal = Double.parseDouble(parts[1].trim());
        double fat = Double.parseDouble(parts[2].trim());
        double sugar = Double.parseDouble(parts[3].trim());
        double sodium = Double.parseDouble(parts[4].trim());
        
        // Validate ข้อมูล
        if (name.isEmpty()) {
            throw new IllegalArgumentException(
                String.format("Line %d: Food name cannot be empty", lineNumber));
        }
        
        if (kcal < 0 || fat < 0 || sugar < 0 || sodium < 0) {
            throw new IllegalArgumentException(
                String.format("Line %d: Nutritional values cannot be negative", lineNumber));
        }
        
        // สร้าง Food object
        return new Food(name, kcal, fat, sugar, sodium);
    }

    /**
     * ดึงรายการอาหารทั้งหมด
     * ส่งคืน copy เพื่อป้องกันการแก้ไขโดยตรง
     */
    public List<Food> findAll() {
        return new ArrayList<>(foodDatabase);
    }

    /**
     * ค้นหาอาหารด้วยชื่อ
     * ใช้ linear search วนลูปหา
     */
    public Food findByName(String name) {
        for (Food food : foodDatabase) {
            if (food.getName().equals(name)) {
                return food;
            }
        }
        return null; // ไม่เจอ
    }
}
