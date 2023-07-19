package io.codelex.flightplanner.flights.repository.databasePostgres;

import io.codelex.flightplanner.flights.admin.domain.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportsRepositoryPostgresDB extends JpaRepository<Airport, Integer> {
}
