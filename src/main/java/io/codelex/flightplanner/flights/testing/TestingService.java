package io.codelex.flightplanner.flights.testing;

import io.codelex.flightplanner.flights.FlightsRepository;
import org.springframework.stereotype.Service;

@Service
public class TestingService {
    private FlightsRepository flightsRepository;

    public TestingService(FlightsRepository flightsRepository) {
        this.flightsRepository = flightsRepository;
    }

    public String clearDatabase(){
        return flightsRepository.clearDatabase();
    }
}
