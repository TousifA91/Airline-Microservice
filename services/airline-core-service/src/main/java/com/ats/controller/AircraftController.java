package com.ats.controller;

import com.ats.entity.Aircraft;
import com.ats.payload.request.AircraftRequest;
import com.ats.payload.response.AircraftResponse;
import com.ats.payload.response.ApiResponse;
import com.ats.service.AircraftService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/aircrafts")
public class AircraftController {

    private final AircraftService aircraftService;

    @PostMapping()
    public ResponseEntity<AircraftResponse> createAircraft(
            @Valid @RequestBody AircraftRequest request,
            @RequestHeader("X-User-Id") Long userId) throws Exception {

        AircraftResponse aircraft = aircraftService.createAircraft(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(aircraft);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AircraftResponse> getAircraftById(@PathVariable Long id) throws Exception {
        AircraftResponse response = aircraftService.getAircraftById(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    @GetMapping()
    public ResponseEntity<List<AircraftResponse>> getAllAircrafts(
            @RequestHeader("X-User-Id") Long userId) throws Exception {
        List<AircraftResponse> aircraftResponses = aircraftService.getAllAircraftByOwnerId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(aircraftResponses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AircraftResponse> updateAircraft(
            @PathVariable Long id,
            @RequestBody AircraftRequest request,
            @RequestHeader("X-User-Id") Long userId) throws Exception {

        AircraftResponse response = aircraftService.updateAircraft(id, request, userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteAircraft(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId) throws Exception {
        aircraftService.deleteAircraft(id, userId);
        ApiResponse response = new ApiResponse("Aircraft deleted successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
