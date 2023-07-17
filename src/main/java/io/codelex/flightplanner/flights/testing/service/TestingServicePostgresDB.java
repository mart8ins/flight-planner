package io.codelex.flightplanner.flights.testing.service;

import io.codelex.flightplanner.flights.repository.FlightsRepositoryPostgresDB;

public class TestingServicePostgresDB implements TestingService {
    private FlightsRepositoryPostgresDB flightsRepositoryPostgresDB;
    public TestingServicePostgresDB(FlightsRepositoryPostgresDB flightsRepositoryPostgresDB) {
        this.flightsRepositoryPostgresDB = flightsRepositoryPostgresDB;
    }
    public void clearDatabase() {

    }
}
