package com.ats.service;

import com.ats.enums.FlightStatus;
import com.ats.payload.request.FlightRequest;
import com.ats.payload.response.FlightResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FlightService {

    FlightResponse createFlight(Long airlineId,FlightRequest request) throws Exception;
    Page<FlightResponse> getFlightByAirline(Long airlineId,
                                            Long departureAirportId,
                                            Long arrivalAirportId,
                                            Pageable pageable);

    FlightResponse getFlightById(Long id) throws Exception;
    FlightResponse updateFlight(Long id, FlightRequest request) throws Exception;
    FlightResponse changeFlightStatus(Long id, FlightStatus status) throws Exception;
    void deleteFlight(Long airlineId, Long id) throws Exception;


}
