package io.codelex.flightplanner.flights.admin.response;

import io.codelex.flightplanner.flights.admin.domain.inMemory.AirportInMemory;

import java.util.Objects;

public class FlightResponse {

    private int id;

    private AirportInMemory from;

    private AirportInMemory to;

    private String carrier;

    private String departureTime;

    private String arrivalTime;

    public FlightResponse(int id, AirportInMemory from, AirportInMemory to, String carrier, String departureTime, String arrivalTime) {
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
        return "FlightResponse{" +
                "id=" + id +
                ", from=" + from +
                ", to=" + to +
                ", carrier='" + carrier + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightResponse that = (FlightResponse) o;
        return id == that.id && Objects.equals(from, that.from) && Objects.equals(to, that.to) && Objects.equals(carrier, that.carrier) && Objects.equals(departureTime, that.departureTime) && Objects.equals(arrivalTime, that.arrivalTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, from, to, carrier, departureTime, arrivalTime);
    }
}
