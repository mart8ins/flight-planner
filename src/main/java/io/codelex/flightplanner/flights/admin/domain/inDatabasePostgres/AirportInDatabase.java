package io.codelex.flightplanner.flights.admin.domain.inDatabasePostgres;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity(name="airport")
@Table(name="airports")
public class AirportInDatabase {

    @Id
    @Column(name = "airport_id")
    private String airport;

    private String country;

    private String city;

    protected AirportInDatabase(){};

    public AirportInDatabase(String airport, String country, String city) {
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
        AirportInDatabase that = (AirportInDatabase) o;
        return Objects.equals(airport, that.airport) && Objects.equals(country, that.country) && Objects.equals(city, that.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(airport, country, city);
    }
}
