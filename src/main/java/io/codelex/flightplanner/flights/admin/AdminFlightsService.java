package io.codelex.flightplanner.flights.admin;

import io.codelex.flightplanner.flights.FlightsRepository;
import io.codelex.flightplanner.flights.admin.domain.Flight;
import io.codelex.flightplanner.flights.admin.request.FlightRequest;
import io.codelex.flightplanner.flights.admin.response.FlightResponse;
import io.codelex.flightplanner.flights.utils.HandleDatesFormatter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

        String departureDateTime = HandleDatesFormatter.formatLocalDateTimeToString(flightFromDatabase.getDepartureTime());
        String arrivalDateTime = HandleDatesFormatter.formatLocalDateTimeToString(flightFromDatabase.getArrivalTime());

        return new FlightResponse(flightFromDatabase.getFrom(), flightFromDatabase.getTo(), flightFromDatabase.getCarrier(),
                departureDateTime, arrivalDateTime, flightFromDatabase.getId());
    }

    public FlightResponse saveFlight(FlightRequest flightRequest) {
        List<Flight> flights = flightsRepository.getFlights();

        LocalDateTime departureDateTime = HandleDatesFormatter.formatStringToDateTime(flightRequest.getDepartureTime());
        LocalDateTime arrivalDateTime = HandleDatesFormatter.formatStringToDateTime(flightRequest.getArrivalTime());

        adminValidationsService.validateRequest(flights, flightRequest, departureDateTime, arrivalDateTime);

        int lastId = flights.stream().mapToInt(fl -> fl.getId()).max().orElse(0);

        Flight flightToSave = new Flight(flightRequest.getFrom(), flightRequest.getTo(),
                flightRequest.getCarrier(), departureDateTime, arrivalDateTime, lastId + 1);

        flightsRepository.addAirports(flightToSave);

        return flightsRepository.saveFlight(flightToSave);
    }

    public String deleteFlight(String flightId) {
        return flightsRepository.deleteFlight(flightId);
    }

}
