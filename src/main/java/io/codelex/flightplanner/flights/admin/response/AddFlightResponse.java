package io.codelex.flightplanner.flights.admin.response;

import io.codelex.flightplanner.flights.admin.request.AddFlightRequest;
import io.codelex.flightplanner.flights.admin.request.AirportRequest;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddFlightResponse that = (AddFlightResponse) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
