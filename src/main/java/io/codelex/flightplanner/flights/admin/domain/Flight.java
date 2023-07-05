package io.codelex.flightplanner.flights.admin.domain;

import io.codelex.flightplanner.flights.admin.request.AddFlightRequest;
import io.codelex.flightplanner.flights.admin.request.AirportRequest;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return id == flight.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
