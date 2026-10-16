package com.example.session06.service;

import com.example.session06.dto.TicketSummaryResponse;
import com.example.session06.repository.ParkingTicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final ParkingTicketRepository ticketRepository;

    public List<TicketSummaryResponse> getTodayTickets() {
        LocalDate today = LocalDate.now();

        return ticketRepository.findTodayTickets(
                today.atStartOfDay(),
                today.plusDays(1).atStartOfDay()
        );
    }
}