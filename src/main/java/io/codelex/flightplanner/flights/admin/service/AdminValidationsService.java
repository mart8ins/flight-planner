package io.codelex.flightplanner.flights.admin.service;

import io.codelex.flightplanner.flights.admin.domain.Flight;
import io.codelex.flightplanner.flights.admin.request.FlightRequest;
import io.codelex.flightplanner.flights.utils.HandleDatesFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminValidationsService {

    private Logger logger = LoggerFactory.getLogger(AdminValidationsService.class);

    public void validateRequest(List<Flight> flightsFromStore, FlightRequest flightRequest){
        LocalDateTime departureDateTime = HandleDatesFormatter.formatStringToDateTime(flightRequest.getDepartureTime());
        LocalDateTime arrivalDateTime = HandleDatesFormatter.formatStringToDateTime(flightRequest.getArrivalTime());

        boolean flightAlreadyExists = false;
        if(flightsFromStore.size() > 0) {
            flightAlreadyExists = flightsFromStore.stream().anyMatch(fl -> (fl.getFrom().equals(flightRequest.getFrom()) &&
                    fl.getTo().equals(flightRequest.getTo()) &&
                    fl.getCarrier().equals(flightRequest.getCarrier()) &&
                    fl.getDepartureTime().isEqual(departureDateTime) &&
                    fl.getArrivalTime().isEqual(arrivalDateTime)));
        }

        if (flightRequest.getFrom().getAirport().trim().toUpperCase().equals(flightRequest.getTo().getAirport().trim().toUpperCase())) {
            logger.error("Trying to add flight what contains the same departure and arrival airport.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Departure airport is the same as arrival.");
        }

        if (flightAlreadyExists) {
            logger.error("Flight what you trying add already exists in database.");
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This flight already exists in database");
        }

        if (arrivalDateTime.isBefore(departureDateTime) || arrivalDateTime.isEqual(departureDateTime)) {
            logger.error("Incorrect arrival and departure dates. Arrival time is the same or before departure.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect arrival and departure dates.");
        }
    }
}
