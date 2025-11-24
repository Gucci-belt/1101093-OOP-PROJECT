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

/**
 * 🎯 Controller Class สำหรับจัดการ HTTP Requests เกี่ยวกับตะกร้า
 * ใช้ OOP: Class + Methods + REST API
 */
@RestController
@CrossOrigin(origins = "*")
public class CartController {

    // Dependency Injection: CartController ต้องใช้ CartService
    private final CartService cartService;

    // Constructor Injection
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * 🎯 API Endpoint: เพิ่มอาหารลงตะกร้า
     * POST /api/cart/add
     * @param request CartRequest ที่มี username และ foodName
     * @return success หรือ error message
     */
    @PostMapping("/api/cart/add")
    public Map<String, String> addToCart(@RequestBody(required = false) CartRequest request) {
        // ตรวจสอบว่า request body มีค่าหรือไม่
        if (request == null) {
            return Map.of("status", "failed", "message", "Request body is missing");
        }
        
        // ตรวจสอบว่า username และ foodName มีค่าหรือไม่
        if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
            return Map.of("status", "failed", "message", "Username is required");
        }
        
        if (request.getFoodName() == null || request.getFoodName().trim().isEmpty()) {
            return Map.of("status", "failed", "message", "Food name is required");
        }
        
        boolean success = cartService.addToCart(request.getUsername(), request.getFoodName());
        if (success) {
            return Map.of("status", "success", "message", "เพิ่มอาหารลงตะกร้าเรียบร้อย");
        }
        return Map.of("status", "failed", "message", "ไม่พบอาหารที่ชื่อ: " + request.getFoodName());
    }

    /**
     * 🎯 API Endpoint: ดึงตะกร้าของผู้ใช้
     * GET /api/cart/{username}
     * @param username ชื่อผู้ใช้
     * @return List ของ CartResponse objects
     */
    @GetMapping("/api/cart/{username}")
    public List<CartResponse> getCart(@PathVariable String username) {
        return CartResponse.fromCartItems(cartService.getCart(username));
    }

    /**
     * 🎯 API Endpoint: ลบรายการออกจากตะกร้า
     * DELETE /api/cart/remove
     * @param request CartRequest ที่มี username และ foodName
     * @return success หรือ error message
     */
    @DeleteMapping("/api/cart/remove")
    public Map<String, String> removeFromCart(@RequestBody(required = false) CartRequest request) {
        // ตรวจสอบว่า request body มีค่าหรือไม่
        if (request == null) {
            return Map.of("status", "failed", "message", "Request body is missing");
        }
        
        // ตรวจสอบว่า username และ foodName มีค่าหรือไม่
        if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
            return Map.of("status", "failed", "message", "Username is required");
        }
        
        if (request.getFoodName() == null || request.getFoodName().trim().isEmpty()) {
            return Map.of("status", "failed", "message", "Food name is required");
        }
        
        boolean success = cartService.removeFromCart(request.getUsername(), request.getFoodName());
        if (success) {
            return Map.of("status", "success", "message", "ลบรายการออกจากตะกร้าเรียบร้อย");
        }
        return Map.of("status", "failed", "message", "ไม่พบรายการในตะกร้า");
    }

    /**
     * 🎯 API Endpoint: ล้างตะกร้า
     * DELETE /api/cart/clear/{username}
     * @param username ชื่อผู้ใช้
     * @return success message
     */
    @DeleteMapping("/api/cart/clear/{username}")
    public Map<String, String> clearCart(@PathVariable String username) {
        cartService.clearCart(username);
        return Map.of("status", "success", "message", "ล้างตะกร้าเรียบร้อย");
    }
}

