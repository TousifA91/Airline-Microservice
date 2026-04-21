package com.ats.payload.request;

import com.ats.enums.AircraftStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AircraftRequest {

    @NotBlank(message = "Aircraft code is mandatory")
    private String code;

    @NotBlank(message = "Aircraft model is mandatory")
    private String model;

    @NotBlank(message = "Aircraft manufacturer is mandatory")
    private String manufacturer;

    @NotNull(message = "Seating capacity is required")
    @Positive(message = "Seating capacity must be positive")
    private Integer seatingCapacity;

    @Positive(message = "Economy seats must be positive")
    private Integer economySeats;

    @Positive(message = "Premium economy seats must be positive")
    private Integer premiumEconomySeats;

    @Positive(message = "Business seats must be positive")
    private Integer businessSeats;

    @Positive(message = "First class seats must be positive")
    private Integer firstClassSeats;

    @Positive(message = "Range must be positive")
    private Integer rangeKm;

    @Positive(message = "Cruising speed must be positive")
    private Integer cruisingSpeedKmh;

    @Positive(message = "Maximum Altitude must be positive")
    private Integer maxAltitudeFt;

    @Positive(message = "year of manufacture must be positive")
    private Integer yearOfManufacture;

    private LocalDate registrationDate;
    private LocalDate nextMaintenanceDate;

    @NotNull(message = "Status is required")
    private AircraftStatus status;

    @NotNull(message = "Availability status is required")
    private Boolean isAvailable;

    private Long currentAirportId;

}
