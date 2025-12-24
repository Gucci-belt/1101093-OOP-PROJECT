package com.login_oop.oop_backend.controllers;

import com.login_oop.oop_backend.dto.FoodTrackingRequest;
import com.login_oop.oop_backend.dto.FoodTrackingResponse;
import com.login_oop.oop_backend.services.FoodTrackingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

// Controller สำหรับจัดการการติดตามอาหาร
@RestController
@CrossOrigin(origins = "*")
public class FoodTrackingController {

    private final FoodTrackingService trackingService;

    // Constructor
    public FoodTrackingController(FoodTrackingService trackingService) {
        this.trackingService = trackingService;
    }

    /**
     * API สำหรับเพิ่ม tracking
     * POST /api/tracking/add
     */
    @PostMapping("/api/tracking/add")
    public Map<String, String> addTracking(@RequestBody(required = false) FoodTrackingRequest request) {
        if (request == null) {
            return Map.of("status", "failed", "message", "Request body is missing");
        }
        
        if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
            return Map.of("status", "failed", "message", "Username is required");
        }
        
        if (request.getFoodName() == null || request.getFoodName().trim().isEmpty()) {
            return Map.of("status", "failed", "message", "Food name is required");
        }
        
        boolean success = trackingService.addTracking(request.getUsername(), request.getFoodName());
        if (success) {
            return Map.of("status", "success", "message", "บันทึกการทานอาหารเรียบร้อย");
        }
        return Map.of("status", "failed", "message", "ไม่พบอาหารที่ชื่อ: " + request.getFoodName());
    }

    /**
     * API สำหรับดึง tracking ของวันนี้
     * GET /api/tracking/today/{username}
     */
    @GetMapping("/api/tracking/today/{username}")
    public List<FoodTrackingResponse> getTodayTracking(@PathVariable String username) {
        // รับวันที่วันนี้ในรูปแบบ YYYY-MM-DD
        String today = java.time.LocalDate.now().toString();
        return FoodTrackingResponse.fromTrackingList(
            trackingService.getTrackingByDate(username, today)
        );
    }

    /**
     * API สำหรับดึง tracking ตามวันที่
     * GET /api/tracking/date/{username}/{date}
     */
    @GetMapping("/api/tracking/date/{username}/{date}")
    public List<FoodTrackingResponse> getTrackingByDate(
            @PathVariable String username,
            @PathVariable String date) {
        return FoodTrackingResponse.fromTrackingList(
            trackingService.getTrackingByDate(username, date)
        );
    }

    /**
     * API สำหรับดึง tracking ทั้งหมด
     * GET /api/tracking/all/{username}
     */
    @GetMapping("/api/tracking/all/{username}")
    public List<FoodTrackingResponse> getAllTracking(@PathVariable String username) {
        return FoodTrackingResponse.fromTrackingList(
            trackingService.getAllTracking(username)
        );
    }

    /**
     * API สำหรับดึง tracking ตามช่วงวันที่
     * GET /api/tracking/range/{username}?startDate=YYYY-MM-DD&endDate=YYYY-MM-DD
     */
    @GetMapping("/api/tracking/range/{username}")
    public List<FoodTrackingResponse> getTrackingByDateRange(
            @PathVariable String username,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return FoodTrackingResponse.fromTrackingList(
            trackingService.getTrackingByDateRange(username, startDate, endDate)
        );
    }

    /**
     * API สำหรับลบ tracking ของวันนี้
     * DELETE /api/tracking/today/{username}
     */
    @DeleteMapping("/api/tracking/today/{username}")
    public Map<String, String> deleteTodayTracking(@PathVariable String username) {
        String today = java.time.LocalDate.now().toString();
        trackingService.deleteTrackingByDate(username, today);
        return Map.of("status", "success", "message", "ลบประวัติการทานอาหารวันนี้เรียบร้อย");
    }
}

