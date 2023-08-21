package io.codelex.flightplanner.flights.repository.database;

import io.codelex.flightplanner.flights.admin.domain.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FlightsRepositoryDatabase extends JpaRepository<Flight, Integer> {

    @Query("SELECT f from Flight f WHERE f.from.airport = :from and f.to.airport = :to and f.departureTime = DATE(:departure)")
    List<Flight> findFlights(
            @Param("from")
            String from,
            @Param("to")
            String to,
            @Param("departure")
            LocalDate departure
    );
}
