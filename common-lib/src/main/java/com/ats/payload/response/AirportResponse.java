package com.ats.payload.response;

import com.ats.embeddable.Address;
import com.ats.embeddable.GeoCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZoneId;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirportResponse {

    private Long id;
    private String iataCode;
    private String name;
    private String detailedName;
    private Address address;
    private GeoCode geoCode;
    private ZoneId timeZone;
    private CityResponse cityResponse;
}
