package com.ats.service.impl;

import com.ats.entity.Aircraft;
import com.ats.entity.Airline;
import com.ats.mapper.AircraftMapper;
import com.ats.payload.request.AircraftRequest;
import com.ats.payload.response.AircraftResponse;
import com.ats.repository.AircraftRepository;
import com.ats.repository.AirlineRepository;
import com.ats.service.AircraftService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AircraftServiceImpl implements AircraftService {

    private final AircraftRepository aircraftRepo;
    private final AirlineRepository airlineRepo;

    @Override
    public AircraftResponse createAircraft(AircraftRequest request, Long ownerId) throws Exception {

        Airline airline = airlineRepo.findByOwnerId(ownerId)
                .orElseThrow(() -> new Exception("Airline doesn't exist for this owner ID " + ownerId));

        Aircraft aircraft = AircraftMapper.toEntity(request, airline);
        if(aircraftRepo.existsByCode(aircraft.getCode())){
            throw new Exception("Aircraft Code already exists with another aircraft.");
        }

        if(aircraft.getSeatingCapacity() < aircraft.totalSeats()){
            throw new Exception("Seating capacity cannot exceeds total seats");
        }

        return AircraftMapper.toResponse(aircraftRepo.save(aircraft));
    }

    @Override
    public AircraftResponse getAircraftById(Long id) throws Exception {
        Aircraft aircraft = aircraftRepo.findById(id)
                .orElseThrow(() -> new Exception("Aircraft doesn't exists with the given ID " + id));

        return AircraftMapper.toResponse(aircraft);
    }

    @Override
    public List<AircraftResponse> getAllAircraftByOwnerId(Long ownerId) throws Exception {
        Airline airline = airlineRepo.findByOwnerId(ownerId)
                .orElseThrow(() -> new Exception("Airline doesn't exists with the given owner ID " + ownerId));

        return aircraftRepo.findByAirlineId(airline.getId()).stream()
                .map(AircraftMapper::toResponse)
                .toList();
    }

    @Override
    public AircraftResponse updateAircraft(Long id, AircraftRequest request, Long ownerId) throws Exception {

        Airline airline = airlineRepo.findByOwnerId(ownerId)
                .orElseThrow(() -> new Exception("Airline doesn't exists with the given owner ID " + ownerId));

        Aircraft aircraft =aircraftRepo.findByIdAndAirlineId(id, airline.getId());

        if(aircraft == null){
            throw new Exception("Aircraft doesn't exists with the given ID " + id);
        }

        if(request.getCode() != null
                && !aircraft.getCode().equals(request.getCode())
                && aircraftRepo.existsByCode(aircraft.getCode())){
            throw new Exception("Aircraft Code already exists with another aircraft.");
        }

        AircraftMapper.updateEntity(aircraft, request);
        Aircraft updatedAircraft = aircraftRepo.save(aircraft);
        return AircraftMapper.toResponse(updatedAircraft);
    }

    @Override
    public void deleteAircraft(Long id, Long ownerId) throws Exception {
        Airline airline = airlineRepo.findByOwnerId(ownerId)
                .orElseThrow(() -> new Exception("Airline doesn't exists with the given owner ID " + ownerId));

        Aircraft aircraft =aircraftRepo.findByIdAndAirlineId(id, airline.getId());

        if(aircraft == null){
            throw new Exception("Aircraft doesn't exists with the given ID " + id);
        }

        aircraftRepo.delete(aircraft);
    }


}
