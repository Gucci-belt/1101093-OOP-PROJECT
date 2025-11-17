package com.login_oop.oop_backend.repositories; // 1. บอกว่าอยู่ในแพ็คเกจ repositories

// 2. Import เครื่องมือที่เราต้องใช้ (สำหรับ List, File, Scanner)
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Repository;

// 3. Import คลาส models ที่เราสร้างไว้
import com.login_oop.oop_backend.models.Admin;
import com.login_oop.oop_backend.models.Member;
import com.login_oop.oop_backend.models.User;

// 4. @Repository บอก Spring ว่าคลาสนี้ทำหน้าที่เป็น "คลังข้อมูล"
@Repository
public class UserRepository {
    
    // 5. นี่คือ "โครงสร้างข้อมูล" (Data Structure) ที่ มคอ.3 ต้องการ
    // เราใช้ List (จาก ArrayList) เพื่อเก็บ User ทั้งหมดที่อ่านมาจากไฟล์
    private List<User> userDatabase;
    
    // 6. กำหนดชื่อไฟล์ที่จะอ่าน
    private String filePath = "users.txt";

    // 7. Constructor: จะทำงานทันทีที่คลาสนี้ถูกสร้าง
    public UserRepository() {
        this.userDatabase = new ArrayList<>();
        loadUsersFromFile(); // สั่งให้โหลดข้อมูลจากไฟล์ทันที!
    }

    /**
     * 🎯 นี่คือเมธอดที่ตอบโจทย์ "การนำเข้าข้อมูลจากไฟล์"
     * ทำหน้าที่อ่านไฟล์ users.txt ทีละบรรทัด
     */
    private void loadUsersFromFile() {
        try (Scanner scanner = new Scanner(new File(filePath))) {
            
            System.out.println("... กำลังอ่านไฟล์ " + filePath + " ...");

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(","); // แยกข้อมูลด้วยเครื่องหมายจุลภาค (,)

                if (parts.length == 3) {
                    String username = parts[0];
                    String password = parts[1];
                    String role = parts[2];

                    // ใช้ OOP ที่เราสร้างไว้!
                    if (role.equalsIgnoreCase("Admin")) {
                        userDatabase.add(new Admin(username, password));
                    } else if (role.equalsIgnoreCase("Member")) {
                        userDatabase.add(new Member(username, password));
                    }
                }
            }
            System.out.println("... โหลดผู้ใช้สำเร็จ " + userDatabase.size() + " คน ...");

        } catch (FileNotFoundException e) {
            // กรณีหาไฟล์ users.txt ไม่เจอ
            System.err.println("[ERROR] ไม่พบไฟล์ " + filePath + "! กรุณาสร้างไฟล์.");
            // คุณอาจจะสร้าง Admin เริ่มต้นไว้ที่นี่ก็ได้ถ้าไฟล์ไม่เจอ
            // userDatabase.add(new Admin("admin", "admin")); 
        }
    }

    /**
     * 🎯 นี่คือเมธอดที่ตอบโจทย์ "การค้นหาข้อมูล" (Data Searching)
     * ทำหน้าที่ค้นหาผู้ใช้ด้วย username
     */
    public User findByUsername(String username) {
        // นี่คือการค้นหาแบบ Linear Search
        for (User user : userDatabase) {
            if (user.getUsername().equals(username)) {
                return user; // เจอ! ส่งผู้ใช้คนนั้นกลับไป
            }
        }
        return null; // ไม่เจอ
    }

    /**
     * เมธอดสำหรับ "บันทึก" ผู้ใช้ใหม่ (สำหรับการสมัครสมาชิก)
     * (ในโปรเจคจริง คุณต้องเขียนโค้ดเพื่อบันทึกลงไฟล์ .txt ด้วย
     * แต่วิชานี้ปกติจะเน้นแค่การอ่านไฟล์และการทำงานของ OOP ครับ)
     */
    public void save(User user) {
        userDatabase.add(user);
        System.out.println("ผู้ใช้ใหม่ " + user.getUsername() + " ถูกเพิ่มในระบบ (ชั่วคราว)");
        // หมายเหตุ: การบันทึกนี้จะหายไปเมื่อปิดเซิร์ฟเวอร์
        // เพราะเรายังไม่ได้เขียนโค้ด "บันทึกกลับลงไฟล์ .txt"
    }
}