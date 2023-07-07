package io.codelex.flightplanner.flights.admin;

import io.codelex.flightplanner.flights.FlightsRepository;
import io.codelex.flightplanner.flights.admin.domain.Flight;
import io.codelex.flightplanner.flights.admin.request.FlightRequest;
import io.codelex.flightplanner.flights.admin.response.FlightResponse;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AdminFlightsService {

    private FlightsRepository flightsRepository;
    private AdminValidationsService adminValidationsService;

    public AdminFlightsService(FlightsRepository flightsRepository, AdminValidationsService adminValidationsService) {
        this.flightsRepository = flightsRepository;
        this.adminValidationsService = adminValidationsService;
    }

    public FlightResponse getFlightById(String flightId) {
        Flight flightFromDatabase = flightsRepository.getFlightById(flightId);
        return new FlightResponse(flightFromDatabase.getFrom(), flightFromDatabase.getTo(), flightFromDatabase.getCarrier(),
                flightFromDatabase.getDepartureTime(), flightFromDatabase.getArrivalTime(), flightFromDatabase.getId());
    }

    public FlightResponse saveFlight(FlightRequest flightRequest) {
        List<Flight> flights = flightsRepository.getFlights();
        adminValidationsService.validateRequest(flights, flightRequest);

        int lastId = flights.stream().mapToInt(fl -> fl.getId()).max().orElse(0);
        Flight flightToSave = new Flight(flightRequest.getFrom(), flightRequest.getTo(),
                flightRequest.getCarrier(), flightRequest.getDepartureTime(), flightRequest.getArrivalTime(), lastId + 1);
        flightsRepository.addAirports(flightToSave);
        return flightsRepository.saveFlight(flightToSave);
    }

    public String deleteFlight(String flightId) {
        return flightsRepository.deleteFlight(flightId);
    }

}
