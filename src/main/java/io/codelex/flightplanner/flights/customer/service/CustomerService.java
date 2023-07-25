package io.codelex.flightplanner.flights.customer.service;

import io.codelex.flightplanner.flights.admin.domain.Airport;
import io.codelex.flightplanner.flights.admin.domain.Flight;
import io.codelex.flightplanner.flights.admin.response.FlightResponse;
import io.codelex.flightplanner.flights.customer.request.SearchFlightRequest;
import io.codelex.flightplanner.flights.customer.response.SearchedFlightsResponse;

import java.util.List;

public interface CustomerService {
    List<Airport> searchAirport(String airportSearchQuery);

    FlightResponse getFlightById(String flightId);
    SearchedFlightsResponse<Flight> searchFlights(SearchFlightRequest flight);
}
