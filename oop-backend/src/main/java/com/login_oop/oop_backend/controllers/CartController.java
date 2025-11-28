package com.login_oop.oop_backend.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.login_oop.oop_backend.dto.CartRequest;
import com.login_oop.oop_backend.dto.CartResponse;
import com.login_oop.oop_backend.services.CartService;

// Controller สำหรับจัดการตะกร้าสินค้า
// รับ request จาก frontend แล้วส่งต่อไปให้ service จัดการต่อ
@RestController
@CrossOrigin(origins = "*")
public class CartController {

    // ต้องใช้ CartService เพื่อทำงานจริงๆ
    private final CartService cartService;

    // Constructor รับ CartService เข้ามา (Spring จะ inject ให้อัตโนมัติ)
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * API สำหรับเพิ่มอาหารลงตะกร้า
     * เรียกผ่าน POST /api/cart/add
     */
    @PostMapping("/api/cart/add")
    public Map<String, String> addToCart(@RequestBody(required = false) CartRequest request) {
        // เช็คว่ามี request body หรือเปล่า
        if (request == null) {
            return Map.of("status", "failed", "message", "Request body is missing");
        }
        
        // เช็ค username ว่ามีค่าหรือเปล่า
        if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
            return Map.of("status", "failed", "message", "Username is required");
        }
        
        // เช็ค foodName ว่ามีค่าหรือเปล่า
        if (request.getFoodName() == null || request.getFoodName().trim().isEmpty()) {
            return Map.of("status", "failed", "message", "Food name is required");
        }
        
        // เรียก service เพื่อเพิ่มอาหารลงตะกร้า
        boolean success = cartService.addToCart(request.getUsername(), request.getFoodName());
        if (success) {
            return Map.of("status", "success", "message", "เพิ่มอาหารลงตะกร้าเรียบร้อย");
        }
        return Map.of("status", "failed", "message", "ไม่พบอาหารที่ชื่อ: " + request.getFoodName());
    }

    /**
     * API สำหรับดึงข้อมูลตะกร้าของผู้ใช้
     * เรียกผ่าน GET /api/cart/{username}
     */
    @GetMapping("/api/cart/{username}")
    public List<CartResponse> getCart(@PathVariable String username) {
        // ดึงตะกร้าจาก service แล้วแปลงเป็น CartResponse
        return CartResponse.fromCartItems(cartService.getCart(username));
    }

    /**
     * API สำหรับลบรายการออกจากตะกร้า
     * เรียกผ่าน DELETE /api/cart/remove
     */
    @DeleteMapping("/api/cart/remove")
    public Map<String, String> removeFromCart(@RequestBody(required = false) CartRequest request) {
        // เช็คว่ามี request body หรือเปล่า
        if (request == null) {
            return Map.of("status", "failed", "message", "Request body is missing");
        }
        
        // เช็ค username
        if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
            return Map.of("status", "failed", "message", "Username is required");
        }
        
        // เช็ค foodName
        if (request.getFoodName() == null || request.getFoodName().trim().isEmpty()) {
            return Map.of("status", "failed", "message", "Food name is required");
        }
        
        // เรียก service เพื่อลบรายการ
        boolean success = cartService.removeFromCart(request.getUsername(), request.getFoodName());
        if (success) {
            return Map.of("status", "success", "message", "ลบรายการออกจากตะกร้าเรียบร้อย");
        }
        return Map.of("status", "failed", "message", "ไม่พบรายการในตะกร้า");
    }

    /**
     * API สำหรับล้างตะกร้าทั้งหมด
     * เรียกผ่าน DELETE /api/cart/clear/{username}
     */
    @DeleteMapping("/api/cart/clear/{username}")
    public Map<String, String> clearCart(@PathVariable String username) {
        cartService.clearCart(username);
        return Map.of("status", "success", "message", "ล้างตะกร้าเรียบร้อย");
    }
}

