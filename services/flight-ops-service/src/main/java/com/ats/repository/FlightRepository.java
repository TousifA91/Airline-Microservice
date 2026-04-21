package com.ats.repository;

import com.ats.entity.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query("""
            Select f from Flight f where f.airlineId = :airlineId
            and (:depId is null or f.departureAirportId = :depId)
            and (:arrId is null or f.arrivalAirportId = :arrId)
            """)
    Page<Flight> findByAirlineId(@Param("airlineId") Long airlineId,
                                 @Param("depId") Long depId,
                                 @Param("arrId") Long arrId,
                                 Pageable pageable);

    boolean existsByFlightNumber(String airlineId);

    boolean existsByFlightNumberAndIdNot(String airlineId, Long Id);

    Optional<Flight> findByAirlineIdAndId(Long airlineId, Long id);


}
