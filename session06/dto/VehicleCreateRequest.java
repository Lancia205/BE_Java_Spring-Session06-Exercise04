package com.example.session06.dto;

import com.example.session06.entity.VehicleType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleCreateRequest {
    private String licensePlate;
    private String color;
    private VehicleType vehicleType;
}