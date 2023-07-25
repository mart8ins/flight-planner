package io.codelex.flightplanner.flights.repository.database;

import io.codelex.flightplanner.flights.admin.domain.Airport;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AirportsRepositoryDatabase extends JpaRepository<Airport, Integer> {
    List<Airport> findAirportsByAirportContainingIgnoreCaseOrCityContainingIgnoreCaseOrCountryContainingIgnoreCase(String airport,String city,String country);
}
