package com.login_oop.oop_backend.repositories;

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

// Repository สำหรับจัดการข้อมูล user
// อ่านข้อมูลจากไฟล์ users.txt และเก็บไว้ใน memory
@Repository
public class UserRepository {
    
    // เก็บ user ทั้งหมดใน memory
    private final List<User> userDatabase;
    
    // เก็บ password แยกไว้ (เพราะ User class ไม่มี getter สำหรับ password)
    // ใช้ตอนบันทึกลงไฟล์
    private final java.util.Map<String, String> passwordStorage = new java.util.HashMap<>();
    
    // ไฟล์ที่เก็บข้อมูล user
    private final String filePath = "src/main/resources/users.txt";

    // Constructor จะโหลดข้อมูลจากไฟล์ทันทีที่สร้าง object
    public UserRepository() {
        this.userDatabase = new ArrayList<>();
        loadUsersFromFile();
    }

    /**
     * อ่านข้อมูล user จากไฟล์ users.txt
     * ไฟล์มีรูปแบบ: username,password,role
     */
    private void loadUsersFromFile() {
        try (Scanner scanner = new Scanner(new File(filePath))) {
            
            System.out.println("... กำลังอ่านไฟล์ " + filePath + " ...");

            // อ่านทีละบรรทัด
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(","); // แยกด้วย comma

                if (parts.length == 3) {
                    String username = parts[0];
                    String password = parts[1];
                    String role = parts[2];

                    // เก็บ password ไว้ใน Map
                    passwordStorage.put(username, password);

                    // สร้าง User object ตาม role
                    if (role.equalsIgnoreCase("Admin")) {
                        userDatabase.add(new Admin(username, password));
                    } else if (role.equalsIgnoreCase("Member")) {
                        userDatabase.add(new Member(username, password));
                    }
                }
            }
            System.out.println("... โหลดผู้ใช้สำเร็จ " + userDatabase.size() + " คน ...");

        } catch (FileNotFoundException e) {
            // ถ้าหาไฟล์ไม่เจอ
            System.err.println("[ERROR] ไม่พบไฟล์ " + filePath + "! กรุณาสร้างไฟล์.");
        }
    }

    /**
     * ค้นหา user จาก username
     * ใช้ linear search วนลูปหา
     */
    public User findByUsername(String username) {
        for (User user : userDatabase) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null; // ไม่เจอ
    }

    /**
     * บันทึก user ใหม่
     * เพิ่มใน memory และบันทึกลงไฟล์
     */
    public void save(User user, String password) {
        userDatabase.add(user);
        passwordStorage.put(user.getUsername(), password);
        System.out.println("ผู้ใช้ใหม่ " + user.getUsername() + " ถูกเพิ่มในระบบ");
        saveToFile(); // บันทึกลงไฟล์
    }
    
    /**
     * บันทึก user (overload method)
     * ใช้ password ที่เก็บไว้ใน Map
     */
    public void save(User user) {
        String password = passwordStorage.getOrDefault(user.getUsername(), "");
        save(user, password);
    }
    
    /**
     * บันทึกข้อมูลทั้งหมดลงไฟล์ users.txt
     * เขียนทับไฟล์เดิม
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