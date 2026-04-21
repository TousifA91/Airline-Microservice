package com.ats.controller;

import com.ats.payload.request.AirportRequest;
import com.ats.payload.response.AirportResponse;
import com.ats.payload.response.ApiResponse;
import com.ats.service.AirportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/airports")
public class AirportController {

    private final AirportService airportService;

    @PostMapping()
    public ResponseEntity<AirportResponse> createAirport(
            @Valid @RequestBody AirportRequest airportRequest) throws Exception {

        AirportResponse airportResponse =airportService.createAirport(airportRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(airportResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirportResponse> getAirportById(@PathVariable Long id) throws Exception {
        AirportResponse response = airportService.getAirportById(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    @GetMapping
    public ResponseEntity<List<AirportResponse>> getAllAirports(){
        List<AirportResponse> response = airportService.getAllAirports();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/city/{cityId}")
    public ResponseEntity<List<AirportResponse>> getAirportByCityId(@PathVariable Long cityId){
        List<AirportResponse> airportByCityId = airportService.getAirportByCityId(cityId);
        return ResponseEntity.status(HttpStatus.OK).body(airportByCityId);
    }


    @PutMapping("/{id}")
    public ResponseEntity<AirportResponse> updateAirport(@PathVariable Long id,
                                                        @Valid @RequestBody AirportRequest airportRequest) throws Exception {
        AirportResponse updatedAirport = airportService.updateAirport(id, airportRequest);
        return ResponseEntity.status(HttpStatus.OK).body(updatedAirport);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteAirport(@PathVariable Long id) throws Exception {
        airportService.deleteAirportById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Airport deleted successfully."));
    }
}
