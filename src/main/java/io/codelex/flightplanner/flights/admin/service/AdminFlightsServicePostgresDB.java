package io.codelex.flightplanner.flights.admin.service;

import io.codelex.flightplanner.flights.admin.domain.Airport;
import io.codelex.flightplanner.flights.admin.domain.Flight;
import io.codelex.flightplanner.flights.admin.request.FlightRequest;
import io.codelex.flightplanner.flights.admin.response.FlightResponse;
import io.codelex.flightplanner.flights.repository.databasePostgres.AirportsRepositoryPostgresDB;
import io.codelex.flightplanner.flights.repository.databasePostgres.FlightsRepositoryPostgresDB;
import io.codelex.flightplanner.flights.utils.HandleDatesFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;

import java.util.List;

public class AdminFlightsServicePostgresDB implements AdminFlightService {
    Logger logger = LoggerFactory.getLogger(AdminFlightsServicePostgresDB.class);
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
        Airport airport1 = new Airport(flightRequest.getFrom().getAirport(), flightRequest.getFrom().getCountry(), flightRequest.getFrom().getCity());
        Airport airport2 = new Airport(flightRequest.getTo().getAirport(), flightRequest.getTo().getCountry(), flightRequest.getTo().getCity());
        boolean airport1Exists = airportsRepositoryPostgresDB.exists(Example.of(airport1));
        boolean airport2Exists = airportsRepositoryPostgresDB.exists(Example.of(airport2));
        if(!airport1Exists) {
            airportsRepositoryPostgresDB.save(airport1);
        }
        if(!airport2Exists) {
            airportsRepositoryPostgresDB.save(airport2);
        }

        List<Flight> flightInMemories = flightsRepositoryPostgresDB.findAll();
        int lastId = flightInMemories.stream().mapToInt(fl -> fl.getId()).max().orElse(0);

        Flight flight = new Flight(lastId, flightRequest.getCarrier(), HandleDatesFormatter.formatStringToDateTime(flightRequest.getDepartureTime()), HandleDatesFormatter.formatStringToDateTime(flightRequest.getArrivalTime()), flightRequest.getFrom(), flightRequest.getTo());
        logger.error("Converted!!!!!!!! " + flight);

        Flight saved = flightsRepositoryPostgresDB.save(flight);
        logger.error("Saved!!!!!!!! " + saved);

        return null;
    }

    public String deleteFlight(String flightId) {
        return null;
    }
}
