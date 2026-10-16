package com.example.session06.repository;

import com.example.session06.dto.TicketSummaryResponse;
import com.example.session06.entity.ParkingTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ParkingTicketRepository extends JpaRepository<ParkingTicket, Long> {

    Optional<ParkingTicket>
    findFirstByVehicle_IdAndCheckOutTimeIsNullOrderByCheckInTimeDesc(
            Long vehicleId);

    @Query("""
    SELECT new com.example.session06.dto.TicketSummaryResponse(
        t.id, t.vehicle.licensePlate, t.zone.name,
        t.checkInTime, t.checkOutTime
    )
    FROM ParkingTicket t
    WHERE t.checkInTime >= :start
      AND t.checkInTime < :end
    ORDER BY t.checkInTime DESC
    """)
    List<TicketSummaryResponse> findTodayTickets(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}