package io.codelex.flightplanner.flights.admin.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.codelex.flightplanner.flights.admin.domain.Airport;

import java.time.LocalDateTime;
import java.util.Objects;

public class FlightResponse {

    private int id;

    private String carrier;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime departureTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime arrivalTime;

    private Airport from;

    private Airport to;

    public FlightResponse(int id, String carrier,@JsonFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime departureTime,@JsonFormat(pattern = "yyyy-MM-dd HH:mm") LocalDateTime arrivalTime, Airport from, Airport to) {
        this.id = id;
        this.carrier = carrier;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.from = from;
        this.to = to;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return "FlightResponse{" +
                "id=" + id +
                ", carrier='" + carrier + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                ", from=" + from +
                ", to=" + to +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightResponse that = (FlightResponse) o;
        return id == that.id && Objects.equals(carrier, that.carrier) && Objects.equals(departureTime, that.departureTime) && Objects.equals(arrivalTime, that.arrivalTime) && Objects.equals(from, that.from) && Objects.equals(to, that.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, carrier, departureTime, arrivalTime, from, to);
    }
}
