package io.codelex.flightplanner.flights.admin;

import io.codelex.flightplanner.flights.FlightsRepository;
import io.codelex.flightplanner.flights.admin.domain.Flight;
import io.codelex.flightplanner.flights.admin.request.AddFlightRequest;
import io.codelex.flightplanner.flights.admin.response.AddFlightResponse;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AdminFlightsService {

    FlightsRepository flightsRepository;

    public AdminFlightsService(FlightsRepository flightsRepository) {
        this.flightsRepository = flightsRepository;
    }

    public String getFlightById(String flightId) {
        return flightsRepository.getFlightById(flightId);
    }

    public AddFlightResponse saveFlight(AddFlightRequest flightRequest) {
        int lastId = flightsRepository.getFlights().stream().mapToInt(fl -> fl.getId()).max().orElse(0);
        Flight flightToSave = new Flight(flightRequest.getFrom(), flightRequest.getTo(),
                flightRequest.getCarrier(), flightRequest.getDepartureTime(), flightRequest.getArrivalTime(), lastId + 1);
        return flightsRepository.saveFlight(flightToSave);
    }

    public String deleteFlight(String flightId) {
        return flightsRepository.deleteFlight(flightId);
    }


}
