package io.codelex.flightplanner.flights.admin.service;

import io.codelex.flightplanner.flights.repository.InMemoryFlightsRepository;
import io.codelex.flightplanner.flights.admin.domain.Flight;
import io.codelex.flightplanner.flights.admin.request.FlightRequest;
import io.codelex.flightplanner.flights.admin.response.FlightResponse;
import io.codelex.flightplanner.flights.utils.HandleDatesFormatter;
import org.springframework.stereotype.Service;

@Service
public class InMemoryAdminFlightsService implements AdminFlightService {

    private InMemoryFlightsRepository inMemoryFlightsRepository;

    public InMemoryAdminFlightsService(InMemoryFlightsRepository inMemoryFlightsRepository) {
        this.inMemoryFlightsRepository = inMemoryFlightsRepository;
    }

    public FlightResponse getFlightById(String flightId) {
        Flight flightFromDatabase = inMemoryFlightsRepository.getFlightById(flightId);

        String departureDateTime = HandleDatesFormatter.formatLocalDateTimeToString(flightFromDatabase.getDepartureTime());
        String arrivalDateTime = HandleDatesFormatter.formatLocalDateTimeToString(flightFromDatabase.getArrivalTime());

        return new FlightResponse(flightFromDatabase.getFrom(), flightFromDatabase.getTo(), flightFromDatabase.getCarrier(),
                departureDateTime, arrivalDateTime, flightFromDatabase.getId());
    }

    public FlightResponse saveFlight(FlightRequest flightRequest) {
        return inMemoryFlightsRepository.saveFlight(flightRequest);
    }

    public String deleteFlight(String flightId) {
        return inMemoryFlightsRepository.deleteFlight(flightId);
    }
}
