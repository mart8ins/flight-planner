package io.codelex.flightplanner.flights.repository.databasePostgres;

import io.codelex.flightplanner.flights.admin.domain.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightsRepositoryPostgresDB extends JpaRepository<Flight, Integer> {
}
