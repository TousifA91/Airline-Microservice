package com.ats.controller;

import com.ats.payload.request.CityRequest;
import com.ats.payload.response.ApiResponse;
import com.ats.payload.response.CityResponse;
import com.ats.service.CityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cities")
public class CityController {

    private final CityService cityService;

    @PostMapping()
    public ResponseEntity<CityResponse> createCity(@Valid @RequestBody CityRequest cityRequest) throws Exception {
        CityResponse cityResponse = cityService.createCity(cityRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cityResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityResponse> getCityById(@PathVariable Long id) throws Exception {
        CityResponse response = cityService.getCityById(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<CityResponse>> getAllCities(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size,
                                                           @RequestParam(defaultValue = "name") String sortBy,
                                                           @RequestParam(defaultValue = "asc") String sortDirection){
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection),sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.status(HttpStatus.OK).body(cityService.getAllCities(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CityResponse> updateCityById(@PathVariable Long id,
                                                       @Valid @RequestBody CityRequest cityRequest) throws Exception {
        CityResponse response = cityService.updateCity(id, cityRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCityById(@PathVariable Long id) throws Exception {
        cityService.deleteCity(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("City deleted successfully."));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CityResponse>> searchCities(@RequestParam String keyword,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(cityService.searchCities(keyword, pageable));
    }

    @GetMapping("/country/{countryCode}")
    public ResponseEntity<Page<CityResponse>> getCitiesByCountryCode(@PathVariable String countryCode,
                                                                     @RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(cityService.getCitiesByCountryCode(countryCode.toUpperCase(), pageable));
    }

    @GetMapping("/exists/{cityCode}")
    public ResponseEntity<Boolean> checkCityExists(@PathVariable String cityCode){
        return ResponseEntity.status(HttpStatus.OK).body(cityService.cityExists(cityCode));
    }
}
