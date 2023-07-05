package io.codelex.flightplanner.flights;

import io.codelex.flightplanner.flights.admin.domain.Flight;
import io.codelex.flightplanner.flights.admin.response.AddFlightResponse;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FlightsRepository {

    private List<Flight> flights = new ArrayList<>();

    // ADMIN
    public String getFlightById(String flightId) {
        return "Flight with id " + flightId;
    }

    public AddFlightResponse saveFlight(Flight flight) {
        flights.add(flight);
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
    public String clearDatabase(){
        return "Database cleared";
    }

    public List<Flight> getFlights() {
        return flights;
    }
}
