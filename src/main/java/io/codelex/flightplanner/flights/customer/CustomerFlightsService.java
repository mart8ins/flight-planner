package io.codelex.flightplanner.flights.customer;

import io.codelex.flightplanner.flights.FlightsRepository;
import io.codelex.flightplanner.flights.admin.domain.Airport;
import io.codelex.flightplanner.flights.admin.domain.Flight;
import io.codelex.flightplanner.flights.admin.response.AddFlightResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerFlightsService {

    Logger logger = LoggerFactory.getLogger(CustomerFlightsService.class);

    private FlightsRepository flightsRepository;

    public CustomerFlightsService(FlightsRepository flightsRepository) {
        this.flightsRepository = flightsRepository;
    }

    public List<Airport> searchAirport(String airportSearchQuery){
        return flightsRepository.searchAirport(airportSearchQuery);
    }

    public AddFlightResponse getFlightById(String flightId) {
        Flight flightFromDatabase = flightsRepository.getFlightById(flightId);
        logger.error("flightFromDatabase ir : " + flightFromDatabase);
        return new AddFlightResponse(flightFromDatabase.getFrom(), flightFromDatabase.getTo(), flightFromDatabase.getCarrier(),
                flightFromDatabase.getDepartureTime(), flightFromDatabase.getArrivalTime(), flightFromDatabase.getId());
    }

    public String searchFlights(String object) {
        return flightsRepository.searchFlights(object);
    }
}
