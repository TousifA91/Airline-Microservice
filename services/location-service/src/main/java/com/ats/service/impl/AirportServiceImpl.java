package com.ats.service.impl;

import com.ats.config.AirportMapper;
import com.ats.entity.Airport;
import com.ats.entity.City;
import com.ats.payload.request.AirportRequest;
import com.ats.payload.response.AirportResponse;
import com.ats.repository.AirportRepository;
import com.ats.repository.CityRepository;
import com.ats.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {

    private final AirportRepository airportRepository;
    private final CityRepository cityRepository;

    @Override
    public AirportResponse createAirport(AirportRequest airportRequest) throws Exception {
        if(airportRepository.findByIataCode(airportRequest.getIataCode()).isPresent()){
            throw new Exception("Aiport with IATA code already exists.");
        }

        City city = cityRepository.findById(airportRequest.getCityId())
                .orElseThrow(() -> new Exception("City not found."));

        Airport airport = AirportMapper.toEntity(airportRequest);
        airport.setCity(city);

        Airport saveAirport = airportRepository.save(airport);
        return AirportMapper.toResponse(saveAirport);
    }

    @Override
    public AirportResponse getAirportById(Long id) throws Exception {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new Exception("Airport does not exist by the given ID."));

        return AirportMapper.toResponse(airport);
    }

    @Override
    public List<AirportResponse> getAllAirports() {
        return airportRepository.findAll().stream()
                .map(AirportMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AirportResponse updateAirport(Long id, AirportRequest airportRequest) throws Exception {

        Airport existingAirport = airportRepository.findById(id)
                .orElseThrow(() -> new Exception("Airport doesn't exists with the given Id: " + id));

        if(airportRequest.getIataCode() != null
                && !existingAirport.getIataCode().equals(airportRequest.getIataCode())
                && airportRepository.findByIataCode(airportRequest.getIataCode()).isPresent()
        ){
            throw new Exception("Airport with IATA code already exists.");
        }

        AirportMapper.updateEntity(existingAirport, airportRequest);
        Airport updatedAirport = airportRepository.save(existingAirport);
        return AirportMapper.toResponse(updatedAirport);
    }

    @Override
    public void deleteAirportById(Long id) throws Exception {

        Airport existingAirport = airportRepository.findById(id)
                .orElseThrow(() -> new Exception("Airport doesn't exists with the given Id: " + id));

        airportRepository.delete(existingAirport);

    }

    @Override
    public List<AirportResponse> getAirportByCityId(Long cityId) {
        return airportRepository.findByCityId(cityId)
                .stream()
                .map(AirportMapper::toResponse)
                .collect(Collectors.toList());
    }
}
