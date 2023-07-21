package io.codelex.flightplanner.flights.customer.service;

import io.codelex.flightplanner.flights.admin.domain.Airport;
import io.codelex.flightplanner.flights.admin.domain.Flight;
import io.codelex.flightplanner.flights.admin.response.FlightResponse;
import io.codelex.flightplanner.flights.customer.request.SearchFlightRequest;
import io.codelex.flightplanner.flights.customer.response.SearchedFlightsResponse;
import io.codelex.flightplanner.flights.repository.databasePostgres.FlightsRepositoryPostgresDB;
import io.codelex.flightplanner.flights.utils.HandleDatesFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

public class CustomerFlightsServicePostgresDB implements CustomerFlightsService{
    Logger logger = LoggerFactory.getLogger(CustomerFlightsServicePostgresDB.class);
    private FlightsRepositoryPostgresDB flightsRepositoryPostgresDB;

    public CustomerFlightsServicePostgresDB(FlightsRepositoryPostgresDB flightsRepositoryPostgresDB) {
        this.flightsRepositoryPostgresDB = flightsRepositoryPostgresDB;
    }

    public List<Airport> searchAirport(String airportSearchQuery) {
        return null;
    }


    public FlightResponse getFlightById(String flightId) {
        Optional<Flight> foundFlightForCustomer = flightsRepositoryPostgresDB.findById(Integer.parseInt(flightId));
        if(foundFlightForCustomer.isPresent()){
            logger.info("Flight with id: " + flightId + " was found.");
            return new FlightResponse(foundFlightForCustomer.get().getId(), foundFlightForCustomer.get().getCarrier(), HandleDatesFormatter.formatLocalDateTimeToString(foundFlightForCustomer.get().getDepartureTime()),
                    HandleDatesFormatter.formatLocalDateTimeToString(foundFlightForCustomer.get().getArrivalTime()), foundFlightForCustomer.get().getFrom(), foundFlightForCustomer.get().getTo());
        }
        logger.info("Failed to find flight with id: " + flightId);
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no flight with given id.");
    }

    public SearchedFlightsResponse<Flight> searchFlights(SearchFlightRequest flight) {
        return null;
    }
}
