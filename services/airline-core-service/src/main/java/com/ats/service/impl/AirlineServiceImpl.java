package com.ats.service.impl;

import com.ats.entity.Airline;
import com.ats.enums.AirlineStatus;
import com.ats.mapper.AirlineMapper;
import com.ats.payload.request.AirlineRequest;
import com.ats.payload.response.AirlineDropdownItem;
import com.ats.payload.response.AirlineResponse;
import com.ats.repository.AirlineRepository;
import com.ats.service.AirlineService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirlineServiceImpl implements AirlineService {

    private final AirlineRepository airlineRepo;


    @Override
    public AirlineResponse createAirline(AirlineRequest request, Long ownerId) {
        Airline airline = AirlineMapper.toEntity(request, ownerId);
        Airline savedAirline = airlineRepo.save(airline);

        return AirlineMapper.toResponse(savedAirline);
    }

    @Override
    public AirlineResponse getAirlineByOwner(Long ownerId) throws Exception {
        Airline airline = airlineRepo.findByOwnerId(ownerId).orElseThrow(
                () -> new Exception("Airline not found with ownerId" + ownerId));

        return AirlineMapper.toResponse(airline);
    }

    @Override
    public AirlineResponse getAirlineById(Long id) throws Exception {

        Airline airline = airlineRepo.findById(id).orElseThrow(
                () -> new Exception("Airline not found by ID : " + id));
        return AirlineMapper.toResponse(airline);
    }

    @Override
    public AirlineResponse updateAirline(AirlineRequest request, Long ownerId) throws Exception {
        Airline airline = airlineRepo.findByOwnerId(ownerId).orElseThrow(
                () -> new Exception("Airline not found with ownerId" + ownerId));

        AirlineMapper.updateEntity(airline, request);
        Airline updatedAirline = airlineRepo.save(airline);

        return AirlineMapper.toResponse(updatedAirline);
    }

    @Override
    public Page<AirlineResponse> getAllAirlines(Pageable pageable) {

        return airlineRepo.findAll(pageable).map(AirlineMapper::toResponse);
    }

    @Override
    public void deleteAirlineById(Long id, Long ownerId) throws Exception {
        Airline airline = airlineRepo.findByOwnerId(ownerId).orElseThrow(
                () -> new Exception("Airline not found with ownerId" + ownerId));

        airlineRepo.delete(airline);
    }

    @Override
    public AirlineResponse updateAirlineStatusByAdmin(Long airlineId, AirlineStatus status) throws Exception {

        Airline airline = airlineRepo.findById(airlineId).orElseThrow(
                () -> new Exception("Airline not found by ID : " + airlineId));

        airline.setStatus(status);
        Airline updatedAirline = airlineRepo.save(airline);
        return AirlineMapper.toResponse(airline);
    }

    @Override
    public List<AirlineDropdownItem> getAirlineDropdownItems() {

        return airlineRepo.findByStatus(AirlineStatus.ACTIVE)
                .stream()
                .map(a -> AirlineDropdownItem.builder()
                        .id(a.getId())
                        .name(a.getName())
                        .iataCode(a.getIataCode())
                        .icaoCode(a.getIcaoCode())
                        .logoUrl(a.getLogoUrl())
                        .build()).toList();
    }
}
