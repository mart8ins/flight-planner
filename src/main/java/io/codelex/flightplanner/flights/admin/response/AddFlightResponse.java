package io.codelex.flightplanner.flights.admin.response;

import io.codelex.flightplanner.flights.admin.request.AddFlightRequest;
import io.codelex.flightplanner.flights.admin.request.AirportRequest;

public class AddFlightResponse extends AddFlightRequest {

    private int id;

    public AddFlightResponse(AirportRequest from, AirportRequest to, String carrier, String departureTime, String arrivalTime, int id) {
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
        return "AddFlightResponse{" +
                "id='" + id + '\'' +
                "} " + super.toString();
    }
}
