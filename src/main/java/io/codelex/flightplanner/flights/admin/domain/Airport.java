package io.codelex.flightplanner.flights.admin.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Entity(name="airport")
@Table(name="airports")
public class Airport {

    @Id
    @Column(name = "airport_id")
    @NotNull
    @NotEmpty
    private String airport;
    @NotNull
    @NotEmpty
    private String country;
    @NotNull
    @NotEmpty
    private String city;

    protected Airport(){};

    public Airport(String airport, String country, String city) {
        this.airport = airport;
        this.country = country;
        this.city = city;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "AirportInDatabase{" +
                "airport='" + airport + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airport airport1 = (Airport) o;
        return Objects.equals(airport, airport1.airport) && Objects.equals(country, airport1.country) && Objects.equals(city, airport1.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(airport, country, city);
    }
}