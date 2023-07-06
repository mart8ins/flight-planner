package io.codelex.flightplanner.flights.admin;

import io.codelex.flightplanner.flights.admin.domain.Flight;
import io.codelex.flightplanner.flights.admin.request.AddFlightRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AdminValidationsService {

    private Logger logger = LoggerFactory.getLogger(AdminValidationsService.class);

    public void validateRequest(List<Flight> flights, AddFlightRequest flightRequest){
        boolean flightAlreadyExists = false;
        if(flights.size() > 0) {
            flightAlreadyExists = flights.stream().anyMatch(fl -> (fl.getFrom().equals(flightRequest.getFrom()) &&
                    fl.getTo().equals(flightRequest.getTo()) &&
                    fl.getCarrier().equals(flightRequest.getCarrier()) &&
                    fl.getDepartureTime().equals(flightRequest.getDepartureTime()) &&
                    fl.getArrivalTime().equals(flightRequest.getArrivalTime())));
        }

        if (flightRequest.getFrom().getAirport().trim().toUpperCase().equals(flightRequest.getTo().getAirport().trim().toUpperCase())) {
            logger.error("Trying to add flight what contains the same departure and arrival airport.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Departure airport is the same as arrival.");
        }

        if (flightAlreadyExists) {
            logger.error("Flight what you trying add already exists in database.");
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This flight already exists in database");
        }

        DateTimeFormatter departureTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime departureTime = LocalDateTime.parse(flightRequest.getDepartureTime(), departureTimeFormatter);

        DateTimeFormatter arrivalTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime arrivalTime = LocalDateTime.parse(flightRequest.getArrivalTime(), arrivalTimeFormatter);

        if (arrivalTime.isBefore(departureTime) || arrivalTime.isEqual(departureTime)) {
            logger.error("Incorrect arrival and departure dates. Arrival time is the same or before departure.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect arrival and departure dates.");
        }
    }
}
