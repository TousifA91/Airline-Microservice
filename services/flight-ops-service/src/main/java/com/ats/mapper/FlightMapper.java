package com.ats.mapper;

import com.ats.entity.Flight;
import com.ats.payload.request.FlightRequest;
import com.ats.payload.response.AircraftResponse;
import com.ats.payload.response.AirlineResponse;
import com.ats.payload.response.AirportResponse;
import com.ats.payload.response.FlightResponse;

public class FlightMapper {

    public static Flight toEntity(FlightRequest request){

        if(request == null) return null;

        return Flight.builder()
                .flightNumber(request.getFlightNumber())
                .aircraftId(request.getAircraftId())
                .departureAirportId(request.getDepartureAirportId())
                .arrivalAirportId(request.getArrivalAirportId())
                .build();
    }

    public static FlightResponse toResponse(Flight flight,
                                            AircraftResponse aircraft,
                                            AirlineResponse airlineResponse,
                                            AirportResponse departureAirport,
                                            AirportResponse arrivalAirport){

        if(flight == null) return null;
        return FlightResponse.builder()
                .id(flight.getId())
                .flightNumber(flight.getFlightNumber())
                .airline(airlineResponse)
                .aircraft(aircraft)
                .departureAirport(departureAirport)
                .arrivalAirport(arrivalAirport)
                .status(flight.getStatus())
                .createdAt(flight.getCreatedAt())
                .updatedAt(flight.getUpdatedAt())
                .build();
    }

    public static void updateEntity(FlightRequest request, Flight existingFlight){
        if(request == null || existingFlight == null) return;

        if(request.getFlightNumber() != null) existingFlight.setFlightNumber(request.getFlightNumber());
        //if(request.getAirlineId() != null) existingFlight.setAirlineId(request.getAirlineId());
        if(request.getAircraftId() != null) existingFlight.setAircraftId(request.getAircraftId());
        if(request.getDepartureAirportId() != null) existingFlight.setDepartureAirportId(request.getDepartureAirportId());
        if(request.getArrivalAirportId() != null) existingFlight.setArrivalAirportId(request.getArrivalAirportId());
        if(request.getStatus() != null) existingFlight.setStatus(request.getStatus());
    }
}
