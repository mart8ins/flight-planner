package io.codelex.flightplanner.flights.customer.service;

import io.codelex.flightplanner.flights.repository.memory.FlightsRepositoryMemory;
import io.codelex.flightplanner.flights.admin.domain.Airport;
import io.codelex.flightplanner.flights.admin.domain.Flight;
import io.codelex.flightplanner.flights.admin.response.FlightResponse;
import io.codelex.flightplanner.flights.customer.request.SearchFlightRequest;
import io.codelex.flightplanner.flights.customer.response.SearchedFlightsResponse;
import io.codelex.flightplanner.flights.utils.HandleDatesFormatter;

import java.util.List;

public class CustomerServiceMemory implements CustomerService {

    private FlightsRepositoryMemory flightsRepositoryMemory;

    public CustomerServiceMemory(FlightsRepositoryMemory flightsRepositoryMemory) {
        this.flightsRepositoryMemory = flightsRepositoryMemory;
    }

    public List<Airport> searchAirport(String airportSearchQuery){
        return flightsRepositoryMemory.searchAirport(airportSearchQuery);
    }

    public FlightResponse getFlightById(String flightId) {
        Flight flightFromDatabase = flightsRepositoryMemory.getFlightById(flightId);

        String departureDateTime = HandleDatesFormatter.formatLocalDateTimeToString(flightFromDatabase.getDepartureTime());
        String arrivalDateTime = HandleDatesFormatter.formatLocalDateTimeToString(flightFromDatabase.getArrivalTime());
        return new FlightResponse(flightFromDatabase.getId(), flightFromDatabase.getCarrier(),
                departureDateTime, arrivalDateTime, flightFromDatabase.getFrom(), flightFromDatabase.getTo());
    }

    public SearchedFlightsResponse<Flight> searchFlights(SearchFlightRequest flight) {
        return flightsRepositoryMemory.searchFlights(flight);
    }
}
