package io.codelex.flightplanner.flights.customer.service;

import io.codelex.flightplanner.flights.admin.domain.Airport;
import io.codelex.flightplanner.flights.admin.domain.Flight;
import io.codelex.flightplanner.flights.admin.response.FlightResponse;
import io.codelex.flightplanner.flights.customer.request.SearchFlightRequest;
import io.codelex.flightplanner.flights.customer.response.SearchedFlightsResponse;
import io.codelex.flightplanner.flights.repository.FlightsRepositoryPostgresDB;

import java.util.List;

public class CustomerFlightsServicePostgresDB implements CustomerFlightsService{

    private FlightsRepositoryPostgresDB flightsRepositoryPostgresDB;

    public CustomerFlightsServicePostgresDB(FlightsRepositoryPostgresDB flightsRepositoryPostgresDB) {
        this.flightsRepositoryPostgresDB = flightsRepositoryPostgresDB;
    }

    public List<Airport> searchAirport(String airportSearchQuery) {
        return null;
    }


    public FlightResponse getFlightById(String flightId) {
        return null;
    }

    public SearchedFlightsResponse<Flight> searchFlights(SearchFlightRequest flight) {
        return null;
    }
}
