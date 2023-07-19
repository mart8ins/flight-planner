package io.codelex.flightplanner.flights.customer;

import io.codelex.flightplanner.flights.customer.service.CustomerFlightsServiceInMemory;
import io.codelex.flightplanner.flights.repository.inMemory.FlightsRepositoryInMemory;
import io.codelex.flightplanner.flights.admin.domain.Airport;
import io.codelex.flightplanner.flights.admin.domain.Flight;
import io.codelex.flightplanner.flights.admin.response.FlightResponse;
import io.codelex.flightplanner.flights.customer.request.SearchFlightRequest;
import io.codelex.flightplanner.flights.customer.response.SearchedFlightsResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class CustomerFlightsServiceInMemoryTest {

    @Mock
    FlightsRepositoryInMemory flightsRepositoryInMemory;

    @InjectMocks
    CustomerFlightsServiceInMemory customerFlightsServiceInMemory;

    @Test
    @DisplayName("Searched flights is found and returned.")
    void searchFlights() {
        // EXPECTED RETURNED VALUE
        SearchedFlightsResponse searchedFlightsResponse = new SearchedFlightsResponse(1,1,new ArrayList<Flight>());
        LocalDateTime departure = LocalDateTime.of(2023, 06, 02, 12, 00);
        LocalDateTime arrival = LocalDateTime.of(2023, 06, 04, 12, 00);
        Flight expectedFlight = new Flight(1,
                "AirBaltic", departure, arrival, new Airport("RIX", "Latvia", "Riga"), new Airport("EENA","Estonia", "Narva" ));
        searchedFlightsResponse.setItems(Arrays.asList(expectedFlight));

        // SEARCH FLIGHT REQUEST
        SearchFlightRequest searchFlightRequest = new SearchFlightRequest("RIX", "EENA", "2023-06-02");

        Mockito.when(flightsRepositoryInMemory.searchFlights(searchFlightRequest)).thenReturn(searchedFlightsResponse);

        SearchedFlightsResponse actualResult = customerFlightsServiceInMemory.searchFlights(searchFlightRequest);
        Flight actualFlight = (Flight)actualResult.getItems().get(0);

        // CHECK RESPONSE OBJECT
        Assertions.assertEquals(1, actualResult.getPage());
        Assertions.assertEquals(1, actualResult.getTotalItems());
        Assertions.assertEquals(1, actualResult.getItems().size());
        Assertions.assertEquals(expectedFlight, actualResult.getItems().get(0));
        // CHECK ACTUAL FLIGHT IN RESPONSE ITEMS OBJECT
        Assertions.assertEquals("Latvia", actualFlight.getFrom().getCountry());
        Assertions.assertEquals("Estonia", actualFlight.getTo().getCountry());
        Assertions.assertEquals("2023-06-02", actualFlight.getDepartureTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

    @Test
    @DisplayName("Searched by half of country name returns one result")
    void searchAirports1(){
        String searchQuery = "Lat";

        Airport airport1 = new Airport("RIX","Latvia", "Riga");
        List<Airport> expextedAirportListInMemory = List.of(airport1);

        Mockito.when(flightsRepositoryInMemory.searchAirport(searchQuery)).thenReturn(expextedAirportListInMemory);

        List<Airport> actualAirportListInMemory = customerFlightsServiceInMemory.searchAirport(searchQuery);

        Assertions.assertEquals(1, actualAirportListInMemory.size());
        Assertions.assertEquals("Latvia", actualAirportListInMemory.get(0).getCountry());
        Assertions.assertEquals("Riga", actualAirportListInMemory.get(0).getCity());
        Assertions.assertEquals("RIX", actualAirportListInMemory.get(0).getAirport());
    }

    @Test
    @DisplayName("Searched by half of countries name returns two results")
    void searchAirports2(){
        String searchQuery = "Lat";

        Airport airport1 = new Airport("RIX", "Latvia", "Riga");
        Airport airport2 = new Airport("BIX","Latunia", "Oaua");
        List<Airport> expextedAirportListInMemory = List.of(airport1, airport2);

        Mockito.when(flightsRepositoryInMemory.searchAirport(searchQuery)).thenReturn(expextedAirportListInMemory);

        List<Airport> actualAirportListInMemory = customerFlightsServiceInMemory.searchAirport(searchQuery);

        Assertions.assertEquals(2, actualAirportListInMemory.size());
        Assertions.assertEquals("Latvia", actualAirportListInMemory.get(0).getCountry());
        Assertions.assertEquals("Latunia", actualAirportListInMemory.get(1).getCountry());
        Assertions.assertEquals("Riga", actualAirportListInMemory.get(0).getCity());
        Assertions.assertEquals("Oaua", actualAirportListInMemory.get(1).getCity());
        Assertions.assertEquals("RIX", actualAirportListInMemory.get(0).getAirport());
        Assertions.assertEquals("BIX", actualAirportListInMemory.get(1).getAirport());
    }

    @Test
    @DisplayName("Searched by part of airport name, returns more results")
    void searchAirports3(){
        String searchQuery = "IX";

        Airport airport1 = new Airport("RIX", "Latvia", "Riga" );
        Airport airport2 = new Airport("BIX", "Latunia", "Oaua" );
        List<Airport> expextedAirportListInMemory = List.of(airport1, airport2);

        Mockito.when(flightsRepositoryInMemory.searchAirport(searchQuery)).thenReturn(expextedAirportListInMemory);

        List<Airport> actualAirportListInMemory = customerFlightsServiceInMemory.searchAirport(searchQuery);

        Assertions.assertEquals(2, actualAirportListInMemory.size());
        Assertions.assertEquals("Latvia", actualAirportListInMemory.get(0).getCountry());
        Assertions.assertEquals("Latunia", actualAirportListInMemory.get(1).getCountry());
        Assertions.assertEquals("Riga", actualAirportListInMemory.get(0).getCity());
        Assertions.assertEquals("Oaua", actualAirportListInMemory.get(1).getCity());
        Assertions.assertEquals("RIX", actualAirportListInMemory.get(0).getAirport());
        Assertions.assertEquals("BIX", actualAirportListInMemory.get(1).getAirport());
    }

    @Test
    void getFlightById(){
        int flightId = 1;

        // EXPECTED FLIGHT FROM DB WHAT IS RETURNED FROM REPOSITORY
        LocalDateTime departure = LocalDateTime.of(2023, 06, 02, 12, 00);
        LocalDateTime arrival = LocalDateTime.of(2023, 06, 04, 12, 00);
        Flight expectedFlightFromDBInMemory = new Flight(1,
                "AirBaltic", departure, arrival, new Airport("RIX", "Latvia", "Riga" ), new Airport("EENA", "Estonia", "Narva" ));
        Mockito.when(flightsRepositoryInMemory.getFlightById(String.valueOf(flightId))).thenReturn(expectedFlightFromDBInMemory);

        FlightResponse flightResponse = customerFlightsServiceInMemory.getFlightById(String.valueOf(flightId));
        Mockito.verify(flightsRepositoryInMemory).getFlightById(String.valueOf(flightId));

        Assertions.assertEquals("Latvia", flightResponse.getFrom().getCountry());
        Assertions.assertEquals("Estonia", flightResponse.getTo().getCountry());
        Assertions.assertEquals("2023-06-02 12:00", flightResponse.getDepartureTime());
        Assertions.assertEquals(flightId, flightResponse.getId());
    }
}