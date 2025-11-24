package com.login_oop.oop_backend.dto;

import com.login_oop.oop_backend.models.CartItem;
import com.login_oop.oop_backend.models.Food;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 🎯 DTO Class สำหรับส่งข้อมูลกลับไป Frontend
 * ใช้ OOP: Encapsulation + Method ที่แปลง CartItem เป็น JSON-friendly format
 */
public class CartResponse {
    private String name;
    private double kcal;
    private double fat;
    private double sugar;
    private double sodium;

    // Constructor
    public CartResponse(String name, double kcal, double fat, double sugar, double sodium) {
        this.name = name;
        this.kcal = kcal;
        this.fat = fat;
        this.sugar = sugar;
        this.sodium = sodium;
    }

    // Getters
    public String getName() {
        return name;
    }

    public double getKcal() {
        return kcal;
    }

    public double getFat() {
        return fat;
    }

    public double getSugar() {
        return sugar;
    }

    public double getSodium() {
        return sodium;
    }

    /**
     * 🎯 Static Method: แปลง CartItem เป็น CartResponse
     * ใช้ OOP: Static method (ไม่ต้องสร้าง object ก็เรียกใช้ได้)
     */
    public static CartResponse fromCartItem(CartItem cartItem) {
        Food food = cartItem.getFood();
        return new CartResponse(
            food.getName(),
            food.getKcal(),
            food.getFat(),
            food.getSugar(),
            food.getSodium()
        );
    }

    /**
     * 🎯 Static Method: แปลง List<CartItem> เป็น List<CartResponse>
     */
    public static List<CartResponse> fromCartItems(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(CartResponse::fromCartItem)
                .collect(Collectors.toList());
    }
}

