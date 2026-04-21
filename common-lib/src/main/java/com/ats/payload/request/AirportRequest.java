package com.ats.payload.request;

import com.ats.embeddable.Address;
import com.ats.embeddable.GeoCode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirportRequest {

    @NotBlank(message = "IATA Code is mandatory")
    @Size(min = 3,max = 3, message = "IATA code should be exactly of 3 characters")
    private String iataCode;

    @NotBlank(message = "Airport name is mandatory")
    private String name;

    private String timeZone;

    @Valid
    private Address address;

    @NotNull(message = "City ID is mandatory")
    private Long cityId;

    @Valid
    private GeoCode geoCode;
}
