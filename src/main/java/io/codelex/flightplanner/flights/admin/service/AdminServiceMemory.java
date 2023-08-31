package io.codelex.flightplanner.flights.admin.service;

import io.codelex.flightplanner.flights.repository.memory.FlightsRepositoryMemory;
import io.codelex.flightplanner.flights.admin.domain.Flight;
import io.codelex.flightplanner.flights.admin.request.FlightRequest;
import io.codelex.flightplanner.flights.admin.response.FlightResponse;

public class AdminServiceMemory implements AdminService {

    private FlightsRepositoryMemory flightsRepositoryMemory;

    public AdminServiceMemory(FlightsRepositoryMemory flightsRepositoryMemory) {
        this.flightsRepositoryMemory = flightsRepositoryMemory;
    }

    public FlightResponse getFlightById(String flightId) {
        Flight flightFromDatabase = flightsRepositoryMemory.getFlightById(flightId);

        return new FlightResponse(flightFromDatabase.getId(), flightFromDatabase.getCarrier(),
                flightFromDatabase.getDepartureTime(), flightFromDatabase.getArrivalTime(), flightFromDatabase.getFrom(), flightFromDatabase.getTo());
    }

    public FlightResponse saveFlight(FlightRequest flightRequest) {
        return flightsRepositoryMemory.saveFlight(flightRequest);
    }

    public String deleteFlight(String flightId) {
        return flightsRepositoryMemory.deleteFlight(flightId);
    }
}
