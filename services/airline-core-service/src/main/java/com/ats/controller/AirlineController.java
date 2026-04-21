package com.ats.controller;

import com.ats.enums.AirlineStatus;
import com.ats.payload.request.AirlineRequest;
import com.ats.payload.response.AirlineDropdownItem;
import com.ats.payload.response.AirlineResponse;
import com.ats.payload.response.ApiResponse;
import com.ats.service.AirlineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/airlines")
public class AirlineController {

    private final AirlineService airlineService;

    @PostMapping("")
    public ResponseEntity<AirlineResponse> createAirline(
            @Valid @RequestBody AirlineRequest request,
            @RequestHeader("X-User-Id") Long userId){

        AirlineResponse response = airlineService.createAirline(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/admin")
    public ResponseEntity<AirlineResponse> getAirlineByOwner(@RequestHeader("X-User-Id") Long userId) throws Exception {

        AirlineResponse airlineResponse = airlineService.getAirlineByOwner(userId);
        return ResponseEntity.status(HttpStatus.OK).body(airlineResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AirlineResponse> getAirlineById(@PathVariable Long id) throws Exception {

        AirlineResponse response = airlineService.getAirlineById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("")
    public ResponseEntity<Page<AirlineResponse>> getAllAirlines(Pageable pageable){
        Page<AirlineResponse> airlineResponses = airlineService.getAllAirlines(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(airlineResponses);
    }


    @GetMapping("/dropdown")
    public ResponseEntity<List<AirlineDropdownItem>> getAirlineforDropdown(){
        return ResponseEntity.ok(airlineService.getAirlineDropdownItems());
    }

    @PutMapping
    public ResponseEntity<AirlineResponse> updateAirline(
            @Valid @RequestBody AirlineRequest request,
            @RequestHeader("X-User-Id") Long userId) throws Exception {

        AirlineResponse response = airlineService.updateAirline(request, userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteAirline(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long userId) throws Exception {

        airlineService.deleteAirlineById(id, userId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Airline deleted successfully."));
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<AirlineResponse> approveAirline(@PathVariable Long id) throws Exception {
        AirlineResponse response = airlineService.updateAirlineStatusByAdmin(id, AirlineStatus.ACTIVE);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/{id}/suspend")
    public ResponseEntity<AirlineResponse> suspendAirline(@PathVariable Long id) throws Exception {
        AirlineResponse response = airlineService.updateAirlineStatusByAdmin(id, AirlineStatus.INACTIVE);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/{id}/ban")
    public ResponseEntity<AirlineResponse> banAirline(@PathVariable Long id) throws Exception {
        AirlineResponse response = airlineService.updateAirlineStatusByAdmin(id, AirlineStatus.BANNED);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
