package io.codelex.flightplanner.flights;

import io.codelex.flightplanner.flights.admin.domain.Flight;
import io.codelex.flightplanner.flights.admin.response.AddFlightResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FlightsRepository {

    Logger logger = LoggerFactory.getLogger(FlightsRepository.class);

    private List<Flight> flights = new ArrayList<>();

    // ADMIN
    public Flight getFlightById(String flightId) {
        List<Flight> isFlight = flights.stream().filter(fl -> flightId.equals(fl.getId())).toList();
        if(isFlight.size() > 0) {
            logger.info("Flight with id: " + flightId + " was found.");
            return isFlight.get(0);
        } else {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no flight with given id.");
        }
    }

    public AddFlightResponse saveFlight(Flight flight) {
        flights.add(flight);
        logger.info("Flight added to database");
        return new AddFlightResponse(flight.getFrom(), flight.getTo(), flight.getCarrier(), flight.getDepartureTime(), flight.getArrivalTime(), flight.getId());
    }

    public String deleteFlight(String flightId) {
        boolean removed = flights.removeIf(fl -> flightId.equals(String.valueOf(fl.getId())));
        if(removed) {
            logger.info("Flight with id: " + flightId + " removed from database.");
            return "Flight with id: " + flightId + " removed from database.";
        } else {
            logger.info("Flight for deletion with id: " + flightId + " was not found.");
            return "Flight for deletion with id: " + flightId + " was not found.";
        }

    }

    // CUSTOMER
    public List<String> getAirports(String query){
        List<String> airports = new ArrayList<>();
        airports.add("UBG");
        airports.add(query);
        return airports;
    }

    public String searchFlights(String object) {
        return "Searched flight object is " + object;
    }

    // TESTING
    public void clearDatabase(){
        logger.info("Database cleared.");
        flights.clear();
    }

    public List<Flight> getFlights() {
        return flights;
    }
}
