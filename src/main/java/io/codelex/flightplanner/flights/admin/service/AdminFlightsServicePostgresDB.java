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
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

public class AdminFlightsServicePostgresDB implements AdminFlightService {
    Logger logger = LoggerFactory.getLogger(AdminFlightsServicePostgresDB.class);
    private FlightsRepositoryPostgresDB flightsRepositoryPostgresDB;
    private AirportsRepositoryPostgresDB airportsRepositoryPostgresDB;

    private AdminValidationsService adminValidationsService;

    public AdminFlightsServicePostgresDB(FlightsRepositoryPostgresDB flightsRepositoryPostgresDB, AirportsRepositoryPostgresDB airportsRepositoryPostgresDB, AdminValidationsService adminValidationsService) {
        this.flightsRepositoryPostgresDB = flightsRepositoryPostgresDB;
        this.airportsRepositoryPostgresDB = airportsRepositoryPostgresDB;
        this.adminValidationsService = adminValidationsService;
    }
    public FlightResponse getFlightById(String flightId) {
        Optional<Flight> foundFlight = flightsRepositoryPostgresDB.findById(Integer.parseInt(flightId));

        if(foundFlight.isPresent()){
            logger.info("Flight with id: " + flightId + " was found.");
            return new FlightResponse(foundFlight.get().getId(), foundFlight.get().getCarrier(), HandleDatesFormatter.formatLocalDateTimeToString(foundFlight.get().getDepartureTime()),
                    HandleDatesFormatter.formatLocalDateTimeToString(foundFlight.get().getArrivalTime()), foundFlight.get().getFrom(), foundFlight.get().getTo());
        }
        logger.info("Failed to find flight with id: " + flightId);
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no flight with given id.");
    }

    public FlightResponse saveFlight(FlightRequest flightRequest) {
        List<Flight> flightsFromDatabase = flightsRepositoryPostgresDB.findAll();
        adminValidationsService.validateRequest(flightsFromDatabase,flightRequest);

        Airport airport1 = new Airport(flightRequest.getFrom().getAirport(), flightRequest.getFrom().getCountry(), flightRequest.getFrom().getCity());
        Airport airport2 = new Airport(flightRequest.getTo().getAirport(), flightRequest.getTo().getCountry(), flightRequest.getTo().getCity());

        boolean airport1Exists = airportsRepositoryPostgresDB.exists(Example.of(airport1));
        boolean airport2Exists = airportsRepositoryPostgresDB.exists(Example.of(airport2));
        if(!airport1Exists) {
            airportsRepositoryPostgresDB.save(airport1);
            logger.info("Airport: " + airport1 +  " added to database.");
        }
        if(!airport2Exists) {
            airportsRepositoryPostgresDB.save(airport2);
            logger.info("Airport: " + airport2 +  " added to database.");
        }
        Flight flight = new Flight(flightRequest.getCarrier(), HandleDatesFormatter.formatStringToDateTime(flightRequest.getDepartureTime()), HandleDatesFormatter.formatStringToDateTime(flightRequest.getArrivalTime()), flightRequest.getFrom(), flightRequest.getTo());

        Flight savedFlight = flightsRepositoryPostgresDB.save(flight);
        logger.info("Flight: " + savedFlight +  " saved in database.");
        return new FlightResponse(savedFlight.getId(), savedFlight.getCarrier(), HandleDatesFormatter.formatLocalDateTimeToString(savedFlight.getDepartureTime()), HandleDatesFormatter.formatLocalDateTimeToString(savedFlight.getArrivalTime()), savedFlight.getFrom(), savedFlight.getTo());
    }

    public String deleteFlight(String flightId) {
        return null;
    }
}
