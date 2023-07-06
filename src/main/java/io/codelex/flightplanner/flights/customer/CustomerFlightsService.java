package io.codelex.flightplanner.flights.customer;

import io.codelex.flightplanner.flights.FlightsRepository;
import io.codelex.flightplanner.flights.admin.domain.Airport;
import io.codelex.flightplanner.flights.admin.domain.Flight;
import io.codelex.flightplanner.flights.admin.response.AddFlightResponse;
import io.codelex.flightplanner.flights.customer.request.SearchFlightRequest;
import io.codelex.flightplanner.flights.customer.response.SearchedFlightsResponse;
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

    public AddFlightResponse getFlightById(String flightId) {
        Flight flightFromDatabase = flightsRepository.getFlightById(flightId);
        return new AddFlightResponse(flightFromDatabase.getFrom(), flightFromDatabase.getTo(), flightFromDatabase.getCarrier(),
                flightFromDatabase.getDepartureTime(), flightFromDatabase.getArrivalTime(), flightFromDatabase.getId());
    }

    public SearchedFlightsResponse<Flight> searchFlights(SearchFlightRequest flight) {
        return flightsRepository.searchFlights(flight);
    }
}
