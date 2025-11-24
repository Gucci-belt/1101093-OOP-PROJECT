package com.login_oop.oop_backend.repositories; // 1. บอกว่าอยู่ในแพ็คเกจ repositories

// 2. Import เครื่องมือที่เราต้องใช้ (สำหรับ List, File, Scanner, PrintWriter)
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Repository;

import com.login_oop.oop_backend.models.Admin;
import com.login_oop.oop_backend.models.Member;
import com.login_oop.oop_backend.models.User;

// 4. @Repository บอก Spring ว่าคลาสนี้ทำหน้าที่เป็น "คลังข้อมูล"
@Repository
public class UserRepository {
    
    // 5. นี่คือ "โครงสร้างข้อมูล" (Data Structure) ที่ มคอ.3 ต้องการ
    // เราใช้ List (จาก ArrayList) เพื่อเก็บ User ทั้งหมดที่อ่านมาจากไฟล์
    private final List<User> userDatabase;
    
    // Map สำหรับเก็บ password (เนื่องจาก User class ไม่มี getter สำหรับ password)
    // หมายเหตุ: ในระบบจริงควรใช้ password hashing
    private final java.util.Map<String, String> passwordStorage = new java.util.HashMap<>();
    
    // 6. กำหนดชื่อไฟล์ที่จะอ่าน (อยู่ใน resources folder)
    private final String filePath = "src/main/resources/users.txt";

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

                    // เก็บ password ไว้ใน Map (เพื่อใช้ตอนบันทึกลงไฟล์)
                    passwordStorage.put(username, password);

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
     * เพิ่มผู้ใช้ใน memory และบันทึกลงไฟล์ users.txt
     * 
     * @param user ผู้ใช้ที่ต้องการบันทึก
     * @param password รหัสผ่านของผู้ใช้ (ต้องส่งมาแยกเพราะ User class ไม่มี getter)
     */
    public void save(User user, String password) {
        userDatabase.add(user);
        // เก็บ password ไว้ใน Map เพื่อใช้ตอนบันทึกลงไฟล์
        passwordStorage.put(user.getUsername(), password);
        System.out.println("ผู้ใช้ใหม่ " + user.getUsername() + " ถูกเพิ่มในระบบ");
        // บันทึกลงไฟล์ users.txt
        saveToFile();
    }
    
    /**
     * เมธอดสำหรับ "บันทึก" ผู้ใช้ใหม่ (overload สำหรับความเข้ากันได้)
     * หมายเหตุ: วิธีนี้จะไม่สามารถบันทึก password ได้ ควรใช้ save(User, String) แทน
     */
    public void save(User user) {
        // เรียกใช้ save(User, String) โดยใช้ password ที่เก็บไว้ใน Map
        String password = passwordStorage.getOrDefault(user.getUsername(), "");
        save(user, password);
    }
    
    /**
     * 🎯 เมธอดสำหรับบันทึกข้อมูลทั้งหมดลงไฟล์ users.txt
     * จะเขียนทับไฟล์เดิมด้วยข้อมูลทั้งหมดใน userDatabase
     */
    private void saveToFile() {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (User user : userDatabase) {
                // ดึง password จาก Map
                String password = passwordStorage.getOrDefault(user.getUsername(), "");
                // เขียนในรูปแบบ: username,password,role
                writer.write(user.getUsername() + "," + password + "," + user.getRole() + "\n");
            }
            System.out.println("... บันทึกข้อมูลลงไฟล์ " + filePath + " สำเร็จ ...");
        } catch (IOException e) {
            System.err.println("[ERROR] ไม่สามารถบันทึกไฟล์ " + filePath + " ได้: " + e.getMessage());
        }
    }
}