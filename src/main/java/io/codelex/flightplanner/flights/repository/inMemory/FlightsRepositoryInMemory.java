package io.codelex.flightplanner.flights.repository.inMemory;

import io.codelex.flightplanner.flights.admin.service.AdminValidationsService;
import io.codelex.flightplanner.flights.admin.domain.Flight;
import io.codelex.flightplanner.flights.admin.domain.Airport;
import io.codelex.flightplanner.flights.admin.request.FlightRequest;
import io.codelex.flightplanner.flights.admin.response.FlightResponse;
import io.codelex.flightplanner.flights.customer.request.SearchFlightRequest;
import io.codelex.flightplanner.flights.customer.response.SearchedFlightsResponse;
import io.codelex.flightplanner.flights.utils.HandleDatesFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FlightsRepositoryInMemory {
    private List<Flight> flightInMemories = new ArrayList<>();
    private Map<String, Airport> allAirports = new HashMap();
    AdminValidationsService adminValidationsService;
    Logger logger = LoggerFactory.getLogger(FlightsRepositoryInMemory.class);

    public FlightsRepositoryInMemory(AdminValidationsService adminValidationsService) {
        this.adminValidationsService = adminValidationsService;
    }

    public Flight getFlightById(String flightId) {
        List<Flight> isFlight = flightInMemories.stream().filter(fl -> flightId.equals(String.valueOf(fl.getId()))).toList();
        if(isFlight.size() > 0) {
            logger.info("Flight with id: " + flightId + " was found.");
            return isFlight.get(0);
        } else {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no flight with given id.");
        }
    }

    public synchronized FlightResponse saveFlight(FlightRequest flightRequest) {
        LocalDateTime departureDateTime = HandleDatesFormatter.formatStringToDateTime(flightRequest.getDepartureTime());
        LocalDateTime arrivalDateTime = HandleDatesFormatter.formatStringToDateTime(flightRequest.getArrivalTime());
        adminValidationsService.validateRequest(flightInMemories, flightRequest, departureDateTime, arrivalDateTime);

        int lastId = flightInMemories.stream().mapToInt(fl -> fl.getId()).max().orElse(0);
        Flight flightToSave = new Flight(lastId + 1,flightRequest.getCarrier(), departureDateTime, arrivalDateTime, flightRequest.getFrom(), flightRequest.getTo());
        flightInMemories.add(flightToSave);
        logger.info("Flight added to database: " + flightToSave);

        addAirports(flightToSave);

        String departureDateTimeString = HandleDatesFormatter.formatLocalDateTimeToString(flightToSave.getDepartureTime());
        String arrivalDateTimeString = HandleDatesFormatter.formatLocalDateTimeToString(flightToSave.getArrivalTime());
        return new FlightResponse(flightToSave.getId(), flightToSave.getFrom(), flightToSave.getTo(), flightToSave.getCarrier(), departureDateTimeString, arrivalDateTimeString);
    }

    public synchronized String deleteFlight(String flightId) {
        boolean removed = flightInMemories.removeIf(fl -> flightId.equals(String.valueOf(fl.getId())));
        if(removed) {
            logger.info("Flight with id: " + flightId + " removed from database.");
            return "Flight with id: " + flightId + " removed from database.";
        } else {
            logger.info("Flight for deletion with id: " + flightId + " was not found.");
            return "Flight for deletion with id: " + flightId + " was not found.";
        }
    }

    public List<Airport> searchAirport(String airportSearchQuery){
        List<Airport> foundAirportInMemories = new ArrayList<>();
        for(Map.Entry<String, Airport> entry: allAirports.entrySet()) {
            if(entry.getKey().toLowerCase().trim().contains(airportSearchQuery.toLowerCase().trim())) {
                foundAirportInMemories.add(entry.getValue());
            }
        }
        logger.info("Using search query: " + airportSearchQuery + " found airports: " + foundAirportInMemories);
        return foundAirportInMemories;
    }

    public SearchedFlightsResponse<Flight> searchFlights(SearchFlightRequest flight) {
        SearchedFlightsResponse result = new SearchedFlightsResponse(0, 0, new ArrayList<Flight>());

        if(flight.getFrom().equals(flight.getTo())) {
            logger.error("Tried to search flight with invalid data, flight from: " + flight.getFrom() + " flight to: " + flight.getTo());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid search data. From and To airports are equal.");
        }
        logger.info("Searched flight is " + flight);

        DateTimeFormatter flightDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<Flight> foundFlightInMemories = flightInMemories.stream().filter(fl -> flight.getFrom().equals(fl.getFrom().getAirport()) &&
                flight.getTo().equals(fl.getTo().getAirport()) &&
                fl.getDepartureTime().format(flightDateFormatter).equals(flight.getDepartureDate())).toList();

        result.setTotalItems(foundFlightInMemories.size());
        result.setItems(foundFlightInMemories);
        result.setPage(foundFlightInMemories.size() / 10);
        return result;
    }

    public synchronized void clearDatabase(){
        logger.info("Database cleared.");
        flightInMemories.clear();
        allAirports.clear();
    }

    private synchronized void addAirports(Flight flight){
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
