package io.codelex.flightplanner.flights.customer;

import io.codelex.flightplanner.flights.FlightsRepository;
import io.codelex.flightplanner.flights.admin.domain.Airport;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerFlightsService {

    private FlightsRepository flightsRepository;

    public CustomerFlightsService(FlightsRepository flightsRepository) {
        this.flightsRepository = flightsRepository;
    }

    public List<Airport> searchAirport(String airportSearchQuery){
        return flightsRepository.searchAirport(airportSearchQuery);
    }

    public String getFlightById(String flightId) {
        return "";
//        return flightsRepository.getFlightById(flightId);
    }

    public String searchFlights(String object) {
        return flightsRepository.searchFlights(object);
    }
}
