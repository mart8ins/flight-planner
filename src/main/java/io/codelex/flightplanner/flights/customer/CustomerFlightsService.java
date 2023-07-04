package io.codelex.flightplanner.flights.customer;

import io.codelex.flightplanner.flights.FlightsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerFlightsService {

    private FlightsRepository flightsRepository;

    public CustomerFlightsService(FlightsRepository flightsRepository) {
        this.flightsRepository = flightsRepository;
    }

    public List<String> getAirports(String query){
        return flightsRepository.getAirports(query);
    }

    public String getFlightById(String flightId) {
        return flightsRepository.getFlightById(flightId);
    }

    public String searchFlights(String object) {
        return flightsRepository.searchFlights(object);
    }
}
