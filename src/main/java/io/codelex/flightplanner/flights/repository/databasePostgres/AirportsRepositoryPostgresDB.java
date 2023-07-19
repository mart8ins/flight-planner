package io.codelex.flightplanner.flights.repository.databasePostgres;

import io.codelex.flightplanner.flights.admin.domain.inDatabasePostgres.AirportInDatabase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportsRepositoryPostgresDB extends JpaRepository<AirportInDatabase, Integer> {
}
