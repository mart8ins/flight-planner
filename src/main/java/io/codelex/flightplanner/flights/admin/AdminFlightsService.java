package io.codelex.flightplanner.flights.admin;

import io.codelex.flightplanner.flights.FlightsRepository;
import io.codelex.flightplanner.flights.admin.domain.Flight;
import io.codelex.flightplanner.flights.admin.request.AddFlightRequest;
import io.codelex.flightplanner.flights.admin.response.AddFlightResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AdminFlightsService {

    Logger logger = LoggerFactory.getLogger(AdminFlightsService.class);

    FlightsRepository flightsRepository;

    public AdminFlightsService(FlightsRepository flightsRepository) {
        this.flightsRepository = flightsRepository;
    }

    public String getFlightById(String flightId) {
        return flightsRepository.getFlightById(flightId);
    }

    public AddFlightResponse saveFlight(AddFlightRequest flightRequest) {
        List<Flight> flights = flightsRepository.getFlights();
        boolean flightAlreadyExists = false;
        if(flights.size() > 0) {
            flightAlreadyExists = flights.stream().anyMatch(fl -> (fl.getFrom().equals(flightRequest.getFrom()) &&
                    fl.getTo().equals(flightRequest.getTo()) &&
                    fl.getCarrier().equals(flightRequest.getCarrier()) &&
                    fl.getDepartureTime().equals(flightRequest.getDepartureTime()) &&
                    fl.getArrivalTime().equals(flightRequest.getArrivalTime())));
        }

        if(flightRequest.getFrom().getAirport().trim().toUpperCase().equals(flightRequest.getTo().getAirport().trim().toUpperCase())) {
            logger.error("Trying to add flight what contains the same departure and arrival airport.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Departure airport is the same as arrival.");
        }

        if(flightAlreadyExists) {
            logger.error("Flight what you trying add already exists in database.");
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This flight already exists in database");
        }

        int lastId = flights.stream().mapToInt(fl -> fl.getId()).max().orElse(0);
        Flight flightToSave = new Flight(flightRequest.getFrom(), flightRequest.getTo(),
                flightRequest.getCarrier(), flightRequest.getDepartureTime(), flightRequest.getArrivalTime(), lastId + 1);
        return flightsRepository.saveFlight(flightToSave);
    }

    public String deleteFlight(String flightId) {
        return flightsRepository.deleteFlight(flightId);
    }


}
