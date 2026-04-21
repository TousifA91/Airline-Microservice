package com.ats.service;


import com.ats.payload.request.FlightInstanceRequest;
import com.ats.payload.response.FlightInstanceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FlightInstanceService {

    FlightInstanceResponse createFlightInstance(Long userId, FlightInstanceRequest request);

    FlightInstanceResponse getFlightInstanceById(Long id);

    Page<FlightInstanceResponse> getFlightInstanceByAirlineId(Long airlineId,
                                                              Long departureAirportId,
                                                              Long arrivalAirportId,
                                                              Long flightId,
                                                              Long onDate,
                                                              Pageable pageable);

    FlightInstanceResponse updateFlightInstance(Long id, FlightInstanceRequest request);

    void deleteFlightInstance(Long id);
}
