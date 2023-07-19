package io.codelex.flightplanner.flights.admin.request;

import io.codelex.flightplanner.flights.admin.domain.Airport;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public class FlightRequest {
    @NotNull
    @NotEmpty
    private String carrier;
    @NotNull
    @NotEmpty
    private String departureTime;
    @NotNull
    @NotEmpty
    private String arrivalTime;

    @NotNull
    @Valid
    private Airport from;
    @NotNull
    @Valid
    private Airport to;

    public FlightRequest(String carrier, String departureTime, String arrivalTime, Airport from, Airport to) {
        this.carrier = carrier;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.from = from;
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

    public Airport getFrom() {
        return from;
    }

    public void setFrom(Airport from) {
        this.from = from;
    }

    public Airport getTo() {
        return to;
    }

    public void setTo(Airport to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "FlightRequest{" +
                "carrier='" + carrier + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                ", from=" + from +
                ", to=" + to +
                '}';
    }
}
