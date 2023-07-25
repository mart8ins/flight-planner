package io.codelex.flightplanner.flights.admin.service;
import io.codelex.flightplanner.flights.admin.request.FlightRequest;
import io.codelex.flightplanner.flights.admin.response.FlightResponse;

public interface AdminService {

    FlightResponse getFlightById(String flightId);

    FlightResponse saveFlight(FlightRequest flightRequest);

    String deleteFlight(String flightId);
}
