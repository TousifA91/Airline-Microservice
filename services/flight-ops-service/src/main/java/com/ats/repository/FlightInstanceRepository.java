package com.ats.repository;

import com.ats.entity.FlightInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightInstanceRepository extends JpaRepository<FlightInstance, Long> {

    @Query("""
            select fi from FlightInstance fi where fi.airlineId = :airlineId
            and (:departureAirportId is null or fi.departureAirportId = :departureAiportId)
            """)
    List<FlightInstance> findByAirlineId(@Param("airlineId") Long airlineId);

}
