package com.ats.service.impl;

import com.ats.config.CityMapper;
import com.ats.entity.City;
import com.ats.payload.request.CityRequest;
import com.ats.payload.response.CityResponse;
import com.ats.repository.CityRepository;
import com.ats.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Override
    public CityResponse createCity(CityRequest cityRequest) throws Exception {
        if(cityRepository.existsByCityCode(cityRequest.getCityCode())){
            throw new Exception("City with given code already exists");
        }
        City city = CityMapper.toEntity(cityRequest);
        City results = cityRepository.save(city);

        return CityMapper.cityToResponse(results);
    }

    @Override
    public CityResponse getCityById(Long id) throws Exception {
        City city = cityRepository.findById(id).orElseThrow(
                () -> new Exception("City doesn't exist by the given id")
        );

        return CityMapper.cityToResponse(city);
    }

    @Override
    public CityResponse updateCity(Long id, CityRequest cityRequest) throws Exception {
        City city = cityRepository.findById(id).orElseThrow(
                () -> new Exception("City not found by the given id")
        );

        if(cityRepository.existsByCityCodeAndIdNot(cityRequest.getCityCode(), id)){
            throw new Exception("City with the given code already exists");
        }

        City updatedCity = cityRepository.save(CityMapper.updateCityEntity(city, cityRequest));

        return CityMapper.cityToResponse(updatedCity);
    }

    @Override
    public void deleteCity(Long id) throws Exception {

        City city = cityRepository.findById(id).orElseThrow(
                () -> new Exception("City not found by the given id")
        );

        cityRepository.deleteById(id);

    }

    @Override
    public Page<CityResponse> getAllCities(Pageable pageable) {
        return cityRepository.findAll(pageable).map(CityMapper::cityToResponse);
    }

    @Override
    public Page<CityResponse> searchCities(String keyword, Pageable pageable) {
        return cityRepository.searchByKeyword(keyword, pageable).map(CityMapper::cityToResponse);
    }

    @Override
    public Page<CityResponse> getCitiesByCountryCode(String countryCode, Pageable pageable) {
        return cityRepository.findByCountryCodeIgnoreCase(countryCode,pageable).map(CityMapper::cityToResponse);
    }

    @Override
    public boolean cityExists(String cityCode) {
        return cityRepository.existsByCityCode(cityCode);
    }

}
