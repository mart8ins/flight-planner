package io.codelex.flightplanner.flights.admin.service;

import io.codelex.flightplanner.flights.repository.inMemory.FlightsRepositoryInMemory;
import io.codelex.flightplanner.flights.admin.domain.Flight;
import io.codelex.flightplanner.flights.admin.request.FlightRequest;
import io.codelex.flightplanner.flights.admin.response.FlightResponse;
import io.codelex.flightplanner.flights.utils.HandleDatesFormatter;

public class AdminFlightsServiceInMemory implements AdminFlightService {

    private FlightsRepositoryInMemory flightsRepositoryInMemory;

    public AdminFlightsServiceInMemory(FlightsRepositoryInMemory flightsRepositoryInMemory) {
        this.flightsRepositoryInMemory = flightsRepositoryInMemory;
    }

    public FlightResponse getFlightById(String flightId) {
        Flight flightFromDatabase = flightsRepositoryInMemory.getFlightById(flightId);

        String departureDateTime = HandleDatesFormatter.formatLocalDateTimeToString(flightFromDatabase.getDepartureTime());
        String arrivalDateTime = HandleDatesFormatter.formatLocalDateTimeToString(flightFromDatabase.getArrivalTime());

        return new FlightResponse(flightFromDatabase.getId(), flightFromDatabase.getCarrier(),
                departureDateTime, arrivalDateTime, flightFromDatabase.getFrom(), flightFromDatabase.getTo());
    }

    public FlightResponse saveFlight(FlightRequest flightRequest) {
        return flightsRepositoryInMemory.saveFlight(flightRequest);
    }

    public String deleteFlight(String flightId) {
        return flightsRepositoryInMemory.deleteFlight(flightId);
    }
}
