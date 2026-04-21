package com.ats.service;

import com.ats.payload.request.AirportRequest;
import com.ats.payload.response.AirportResponse;

import java.util.List;

public interface AirportService {

    AirportResponse createAirport(AirportRequest airportRequest) throws Exception;
    AirportResponse getAirportById(Long id) throws Exception;
    List<AirportResponse> getAllAirports();
    AirportResponse updateAirport(Long id, AirportRequest airportRequest) throws Exception;
    void deleteAirportById(Long id) throws Exception;
    List<AirportResponse> getAirportByCityId(Long cityId);

}
