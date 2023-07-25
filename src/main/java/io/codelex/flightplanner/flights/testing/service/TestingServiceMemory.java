package io.codelex.flightplanner.flights.testing.service;

import io.codelex.flightplanner.flights.repository.memory.FlightsRepositoryMemory;

public class TestingServiceMemory implements TestingService {
    private FlightsRepositoryMemory flightsRepositoryMemory;

    public TestingServiceMemory(FlightsRepositoryMemory flightsRepositoryMemory) {
        this.flightsRepositoryMemory = flightsRepositoryMemory;
    }

    public void clearDatabase(){
        flightsRepositoryMemory.clearDatabase();
    }
}
