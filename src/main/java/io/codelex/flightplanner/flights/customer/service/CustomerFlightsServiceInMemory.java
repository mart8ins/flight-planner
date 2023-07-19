package io.codelex.flightplanner.flights.customer.service;

import io.codelex.flightplanner.flights.repository.inMemory.FlightsRepositoryInMemory;
import io.codelex.flightplanner.flights.admin.domain.inMemory.AirportInMemory;
import io.codelex.flightplanner.flights.admin.domain.inMemory.FlightInMemory;
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

    public List<AirportInMemory> searchAirport(String airportSearchQuery){
        return flightsRepositoryInMemory.searchAirport(airportSearchQuery);
    }

    public FlightResponse getFlightById(String flightId) {
        FlightInMemory flightInMemoryFromDatabase = flightsRepositoryInMemory.getFlightById(flightId);

        String departureDateTime = HandleDatesFormatter.formatLocalDateTimeToString(flightInMemoryFromDatabase.getDepartureTime());
        String arrivalDateTime = HandleDatesFormatter.formatLocalDateTimeToString(flightInMemoryFromDatabase.getArrivalTime());
        return new FlightResponse(flightInMemoryFromDatabase.getId(), flightInMemoryFromDatabase.getFrom(), flightInMemoryFromDatabase.getTo(), flightInMemoryFromDatabase.getCarrier(),
                departureDateTime, arrivalDateTime);
    }

    public SearchedFlightsResponse<FlightInMemory> searchFlights(SearchFlightRequest flight) {
        return flightsRepositoryInMemory.searchFlights(flight);
    }
}
