package io.codelex.flightplanner.flights.admin.domain.inMemory;

import java.time.LocalDateTime;
import java.util.Objects;

public class FlightInMemory {

    private int id;

    private AirportInMemory from;

    private AirportInMemory to;

    private String carrier;

    private LocalDateTime departureTime;

    private LocalDateTime arrivalTime;

    public FlightInMemory(int id, AirportInMemory from, AirportInMemory to, String carrier, LocalDateTime departureTime, LocalDateTime arrivalTime) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.carrier = carrier;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", from=" + from +
                ", to=" + to +
                ", carrier='" + carrier + '\'' +
                ", departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightInMemory flightInMemory = (FlightInMemory) o;
        return id == flightInMemory.id && Objects.equals(from, flightInMemory.from) && Objects.equals(to, flightInMemory.to) && Objects.equals(carrier, flightInMemory.carrier) && Objects.equals(departureTime, flightInMemory.departureTime) && Objects.equals(arrivalTime, flightInMemory.arrivalTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, from, to, carrier, departureTime, arrivalTime);
    }
}
