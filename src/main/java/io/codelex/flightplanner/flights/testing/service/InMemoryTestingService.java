package io.codelex.flightplanner.flights.testing.service;

import io.codelex.flightplanner.flights.repository.InMemoryFlightsRepository;
import org.springframework.stereotype.Service;

@Service
public class InMemoryTestingService implements TestingService {
    private InMemoryFlightsRepository inMemoryFlightsRepository;

    public InMemoryTestingService(InMemoryFlightsRepository inMemoryFlightsRepository) {
        this.inMemoryFlightsRepository = inMemoryFlightsRepository;
    }

    public void clearDatabase(){
        inMemoryFlightsRepository.clearDatabase();
    }
}
