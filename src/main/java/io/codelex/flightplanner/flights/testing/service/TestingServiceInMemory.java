package io.codelex.flightplanner.flights.testing.service;

import io.codelex.flightplanner.flights.repository.inMemory.FlightsRepositoryInMemory;

public class TestingServiceInMemory implements TestingService {
    private FlightsRepositoryInMemory flightsRepositoryInMemory;

    public TestingServiceInMemory(FlightsRepositoryInMemory flightsRepositoryInMemory) {
        this.flightsRepositoryInMemory = flightsRepositoryInMemory;
    }

    public void clearDatabase(){
        flightsRepositoryInMemory.clearDatabase();
    }
}
