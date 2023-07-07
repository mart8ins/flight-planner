package io.codelex.flightplanner.flights;

import io.codelex.flightplanner.flights.admin.domain.Flight;
import io.codelex.flightplanner.flights.admin.domain.Airport;
import io.codelex.flightplanner.flights.admin.response.FlightResponse;
import io.codelex.flightplanner.flights.customer.request.SearchFlightRequest;
import io.codelex.flightplanner.flights.customer.response.SearchedFlightsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FlightsRepository {

    Logger logger = LoggerFactory.getLogger(FlightsRepository.class);
    private List<Flight> flights = new ArrayList<>();
    private Map<String, Airport> allAirports = new HashMap();

    public Flight getFlightById(String flightId) {
        List<Flight> isFlight = flights.stream().filter(fl -> flightId.equals(String.valueOf(fl.getId()))).toList();
        if(isFlight.size() > 0) {
            logger.info("Flight with id: " + flightId + " was found.");
            return isFlight.get(0);
        } else {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no flight with given id.");
        }
    }

    public synchronized FlightResponse saveFlight(Flight flight) {
        flights.add(flight);
        logger.info("Flight added to database: " + flight);
        return new FlightResponse(flight.getFrom(), flight.getTo(), flight.getCarrier(), flight.getDepartureTime(), flight.getArrivalTime(), flight.getId());
    }

    public synchronized String deleteFlight(String flightId) {
        boolean removed = flights.removeIf(fl -> flightId.equals(String.valueOf(fl.getId())));
        if(removed) {
            logger.info("Flight with id: " + flightId + " removed from database.");
            return "Flight with id: " + flightId + " removed from database.";
        } else {
            logger.info("Flight for deletion with id: " + flightId + " was not found.");
            return "Flight for deletion with id: " + flightId + " was not found.";
        }
    }

    public List<Airport> searchAirport(String airportSearchQuery){
        List<Airport> foundAirports = new ArrayList<>();
        for(Map.Entry<String, Airport> entry: allAirports.entrySet()) {
            if(entry.getKey().toLowerCase().trim().contains(airportSearchQuery.toLowerCase().trim())) {
                foundAirports.add(entry.getValue());
            }
        }
        logger.info("Using search query: " + airportSearchQuery + " found airports: " + foundAirports);
        return foundAirports;
    }

    public SearchedFlightsResponse<Flight> searchFlights(SearchFlightRequest flight) {
        SearchedFlightsResponse result = new SearchedFlightsResponse(0, 0, new ArrayList<Flight>());

        if(flight.getFrom().equals(flight.getTo())) {
            logger.error("Tried to search flight with invalid data, flight from: " + flight.getFrom() + " flight to: " + flight.getTo());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid search data. From and To airports are equal.");
        }
        logger.info("Searched flight is " + flight);

        List<Flight> foundFlights = flights.stream().filter(fl -> flight.getFrom().equals(fl.getFrom().getAirport()) &&
                flight.getTo().equals(fl.getTo().getAirport()) &&
                fl.getDepartureTime().contains(flight.getDepartureDate())).toList();
        result.setTotalItems(foundFlights.size());
        result.setItems(foundFlights);
        result.setPage(foundFlights.size() / 10);
        return result;
    }

    public synchronized void clearDatabase(){
        logger.info("Database cleared.");
        flights.clear();
        allAirports.clear();
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public synchronized void addAirports(Flight flight){
        StringBuilder airportFrom = new StringBuilder();
        airportFrom.append(flight.getFrom().getCountry() + " ");
        airportFrom.append(flight.getFrom().getCity() + " ");
        airportFrom.append(flight.getFrom().getAirport());

        StringBuilder airportTo = new StringBuilder();
        airportTo.append(flight.getTo().getCountry() + " ");
        airportTo.append(flight.getTo().getCity() + " ");
        airportTo.append(flight.getTo().getAirport());
        allAirports.put(airportFrom.toString(), flight.getFrom());
        allAirports.put(airportTo.toString(), flight.getTo());
        logger.info("Airport data from flight added to airports database.");
    }
}
