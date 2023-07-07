package io.codelex.flightplanner.flights.customer;

import io.codelex.flightplanner.flights.FlightsRepository;
import io.codelex.flightplanner.flights.admin.domain.Airport;
import io.codelex.flightplanner.flights.admin.domain.Flight;
import io.codelex.flightplanner.flights.admin.response.FlightResponse;
import io.codelex.flightplanner.flights.customer.request.SearchFlightRequest;
import io.codelex.flightplanner.flights.customer.response.SearchedFlightsResponse;
import io.codelex.flightplanner.flights.utils.HandleDatesFormatter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerFlightsService {

    private FlightsRepository flightsRepository;

    public CustomerFlightsService(FlightsRepository flightsRepository) {
        this.flightsRepository = flightsRepository;
    }

    public List<Airport> searchAirport(String airportSearchQuery){
        return flightsRepository.searchAirport(airportSearchQuery);
    }

    public FlightResponse getFlightById(String flightId) {
        Flight flightFromDatabase = flightsRepository.getFlightById(flightId);

        String departureDateTime = HandleDatesFormatter.formatLocalDateTimeToString(flightFromDatabase.getDepartureTime());
        String arrivalDateTime = HandleDatesFormatter.formatLocalDateTimeToString(flightFromDatabase.getArrivalTime());
        return new FlightResponse(flightFromDatabase.getFrom(), flightFromDatabase.getTo(), flightFromDatabase.getCarrier(),
                departureDateTime, arrivalDateTime, flightFromDatabase.getId());
    }

    public SearchedFlightsResponse<Flight> searchFlights(SearchFlightRequest flight) {
        return flightsRepository.searchFlights(flight);
    }
}
