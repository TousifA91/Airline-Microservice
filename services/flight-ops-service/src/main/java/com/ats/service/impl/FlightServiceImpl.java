package com.ats.service.impl;

import com.ats.entity.Flight;
import com.ats.enums.FlightStatus;
import com.ats.mapper.FlightMapper;
import com.ats.payload.request.FlightRequest;
import com.ats.payload.response.AircraftResponse;
import com.ats.payload.response.AirlineResponse;
import com.ats.payload.response.AirportResponse;
import com.ats.payload.response.FlightResponse;
import com.ats.repository.FlightRepository;
import com.ats.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepo;

    @Override
    public FlightResponse createFlight(Long airlineId, FlightRequest request) throws Exception {

        if(flightRepo.existsByFlightNumber(request.getFlightNumber())){
            throw new Exception("Flight already exist with the flight number ");
        }

        Flight flight = FlightMapper.toEntity(request);
        flight.setAirlineId(airlineId);
        Flight savedFlight = flightRepo.save(flight);
        return convertToFlightResponse(flight);
    }

    @Override
    public Page<FlightResponse> getFlightByAirline(Long airlineId, Long departureAirportId, Long arrivalAirportId, Pageable pageable) {

        return flightRepo.findByAirlineId(airlineId,
                departureAirportId,
                arrivalAirportId,
                pageable).map(this::convertToFlightResponse);
    }

    @Override
    public FlightResponse getFlightById(Long id) throws Exception {
        Flight flight = flightRepo.findById(id).orElseThrow(
                () -> new Exception("Flight not found by the given Id :" + id));

        return convertToFlightResponse(flight);
    }

    @Override
    public FlightResponse updateFlight(Long id, FlightRequest request) throws Exception {

        Flight existingFlight = flightRepo.findById(id).orElseThrow(
                () -> new Exception("Flight not found by the given Id :" + id));

        if(request.getFlightNumber() != null &&
        flightRepo.existsByFlightNumberAndIdNot(request.getFlightNumber(), id)){
            throw new Exception("Flight already exist ");
        }
        FlightMapper.updateEntity(request, existingFlight);
        Flight savedFlight = FlightMapper.toEntity(request);

        return convertToFlightResponse(savedFlight);
    }

    @Override
    public FlightResponse changeFlightStatus(Long id, FlightStatus status) throws Exception {

        Flight existingFlight = flightRepo.findById(id).orElseThrow(
                () -> new Exception("Flight not found by the given Id :" + id));

        existingFlight.setStatus(status);
        Flight savedFlight = flightRepo.save(existingFlight);
        return convertToFlightResponse(savedFlight);
    }

    @Override
    public void deleteFlight(Long airlineId, Long id) throws Exception {

        Flight existingFlight = flightRepo.findByAirlineIdAndId(airlineId, id).orElseThrow(
                () -> new Exception("Flight not found by the given Id :" + id));

        flightRepo.delete(existingFlight);

    }

    public FlightResponse convertToFlightResponse(Flight flight){

        AircraftResponse aircraft = AircraftResponse.builder()
                .id(flight.getAircraftId())
                .build();

        AirlineResponse airline = AirlineResponse.builder()
                .id(flight.getAirlineId())
                .build();

        AirportResponse departureAirport = AirportResponse.builder()
                .id(flight.getDepartureAirportId())
                .build();
        AirportResponse arrivalAirport = AirportResponse.builder()
                .id(flight.getArrivalAirportId())
                .build();

        return FlightMapper.toResponse(
                flight,
                aircraft,
                airline,
                departureAirport,
                arrivalAirport
        );
    }
}
