package com.ats.controller;

import com.ats.enums.FlightStatus;
import com.ats.payload.request.FlightRequest;
import com.ats.payload.response.AirlineResponse;
import com.ats.payload.response.ApiResponse;
import com.ats.payload.response.FlightResponse;
import com.ats.service.FlightService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/flights")
@Slf4j
public class FlightController {

    private final FlightService flightService;

    @PostMapping("")
    public ResponseEntity<FlightResponse> createFlight(@Valid @RequestBody FlightRequest request,
                                                       @RequestHeader("Airline-Id") Long airlineId) throws Exception {

        log.info("Inside the create flight method :::: ");
        FlightResponse response = flightService.createFlight(airlineId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightResponse> getFlightById(@PathVariable Long id) throws Exception {

        FlightResponse response = flightService.getFlightById(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    @GetMapping("/airline")
    public ResponseEntity<Page<FlightResponse>> getFlightByAirline(@RequestHeader("Airline-Id") Long airlineId,
                                                             @RequestParam(required = false) Long departureAirportId,
                                                             @RequestParam(required = false) Long arrivalAirportId,
                                                             Pageable pageable){

        Page<FlightResponse> response = flightService.getFlightByAirline(airlineId,
                departureAirportId,
                arrivalAirportId, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightResponse> updateFlight(@PathVariable Long id,
                                                       @RequestBody FlightRequest request) throws Exception {
        return ResponseEntity.ok(flightService.updateFlight(id, request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<FlightResponse> changeFlightStatus(@PathVariable Long id,
                                                             @RequestParam FlightStatus status) throws Exception {

        return ResponseEntity.ok(flightService.changeFlightStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteFlight(@PathVariable Long id,
                                                    @RequestHeader("Airline-Id") Long airlineId) throws Exception {
        flightService.deleteFlight(airlineId, id);
        return ResponseEntity.ok(new ApiResponse("Flight deleted successfully."));
    }
}
