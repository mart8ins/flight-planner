package io.codelex.flightplanner.flights;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FlightsRepository {

    // ADMIN

    public String getFlightById(String flightId) {
        return "Flight with id " + flightId;
    }

    public String saveFlight(String flightId) {
        return "Flight saved: " + flightId;
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
    public String clearDatabase(){
        return "Database cleared";
    }
}
