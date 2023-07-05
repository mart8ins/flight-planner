package io.codelex.flightplanner.flights.admin.domain;

import io.codelex.flightplanner.flights.admin.request.AddFlightRequest;
import io.codelex.flightplanner.flights.admin.request.AirportRequest;

public class Flight extends AddFlightRequest {

    private int id;

    public Flight(AirportRequest from, AirportRequest to, String carrier, String departureTime, String arrivalTime, int id) {
        super(from, to, carrier, departureTime, arrivalTime);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                "} " + super.toString();
    }
}
