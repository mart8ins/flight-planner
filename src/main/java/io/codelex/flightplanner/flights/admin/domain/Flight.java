package io.codelex.flightplanner.flights.admin.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name="flight")
@Table(name="flights")
public class Flight {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="flight_id")
    private int id;

    private String carrier;

    private LocalDateTime departureTime;

    private LocalDateTime arrivalTime;

    @OneToOne
    @JoinColumn(name = "airport_from_id", referencedColumnName = "airport_id")
    private Airport from;
    @OneToOne
    @JoinColumn(name = "airport_to_id", referencedColumnName = "airport_id")
    private Airport to;

    protected Flight(){};

    public Flight(int id, String carrier, LocalDateTime departureTime, LocalDateTime arrivalTime, Airport from, Airport to) {
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
        return "FlightInDatabase{" +
                "id=" + id +
                ", carrier='" + carrier + '\'' +
                ", departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return id == flight.id && Objects.equals(carrier, flight.carrier) && Objects.equals(departureTime, flight.departureTime) && Objects.equals(arrivalTime, flight.arrivalTime) && Objects.equals(from, flight.from) && Objects.equals(to, flight.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, carrier, departureTime, arrivalTime, from, to);
    }
}
