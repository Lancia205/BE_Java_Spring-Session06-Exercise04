package com.example.session06.controller;

import com.example.session06.dto.*;
import com.example.session06.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping
    public ApiResponse<VehicleResponse> create(
            @RequestBody VehicleCreateRequest request) {
        return new ApiResponse<>(
                true, "Tạo xe thành công",
                vehicleService.createVehicle(request)
        );
    }

    @GetMapping
    public ApiResponse<PageResponse<VehicleResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String direction,
            @RequestParam(required = false) String keyword) {

        return new ApiResponse<>(
                true, "Lấy danh sách xe thành công",
                vehicleService.getPagedVehicles(
                        page, size, sortBy, direction, keyword)
        );
    }
}