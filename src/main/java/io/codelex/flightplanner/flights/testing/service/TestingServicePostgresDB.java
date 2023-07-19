package io.codelex.flightplanner.flights.testing.service;

import io.codelex.flightplanner.flights.repository.databasePostgres.AirportsRepositoryPostgresDB;
import io.codelex.flightplanner.flights.repository.databasePostgres.FlightsRepositoryPostgresDB;

public class TestingServicePostgresDB implements TestingService {
    private FlightsRepositoryPostgresDB flightsRepositoryPostgresDB;
    private AirportsRepositoryPostgresDB airportsRepositoryPostgresDB;

    public TestingServicePostgresDB(FlightsRepositoryPostgresDB flightsRepositoryPostgresDB, AirportsRepositoryPostgresDB airportsRepositoryPostgresDB) {
        this.flightsRepositoryPostgresDB = flightsRepositoryPostgresDB;
        this.airportsRepositoryPostgresDB = airportsRepositoryPostgresDB;
    }
    public void clearDatabase() {
        flightsRepositoryPostgresDB.deleteAll();
        airportsRepositoryPostgresDB.deleteAll();
    }
}
