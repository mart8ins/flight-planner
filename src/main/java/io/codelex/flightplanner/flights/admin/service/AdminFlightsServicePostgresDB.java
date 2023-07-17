package io.codelex.flightplanner.flights.admin.service;

import io.codelex.flightplanner.flights.admin.request.FlightRequest;
import io.codelex.flightplanner.flights.admin.response.FlightResponse;
import io.codelex.flightplanner.flights.repository.FlightsRepositoryPostgresDB;

public class AdminFlightsServicePostgresDB implements AdminFlightService {
    private FlightsRepositoryPostgresDB flightsRepositoryPostgresDB;
    public AdminFlightsServicePostgresDB(FlightsRepositoryPostgresDB flightsRepositoryPostgresDB) {
        this.flightsRepositoryPostgresDB = flightsRepositoryPostgresDB;
    }
    public FlightResponse getFlightById(String flightId) {
        return null;
    }

    public FlightResponse saveFlight(FlightRequest flightRequest) {
        return null;
    }

    public String deleteFlight(String flightId) {
        return null;
    }
}
