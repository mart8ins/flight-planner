package io.codelex.flightplanner.flights.admin.request;

import io.codelex.flightplanner.flights.admin.domain.inMemory.AirportInMemory;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public class FlightRequest {
    @NotNull
    @Valid
    private AirportInMemory from;
    @NotNull
    @Valid
    private AirportInMemory to;
    @NotNull
    @NotEmpty
    private String carrier;
    @NotNull
    @NotEmpty
    private String departureTime;
    @NotNull
    @NotEmpty
    private String arrivalTime;

    public FlightRequest(AirportInMemory from, AirportInMemory to, String carrier, String departureTime, String arrivalTime) {
        this.from = from;
        this.to = to;
        this.carrier = carrier;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public AirportInMemory getFrom() {
        return from;
    }

    public void setFrom(AirportInMemory from) {
        this.from = from;
    }

    public AirportInMemory getTo() {
        return to;
    }

    public void setTo(AirportInMemory to) {
        this.to = to;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @Override
    public String toString() {
        return "AddFlightRequest{" +
                "from=" + from +
                ", to=" + to +
                ", carrier='" + carrier + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                '}';
    }
}
