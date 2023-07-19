package io.codelex.flightplanner.flights.admin.service;

import io.codelex.flightplanner.flights.admin.domain.inDatabasePostgres.AirportInDatabase;
import io.codelex.flightplanner.flights.admin.request.FlightRequest;
import io.codelex.flightplanner.flights.admin.response.FlightResponse;
import io.codelex.flightplanner.flights.repository.databasePostgres.AirportsRepositoryPostgresDB;
import io.codelex.flightplanner.flights.repository.databasePostgres.FlightsRepositoryPostgresDB;

import java.util.List;

public class AdminFlightsServicePostgresDB implements AdminFlightService {
    private FlightsRepositoryPostgresDB flightsRepositoryPostgresDB;
    private AirportsRepositoryPostgresDB airportsRepositoryPostgresDB;

    public AdminFlightsServicePostgresDB(FlightsRepositoryPostgresDB flightsRepositoryPostgresDB, AirportsRepositoryPostgresDB airportsRepositoryPostgresDB) {
        this.flightsRepositoryPostgresDB = flightsRepositoryPostgresDB;
        this.airportsRepositoryPostgresDB = airportsRepositoryPostgresDB;
    }
    public FlightResponse getFlightById(String flightId) {
        return null;
    }

    public FlightResponse saveFlight(FlightRequest flightRequest) {
        AirportInDatabase airport1 = new AirportInDatabase(flightRequest.getFrom().getAirport(), flightRequest.getFrom().getCountry(), flightRequest.getFrom().getCity());
        AirportInDatabase airport2 = new AirportInDatabase(flightRequest.getTo().getAirport(), flightRequest.getTo().getCountry(), flightRequest.getTo().getCity());
        airportsRepositoryPostgresDB.saveAll(List.of(airport1, airport2));

        return null;
    }

    public String deleteFlight(String flightId) {
        return null;
    }
}
