package io.codelex.flightplanner.flights.admin.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.codelex.flightplanner.flights.admin.domain.Airport;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public class FlightRequest {
    @NotNull
    @NotEmpty
    private String carrier;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime departureTime;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime arrivalTime;

    @NotNull
    @Valid
    private Airport from;
    @NotNull
    @Valid
    private Airport to;

    public FlightRequest(String carrier,@JsonFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime departureTime,@JsonFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime arrivalTime, Airport from, Airport to) {
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

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
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
