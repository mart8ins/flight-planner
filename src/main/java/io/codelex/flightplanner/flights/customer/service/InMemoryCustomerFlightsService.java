package io.codelex.flightplanner.flights.customer.service;

import io.codelex.flightplanner.flights.repository.InMemoryFlightsRepository;
import io.codelex.flightplanner.flights.admin.domain.Airport;
import io.codelex.flightplanner.flights.admin.domain.Flight;
import io.codelex.flightplanner.flights.admin.response.FlightResponse;
import io.codelex.flightplanner.flights.customer.request.SearchFlightRequest;
import io.codelex.flightplanner.flights.customer.response.SearchedFlightsResponse;
import io.codelex.flightplanner.flights.utils.HandleDatesFormatter;

import java.util.List;

public class InMemoryCustomerFlightsService implements CustomerFlightsService {

    private InMemoryFlightsRepository inMemoryFlightsRepository;

    public InMemoryCustomerFlightsService(InMemoryFlightsRepository inMemoryFlightsRepository) {
        this.inMemoryFlightsRepository = inMemoryFlightsRepository;
    }

    public List<Airport> searchAirport(String airportSearchQuery){
        return inMemoryFlightsRepository.searchAirport(airportSearchQuery);
    }

    public FlightResponse getFlightById(String flightId) {
        Flight flightFromDatabase = inMemoryFlightsRepository.getFlightById(flightId);

        String departureDateTime = HandleDatesFormatter.formatLocalDateTimeToString(flightFromDatabase.getDepartureTime());
        String arrivalDateTime = HandleDatesFormatter.formatLocalDateTimeToString(flightFromDatabase.getArrivalTime());
        return new FlightResponse(flightFromDatabase.getFrom(), flightFromDatabase.getTo(), flightFromDatabase.getCarrier(),
                departureDateTime, arrivalDateTime, flightFromDatabase.getId());
    }

    public SearchedFlightsResponse<Flight> searchFlights(SearchFlightRequest flight) {
        return inMemoryFlightsRepository.searchFlights(flight);
    }
}
