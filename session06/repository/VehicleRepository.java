package com.example.session06.repository;

import com.example.session06.dto.VehicleResponse;
import com.example.session06.entity.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    @Query("""
        SELECT new com.example.session06.dto.VehicleResponse(
            v.id, v.licensePlate, v.color, v.type
        )
        FROM Vehicle v
        WHERE (:keyword IS NULL OR LOWER(v.licensePlate)
               LIKE LOWER(CONCAT('%', :keyword, '%')))
        """)
    Page<VehicleResponse> findAllByKeyword(
            @Param("keyword") String keyword,
            Pageable pageable
    );
}