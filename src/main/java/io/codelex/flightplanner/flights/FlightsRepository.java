package io.codelex.flightplanner.flights;

import io.codelex.flightplanner.flights.admin.domain.Flight;
import io.codelex.flightplanner.flights.admin.response.AddFlightResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FlightsRepository {

    Logger logger = LoggerFactory.getLogger(FlightsRepository.class);

    private List<Flight> flights = new ArrayList<>();

    // ADMIN
    public String getFlightById(String flightId) {
        return "Flight with id " + flightId;
    }

    public AddFlightResponse saveFlight(Flight flight) {
        flights.add(flight);
        logger.info("Flight added to database");
        return new AddFlightResponse(flight.getFrom(), flight.getTo(), flight.getCarrier(), flight.getDepartureTime(), flight.getArrivalTime(), flight.getId());
    }

    public String deleteFlight(String flightId) {
        return "Flight deleted: " + flightId;
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
