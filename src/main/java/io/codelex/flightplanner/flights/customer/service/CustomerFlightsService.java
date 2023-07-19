package io.codelex.flightplanner.flights.customer.service;

import io.codelex.flightplanner.flights.admin.domain.inMemory.AirportInMemory;
import io.codelex.flightplanner.flights.admin.domain.inMemory.FlightInMemory;
import io.codelex.flightplanner.flights.admin.response.FlightResponse;
import io.codelex.flightplanner.flights.customer.request.SearchFlightRequest;
import io.codelex.flightplanner.flights.customer.response.SearchedFlightsResponse;

import java.util.List;

public interface CustomerFlightsService {
    List<AirportInMemory> searchAirport(String airportSearchQuery);

    FlightResponse getFlightById(String flightId);
    SearchedFlightsResponse<FlightInMemory> searchFlights(SearchFlightRequest flight);
}
