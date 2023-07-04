package io.codelex.flightplanner.flights.admin;

import io.codelex.flightplanner.flights.FlightsRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminFlightsService {

    FlightsRepository flightsRepository;

    public AdminFlightsService(FlightsRepository flightsRepository) {
        this.flightsRepository = flightsRepository;
    }

    public String getFlightById(String flightId) {
        return flightsRepository.getFlightById(flightId);
    }

    public String saveFlight(String flightId) {
        return flightsRepository.saveFlight(flightId);
    }

    public String deleteFlight(String flightId) {
        return flightsRepository.deleteFlight(flightId);
    }


}
