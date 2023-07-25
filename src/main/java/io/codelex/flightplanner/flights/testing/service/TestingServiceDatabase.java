package io.codelex.flightplanner.flights.testing.service;

import io.codelex.flightplanner.flights.repository.database.AirportsRepositoryDatabase;
import io.codelex.flightplanner.flights.repository.database.FlightsRepositoryDatabase;

public class TestingServiceDatabase implements TestingService {
    private FlightsRepositoryDatabase flightsRepositoryDatabase;
    private AirportsRepositoryDatabase airportsRepositoryDatabase;

    public TestingServiceDatabase(FlightsRepositoryDatabase flightsRepositoryDatabase, AirportsRepositoryDatabase airportsRepositoryDatabase) {
        this.flightsRepositoryDatabase = flightsRepositoryDatabase;
        this.airportsRepositoryDatabase = airportsRepositoryDatabase;
    }
    public void clearDatabase() {
        flightsRepositoryDatabase.deleteAll();
        airportsRepositoryDatabase.deleteAll();
    }
}
