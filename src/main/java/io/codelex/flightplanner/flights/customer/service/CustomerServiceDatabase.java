package io.codelex.flightplanner.flights.customer.service;

import io.codelex.flightplanner.flights.admin.domain.Airport;
import io.codelex.flightplanner.flights.admin.domain.Flight;
import io.codelex.flightplanner.flights.admin.response.FlightResponse;
import io.codelex.flightplanner.flights.customer.request.SearchFlightRequest;
import io.codelex.flightplanner.flights.customer.response.SearchedFlightsResponse;
import io.codelex.flightplanner.flights.repository.database.AirportsRepositoryDatabase;
import io.codelex.flightplanner.flights.repository.database.FlightsRepositoryDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerServiceDatabase implements CustomerService {
    Logger logger = LoggerFactory.getLogger(CustomerServiceDatabase.class);
    private FlightsRepositoryDatabase flightsRepositoryDatabase;
    private AirportsRepositoryDatabase airportsRepositoryDatabase;

    public CustomerServiceDatabase(FlightsRepositoryDatabase flightsRepositoryDatabase, AirportsRepositoryDatabase airportsRepositoryDatabase) {
        this.flightsRepositoryDatabase = flightsRepositoryDatabase;
        this.airportsRepositoryDatabase = airportsRepositoryDatabase;
    }

    public List<Airport> searchAirport(String airportSearchQuery) {
        String formattedSearchQuery = airportSearchQuery.trim();
        List<Airport> foundAirports = airportsRepositoryDatabase.findAirportsByAirportContainingIgnoreCaseOrCityContainingIgnoreCaseOrCountryContainingIgnoreCase(formattedSearchQuery, formattedSearchQuery, formattedSearchQuery);
        return foundAirports;
    }

    public FlightResponse getFlightById(String flightId) {
        Optional<Flight> foundFlightForCustomer = flightsRepositoryDatabase.findById(Integer.parseInt(flightId));
        if(foundFlightForCustomer.isPresent()){
            logger.info("Flight with id: " + flightId + " was found.");
            return new FlightResponse(foundFlightForCustomer.get().getId(), foundFlightForCustomer.get().getCarrier(), foundFlightForCustomer.get().getDepartureTime(),
                    foundFlightForCustomer.get().getArrivalTime(), foundFlightForCustomer.get().getFrom(), foundFlightForCustomer.get().getTo());
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
        logger.info("Searched flight in database is " + flight);
        List<Flight> foundFlights = flightsRepositoryDatabase.findFlights(flight.getFrom().toUpperCase(), flight.getTo().toUpperCase(), flight.getDepartureDate());

        result.setTotalItems(foundFlights.size());
        result.setItems(foundFlights);
        result.setPage(foundFlights.size() / 10);
        return result;
    }
}
