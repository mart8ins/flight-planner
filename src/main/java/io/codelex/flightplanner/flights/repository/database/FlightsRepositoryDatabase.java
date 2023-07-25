package io.codelex.flightplanner.flights.repository.database;

import io.codelex.flightplanner.flights.admin.domain.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightsRepositoryDatabase extends JpaRepository<Flight, Integer> {
}
