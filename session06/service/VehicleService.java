package com.example.session06.service;

import com.example.session06.dto.*;
import com.example.session06.entity.Vehicle;
import com.example.session06.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public PageResponse<VehicleResponse> getPagedVehicles(
            int page, int size, String sortBy,
            String direction, String keyword) {

        page = Math.max(page, 0);
        size = Math.max(size, 1);

        Sort sort = Sort.unsorted();

        if (sortBy != null && !sortBy.isBlank()
                && direction != null && !direction.isBlank()) {
            sort = direction.equalsIgnoreCase("DESC")
                    ? Sort.by(sortBy).descending()
                    : Sort.by(sortBy).ascending();
        }

        Pageable pageable = PageRequest.of(page, size, sort);

        var result = vehicleRepository.findAllByKeyword(keyword, pageable);

        return new PageResponse<>(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages(),
                result.isLast()
        );
    }

    public VehicleResponse createVehicle(VehicleCreateRequest request) {
        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate(request.getLicensePlate());
        vehicle.setColor(request.getColor());
        vehicle.setType(request.getVehicleType());

        Vehicle saved = vehicleRepository.save(vehicle);

        return new VehicleResponse(
                saved.getId(),
                saved.getLicensePlate(),
                saved.getColor(),
                saved.getType()
        );
    }
}