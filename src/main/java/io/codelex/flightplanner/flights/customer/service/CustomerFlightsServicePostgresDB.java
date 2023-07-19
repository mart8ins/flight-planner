package io.codelex.flightplanner.flights.customer.service;

import io.codelex.flightplanner.flights.admin.domain.inMemory.AirportInMemory;
import io.codelex.flightplanner.flights.admin.domain.inMemory.FlightInMemory;
import io.codelex.flightplanner.flights.admin.response.FlightResponse;
import io.codelex.flightplanner.flights.customer.request.SearchFlightRequest;
import io.codelex.flightplanner.flights.customer.response.SearchedFlightsResponse;
import io.codelex.flightplanner.flights.repository.databasePostgres.FlightsRepositoryPostgresDB;

import java.util.List;

public class CustomerFlightsServicePostgresDB implements CustomerFlightsService{

    private FlightsRepositoryPostgresDB flightsRepositoryPostgresDB;

    public CustomerFlightsServicePostgresDB(FlightsRepositoryPostgresDB flightsRepositoryPostgresDB) {
        this.flightsRepositoryPostgresDB = flightsRepositoryPostgresDB;
    }

    public List<AirportInMemory> searchAirport(String airportSearchQuery) {
        return null;
    }


    public FlightResponse getFlightById(String flightId) {
        return null;
    }

    public SearchedFlightsResponse<FlightInMemory> searchFlights(SearchFlightRequest flight) {
        return null;
    }
}
