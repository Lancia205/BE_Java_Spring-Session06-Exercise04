package com.example.session06.controller;

import com.example.session06.dto.*;
import com.example.session06.service.ParkingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.session06.dto.TicketSummaryResponse;
import com.example.session06.service.TicketService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
public class ParkingController {

    private final ParkingService parkingService;
    private final TicketService ticketService;

    @PostMapping("/check-in")
    public ApiResponse<TicketResponse> checkIn(
            @RequestBody TicketRequest req) {
        return new ApiResponse<>(
                true, "Gửi xe thành công",
                parkingService.checkIn(req)
        );
    }

    @PutMapping("/check-out/{vehicleId}")
    public ApiResponse<TicketResponse> checkOut(
            @PathVariable Long vehicleId) {
        return new ApiResponse<>(
                true, "Trả xe thành công",
                parkingService.checkOut(vehicleId)
        );
    }
    @GetMapping("/summary")
    public ApiResponse<List<TicketSummaryResponse>> getSummary() {
        return new ApiResponse<>(
                true,
                "Lấy danh sách vé xe trong ngày thành công",
                ticketService.getTodayTickets()
        );
    }
}