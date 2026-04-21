package com.ats.config;

import com.ats.entity.Airport;
import com.ats.payload.request.AirportRequest;
import com.ats.payload.response.AirportResponse;

public class AirportMapper {

    public static Airport toEntity(AirportRequest airportRequest){
        if(airportRequest == null) return null;

        return Airport.builder()
                .iataCode(airportRequest.getIataCode())
                .name(airportRequest.getName())
                //.timeZone(airportRequest.getTimeZone())
                .address(airportRequest.getAddress())
                .geoCode(airportRequest.getGeoCode())
                .build();
    }

    public static AirportResponse toResponse(Airport airport){
        if(airport == null) return null;

        return AirportResponse.builder()
                .id(airport.getId())
                .iataCode(airport.getIataCode())
                .name(airport.getName())
                .detailedName(airport.getDetailedName())
             //   .timeZone(airport.getTimeZone())
                .address(airport.getAddress())
                .geoCode(airport.getGeoCode())
                .cityResponse(CityMapper.cityToResponse(airport.getCity()))
                .build();
    }

    public static void updateEntity(Airport existingAirport, AirportRequest airportRequest){
        if(airportRequest == null || existingAirport == null) return ;

        if(airportRequest.getIataCode() != null){
            existingAirport.setIataCode(airportRequest.getIataCode());
        }

        if(airportRequest.getName() != null){
            existingAirport.setName(airportRequest.getName());
        }

        if(airportRequest.getTimeZone() != null){
            existingAirport.setTimeZone(airportRequest.getTimeZone());
        }

        if(airportRequest.getAddress() != null){
            existingAirport.setAddress(airportRequest.getAddress());
        }

        if(airportRequest.getGeoCode() != null){
            existingAirport.setGeoCode(airportRequest.getGeoCode());
        }
    }
}
