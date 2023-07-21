package io.codelex.flightplanner.flights.customer.service;

import io.codelex.flightplanner.flights.admin.domain.Airport;
import io.codelex.flightplanner.flights.admin.domain.Flight;
import io.codelex.flightplanner.flights.admin.response.FlightResponse;
import io.codelex.flightplanner.flights.customer.request.SearchFlightRequest;
import io.codelex.flightplanner.flights.customer.response.SearchedFlightsResponse;
import io.codelex.flightplanner.flights.repository.databasePostgres.AirportsRepositoryPostgresDB;
import io.codelex.flightplanner.flights.repository.databasePostgres.FlightsRepositoryPostgresDB;
import io.codelex.flightplanner.flights.utils.HandleDatesFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerFlightsServicePostgresDB implements CustomerFlightsService{
    Logger logger = LoggerFactory.getLogger(CustomerFlightsServicePostgresDB.class);
    private FlightsRepositoryPostgresDB flightsRepositoryPostgresDB;
    private AirportsRepositoryPostgresDB airportsRepositoryPostgresDB;

    public CustomerFlightsServicePostgresDB(FlightsRepositoryPostgresDB flightsRepositoryPostgresDB, AirportsRepositoryPostgresDB airportsRepositoryPostgresDB) {
        this.flightsRepositoryPostgresDB = flightsRepositoryPostgresDB;
        this.airportsRepositoryPostgresDB = airportsRepositoryPostgresDB;
    }

    public List<Airport> searchAirport(String airportSearchQuery) {
        String formattedSearchQuery = airportSearchQuery.toLowerCase().trim();
        List<Airport> allAirports = airportsRepositoryPostgresDB.findAll();
        List<Airport> foundAirports = new ArrayList<>();

        allAirports.forEach(a -> {
            if(a.getAirport().toLowerCase().contains(formattedSearchQuery) ||
                    a.getCountry().toLowerCase().contains(formattedSearchQuery)||
                    a.getCity().toLowerCase().contains(formattedSearchQuery)){
                foundAirports.add(a);
            }
        });
        return foundAirports;
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
        SearchedFlightsResponse result = new SearchedFlightsResponse(0, 0, new ArrayList<Flight>());

        if(flight.getFrom().equals(flight.getTo())) {
            logger.error("Tried to search flight with invalid data, flight from: " + flight.getFrom() + " flight to: " + flight.getTo());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid search data. From and To airports are equal.");
        }
        logger.info("Searched flight is " + flight);

        DateTimeFormatter flightDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<Flight> foundFlights = flightsRepositoryPostgresDB.findAll().stream().filter(fl -> flight.getFrom().equals(fl.getFrom().getAirport()) &&
                flight.getTo().equals(fl.getTo().getAirport()) &&
                fl.getDepartureTime().format(flightDateFormatter).equals(flight.getDepartureDate())).toList();

        result.setTotalItems(foundFlights.size());
        result.setItems(foundFlights);
        result.setPage(foundFlights.size() / 10);
        return result;
    }
}
