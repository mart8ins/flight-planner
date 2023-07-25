package io.codelex.flightplanner.flights.repository.database;

import io.codelex.flightplanner.flights.admin.domain.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportsRepositoryDatabase extends JpaRepository<Airport, Integer> {
}
