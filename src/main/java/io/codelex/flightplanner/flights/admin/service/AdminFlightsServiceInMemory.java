package io.codelex.flightplanner.flights.admin.service;

import io.codelex.flightplanner.flights.repository.inMemory.FlightsRepositoryInMemory;
import io.codelex.flightplanner.flights.admin.domain.inMemory.FlightInMemory;
import io.codelex.flightplanner.flights.admin.request.FlightRequest;
import io.codelex.flightplanner.flights.admin.response.FlightResponse;
import io.codelex.flightplanner.flights.utils.HandleDatesFormatter;

public class AdminFlightsServiceInMemory implements AdminFlightService {

    private FlightsRepositoryInMemory flightsRepositoryInMemory;

    public AdminFlightsServiceInMemory(FlightsRepositoryInMemory flightsRepositoryInMemory) {
        this.flightsRepositoryInMemory = flightsRepositoryInMemory;
    }

    public FlightResponse getFlightById(String flightId) {
        FlightInMemory flightInMemoryFromDatabase = flightsRepositoryInMemory.getFlightById(flightId);

        String departureDateTime = HandleDatesFormatter.formatLocalDateTimeToString(flightInMemoryFromDatabase.getDepartureTime());
        String arrivalDateTime = HandleDatesFormatter.formatLocalDateTimeToString(flightInMemoryFromDatabase.getArrivalTime());

        return new FlightResponse(flightInMemoryFromDatabase.getId(), flightInMemoryFromDatabase.getFrom(), flightInMemoryFromDatabase.getTo(), flightInMemoryFromDatabase.getCarrier(),
                departureDateTime, arrivalDateTime);
    }

    public FlightResponse saveFlight(FlightRequest flightRequest) {
        return flightsRepositoryInMemory.saveFlight(flightRequest);
    }

    public String deleteFlight(String flightId) {
        return flightsRepositoryInMemory.deleteFlight(flightId);
    }
}
