package com.ats.service;

import com.ats.entity.Airline;
import com.ats.enums.AirlineStatus;
import com.ats.payload.request.AirlineRequest;
import com.ats.payload.response.AirlineDropdownItem;
import com.ats.payload.response.AirlineResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AirlineService {

    AirlineResponse createAirline(AirlineRequest request, Long ownerId);
    AirlineResponse getAirlineByOwner(Long ownerId) throws Exception;
    AirlineResponse getAirlineById(Long id) throws Exception;
    AirlineResponse updateAirline(AirlineRequest request, Long ownerId) throws Exception;
    Page<AirlineResponse> getAllAirlines(Pageable pageable);
    void deleteAirlineById(Long id, Long ownerId) throws Exception;

    AirlineResponse updateAirlineStatusByAdmin(Long airlineId, AirlineStatus status) throws Exception;
    List<AirlineDropdownItem> getAirlineDropdownItems();
}
