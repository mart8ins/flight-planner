package io.codelex.flightplanner.flights.customer;

import io.codelex.flightplanner.flights.customer.service.CustomerFlightsServiceInMemory;
import io.codelex.flightplanner.flights.repository.inMemory.FlightsRepositoryInMemory;
import io.codelex.flightplanner.flights.admin.domain.inMemory.AirportInMemory;
import io.codelex.flightplanner.flights.admin.domain.inMemory.FlightInMemory;
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
        SearchedFlightsResponse searchedFlightsResponse = new SearchedFlightsResponse(1,1,new ArrayList<FlightInMemory>());
        LocalDateTime departure = LocalDateTime.of(2023, 06, 02, 12, 00);
        LocalDateTime arrival = LocalDateTime.of(2023, 06, 04, 12, 00);
        FlightInMemory expectedFlightInMemory = new FlightInMemory(1,new AirportInMemory("Latvia", "Riga", "RIX"), new AirportInMemory("Estonia", "Narva", "EENA"),
                "AirBaltic", departure, arrival);
        searchedFlightsResponse.setItems(Arrays.asList(expectedFlightInMemory));

        // SEARCH FLIGHT REQUEST
        SearchFlightRequest searchFlightRequest = new SearchFlightRequest("RIX", "EENA", "2023-06-02");

        Mockito.when(flightsRepositoryInMemory.searchFlights(searchFlightRequest)).thenReturn(searchedFlightsResponse);

        SearchedFlightsResponse actualResult = customerFlightsServiceInMemory.searchFlights(searchFlightRequest);
        FlightInMemory actualFlightInMemory = (FlightInMemory)actualResult.getItems().get(0);

        // CHECK RESPONSE OBJECT
        Assertions.assertEquals(1, actualResult.getPage());
        Assertions.assertEquals(1, actualResult.getTotalItems());
        Assertions.assertEquals(1, actualResult.getItems().size());
        Assertions.assertEquals(expectedFlightInMemory, actualResult.getItems().get(0));
        // CHECK ACTUAL FLIGHT IN RESPONSE ITEMS OBJECT
        Assertions.assertEquals("Latvia", actualFlightInMemory.getFrom().getCountry());
        Assertions.assertEquals("Estonia", actualFlightInMemory.getTo().getCountry());
        Assertions.assertEquals("2023-06-02", actualFlightInMemory.getDepartureTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

    @Test
    @DisplayName("Searched by half of country name returns one result")
    void searchAirports1(){
        String searchQuery = "Lat";

        AirportInMemory airportInMemory1 = new AirportInMemory("Latvia", "Riga", "RIX");
        List<AirportInMemory> expextedAirportListInMemory = List.of(airportInMemory1);

        Mockito.when(flightsRepositoryInMemory.searchAirport(searchQuery)).thenReturn(expextedAirportListInMemory);

        List<AirportInMemory> actualAirportListInMemory = customerFlightsServiceInMemory.searchAirport(searchQuery);

        Assertions.assertEquals(1, actualAirportListInMemory.size());
        Assertions.assertEquals("Latvia", actualAirportListInMemory.get(0).getCountry());
        Assertions.assertEquals("Riga", actualAirportListInMemory.get(0).getCity());
        Assertions.assertEquals("RIX", actualAirportListInMemory.get(0).getAirport());
    }

    @Test
    @DisplayName("Searched by half of countries name returns two results")
    void searchAirports2(){
        String searchQuery = "Lat";

        AirportInMemory airportInMemory1 = new AirportInMemory("Latvia", "Riga", "RIX");
        AirportInMemory airportInMemory2 = new AirportInMemory("Latunia", "Oaua", "BIX");
        List<AirportInMemory> expextedAirportListInMemory = List.of(airportInMemory1, airportInMemory2);

        Mockito.when(flightsRepositoryInMemory.searchAirport(searchQuery)).thenReturn(expextedAirportListInMemory);

        List<AirportInMemory> actualAirportListInMemory = customerFlightsServiceInMemory.searchAirport(searchQuery);

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

        AirportInMemory airportInMemory1 = new AirportInMemory("Latvia", "Riga", "RIX");
        AirportInMemory airportInMemory2 = new AirportInMemory("Latunia", "Oaua", "BIX");
        List<AirportInMemory> expextedAirportListInMemory = List.of(airportInMemory1, airportInMemory2);

        Mockito.when(flightsRepositoryInMemory.searchAirport(searchQuery)).thenReturn(expextedAirportListInMemory);

        List<AirportInMemory> actualAirportListInMemory = customerFlightsServiceInMemory.searchAirport(searchQuery);

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
        FlightInMemory expectedFlightFromDBInMemory = new FlightInMemory(1,new AirportInMemory("Latvia", "Riga", "RIX"), new AirportInMemory("Estonia", "Narva", "EENA"),
                "AirBaltic", departure, arrival);
        Mockito.when(flightsRepositoryInMemory.getFlightById(String.valueOf(flightId))).thenReturn(expectedFlightFromDBInMemory);

        FlightResponse flightResponse = customerFlightsServiceInMemory.getFlightById(String.valueOf(flightId));
        Mockito.verify(flightsRepositoryInMemory).getFlightById(String.valueOf(flightId));

        Assertions.assertEquals("Latvia", flightResponse.getFrom().getCountry());
        Assertions.assertEquals("Estonia", flightResponse.getTo().getCountry());
        Assertions.assertEquals("2023-06-02 12:00", flightResponse.getDepartureTime());
        Assertions.assertEquals(flightId, flightResponse.getId());
    }
}