package io.codelex.flightplanner.flights.admin.domain.inDatabasePostgres;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name="flight")
@Table(name="flights")
public class FlightInDatabase {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="flight_id")
    private int id;

    private String carrier;

    private LocalDateTime departureTime;

    private LocalDateTime arrivalTime;

    @OneToOne
    @JoinColumn(name = "airport_from_id", referencedColumnName = "airport_id")
    private AirportInDatabase from;
    @OneToOne
    @JoinColumn(name = "airport_to_id", referencedColumnName = "airport_id")
    private AirportInDatabase to;

    protected FlightInDatabase(){};

    public FlightInDatabase(int id, String carrier, LocalDateTime departureTime, LocalDateTime arrivalTime, AirportInDatabase from, AirportInDatabase to) {
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

    public AirportInDatabase getFrom() {
        return from;
    }

    public void setFrom(AirportInDatabase from) {
        this.from = from;
    }

    public AirportInDatabase getTo() {
        return to;
    }

    public void setTo(AirportInDatabase to) {
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
        FlightInDatabase that = (FlightInDatabase) o;
        return id == that.id && Objects.equals(carrier, that.carrier) && Objects.equals(departureTime, that.departureTime) && Objects.equals(arrivalTime, that.arrivalTime) && Objects.equals(from, that.from) && Objects.equals(to, that.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, carrier, departureTime, arrivalTime, from, to);
    }
}
