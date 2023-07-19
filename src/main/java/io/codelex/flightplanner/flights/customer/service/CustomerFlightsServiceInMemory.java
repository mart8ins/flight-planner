package io.codelex.flightplanner.flights.customer.service;

import io.codelex.flightplanner.flights.repository.FlightsRepositoryInMemory;
import io.codelex.flightplanner.flights.admin.domain.Airport;
import io.codelex.flightplanner.flights.admin.domain.Flight;
import io.codelex.flightplanner.flights.admin.response.FlightResponse;
import io.codelex.flightplanner.flights.customer.request.SearchFlightRequest;
import io.codelex.flightplanner.flights.customer.response.SearchedFlightsResponse;
import io.codelex.flightplanner.flights.utils.HandleDatesFormatter;

import java.util.List;

public class CustomerFlightsServiceInMemory implements CustomerFlightsService {

    private FlightsRepositoryInMemory flightsRepositoryInMemory;

    public CustomerFlightsServiceInMemory(FlightsRepositoryInMemory flightsRepositoryInMemory) {
        this.flightsRepositoryInMemory = flightsRepositoryInMemory;
    }

    public List<Airport> searchAirport(String airportSearchQuery){
        return flightsRepositoryInMemory.searchAirport(airportSearchQuery);
    }

    public FlightResponse getFlightById(String flightId) {
        Flight flightFromDatabase = flightsRepositoryInMemory.getFlightById(flightId);

        String departureDateTime = HandleDatesFormatter.formatLocalDateTimeToString(flightFromDatabase.getDepartureTime());
        String arrivalDateTime = HandleDatesFormatter.formatLocalDateTimeToString(flightFromDatabase.getArrivalTime());
        return new FlightResponse(flightFromDatabase.getId(), flightFromDatabase.getFrom(), flightFromDatabase.getTo(), flightFromDatabase.getCarrier(),
                departureDateTime, arrivalDateTime);
    }

    public SearchedFlightsResponse<Flight> searchFlights(SearchFlightRequest flight) {
        return flightsRepositoryInMemory.searchFlights(flight);
    }
}
