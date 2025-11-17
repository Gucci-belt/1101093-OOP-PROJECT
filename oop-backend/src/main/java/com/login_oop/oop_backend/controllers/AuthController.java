package com.login_oop.oop_backend.controllers; // 1. อยู่ในแพ็คเกจ controllers

// 2. Import เครื่องมือและคลาสที่ต้องใช้
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin; // ⭐️ สำคัญมาก

import com.login_oop.oop_backend.dto.LoginRequest;
import com.login_oop.oop_backend.dto.RegisterRequest;
import com.login_oop.oop_backend.models.User;
import com.login_oop.oop_backend.services.AuthService;

import java.util.Map; // สำหรับสร้าง JSON ตอบกลับง่ายๆ

// 3. @RestController บอก Spring ว่าคลาสนี้คือ "พนักงานต้อนรับ" API
@RestController
// 4. ⭐️ @CrossOrigin อนุญาตให้ JavaScript จาก "ที่อื่น" (เช่นไฟล์ HTML ของคุณ) เรียกเข้ามาได้
@CrossOrigin(origins = "*") // อนุญาตทุกที่ (เพื่อความง่ายในการทดสอบ)
public class AuthController {

    // 5. "พนักงานต้อนรับ" ต้องคุยกับ "พ่อครัว" (Service)
    @Autowired
    private AuthService authService;

    /**
     * 🎯 นี่คือ "ช่องทาง" สำหรับการ Login
     * @PostMapping("/login") บอกว่า:
     * "ใครก็ตามที่ส่งคำขอแบบ 'POST' มาที่ http://localhost:8080/login ให้เรียกเมธอดนี้"
     */
    @PostMapping("/login")
    public Map<String, String> handleLogin(@RequestBody LoginRequest request) {
        // 1. รับ "ถาดข้อมูล" (LoginRequest) ที่ JS ส่งมา
        // 2. ส่ง username/password ไปให้ "พ่อครัว" (AuthService)
        User user = authService.login(request.getUsername(), request.getPassword());

        // 3. ตอบกลับไปหา JavaScript
        if (user != null) {
            // Login สำเร็จ: ส่ง "status" และ "role" กลับไป
            return Map.of(
                "status", "success",
                "role", user.getRole(),
                "username", user.getUsername()
            );
        } else {
            // Login ล้มเหลว
            return Map.of("status", "failed");
        }
    }

    /**
     * 🎯 นี่คือ "ช่องทาง" สำหรับการสมัครสมาชิก
     * @PostMapping("/register") บอกว่า:
     * "ใครก็ตามที่ส่งคำขอแบบ 'POST' มาที่ http://localhost:8080/register ให้เรียกเมธอดนี้"
     */
    @PostMapping("/register")
    public Map<String, String> handleRegister(@RequestBody RegisterRequest request) {
        // 1. รับ "ถาดข้อมูล" (RegisterRequest)
        // 2. ส่งไปให้ "พ่อครัว" (AuthService)
        boolean isSuccess = authService.register(request.getUsername(), request.getPassword());

        // 3. ตอบกลับไปหา JavaScript
        if (isSuccess) {
            return Map.of("status", "success");
        } else {
            // สมัครล้มเหลว (เช่น ชื่อซ้ำ)
            return Map.of("status", "failed", "message", "Username already taken");
        }
    }
}