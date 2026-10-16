package com.example.session06.service;

import com.example.session06.dto.TicketRequest;
import com.example.session06.dto.TicketResponse;
import com.example.session06.entity.*;
import com.example.session06.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ParkingService {

    private final VehicleRepository vehicleRepository;
    private final ZoneRepository zoneRepository;
    private final ParkingTicketRepository ticketRepository;

    @Transactional
    public TicketResponse checkIn(TicketRequest req) {
        Vehicle vehicle = vehicleRepository.findById(req.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy xe"));

        Zone zone = zoneRepository.findById(req.getZoneId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khu vực"));

        int occupied = zone.getOccupiedSpots() == null
                ? 0 : zone.getOccupiedSpots();

        if (zone.getCapacity() == null || occupied >= zone.getCapacity()) {
            throw new RuntimeException("Khu vực đã hết chỗ");
        }

        ParkingTicket ticket = new ParkingTicket();
        ticket.setVehicle(vehicle);
        ticket.setZone(zone);
        ticket.setCheckInTime(LocalDateTime.now());

        zone.setOccupiedSpots(occupied + 1);
        zoneRepository.save(zone);

        ParkingTicket saved = ticketRepository.save(ticket);

        return new TicketResponse(
                saved.getId(),
                vehicle.getLicensePlate(),
                zone.getName(),
                saved.getCheckInTime(),
                saved.getCheckOutTime()
        );
    }

    @Transactional
    public TicketResponse checkOut(Long vehicleId) {
        ParkingTicket ticket = ticketRepository
                .findFirstByVehicle_IdAndCheckOutTimeIsNullOrderByCheckInTimeDesc(
                        vehicleId)
                .orElseThrow(() -> new RuntimeException(
                        "Xe không có vé gửi đang hoạt động"));

        Zone zone = ticket.getZone();

        ticket.setCheckOutTime(LocalDateTime.now());

        int occupied = zone.getOccupiedSpots() == null
                ? 0 : zone.getOccupiedSpots();

        zone.setOccupiedSpots(Math.max(0, occupied - 1));

        zoneRepository.save(zone);
        ParkingTicket saved = ticketRepository.save(ticket);

        return new TicketResponse(
                saved.getId(),
                ticket.getVehicle().getLicensePlate(),
                zone.getName(),
                saved.getCheckInTime(),
                saved.getCheckOutTime()
        );
    }
}