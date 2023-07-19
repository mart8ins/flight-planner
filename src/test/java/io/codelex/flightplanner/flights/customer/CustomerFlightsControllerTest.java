package io.codelex.flightplanner.flights.customer;

import io.codelex.flightplanner.flights.admin.domain.Airport;
import io.codelex.flightplanner.flights.admin.domain.Flight;
import io.codelex.flightplanner.flights.admin.response.FlightResponse;
import io.codelex.flightplanner.flights.customer.request.SearchFlightRequest;
import io.codelex.flightplanner.flights.customer.response.SearchedFlightsResponse;
import io.codelex.flightplanner.flights.customer.service.CustomerFlightsServiceInMemory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class CustomerFlightsControllerTest {

    @Mock
    CustomerFlightsServiceInMemory customerFlightsServiceInMemory;

    @InjectMocks
    CustomerFlightsController customerFlightsController;

    @Captor
    ArgumentCaptor<String> airportSearchQueryCapture;

    @Captor
    ArgumentCaptor<String> flightIdQueryCapture;

    @Captor
    ArgumentCaptor<SearchFlightRequest> searchFlightCapture;

    @Test
    void searchAirport() {
        String searchQuery = "Lat";

        Airport airport1 = new Airport("RIX", "Latvia", "Riga");
        List<Airport> expextedAirportListInMemory = List.of(airport1);

        Mockito.when(customerFlightsServiceInMemory.searchAirport(searchQuery)).thenReturn(expextedAirportListInMemory);

        List<Airport> actualAirportListInMemory = customerFlightsController.searchAirport(searchQuery);
        Mockito.verify(customerFlightsServiceInMemory).searchAirport(airportSearchQueryCapture.capture());
        String capturedSearchQuery = airportSearchQueryCapture.getValue();

        Assertions.assertEquals(searchQuery, capturedSearchQuery);
        Assertions.assertEquals(1, actualAirportListInMemory.size());
        Assertions.assertEquals("Latvia", actualAirportListInMemory.get(0).getCountry());
        Assertions.assertEquals("Riga", actualAirportListInMemory.get(0).getCity());
        Assertions.assertEquals("RIX", actualAirportListInMemory.get(0).getAirport());
    }

    @Test
    void getFlightById() {
        int flightId = 1;

        FlightResponse expectedFlightResponse = new FlightResponse(1,
                "AirBaltic", "2023-06-02 12:00", "2023-06-04 12:00", new Airport("RIX", "Latvia", "Riga"), new Airport("EENA", "Estonia", "Narva" ));

        Mockito.when(customerFlightsServiceInMemory.getFlightById(String.valueOf(flightId))).thenReturn(expectedFlightResponse);

        FlightResponse actualFlightResponse = customerFlightsController.getFlightById(String.valueOf(flightId));

        Mockito.verify(customerFlightsServiceInMemory).getFlightById(flightIdQueryCapture.capture());
        String capturedFlightIdQuery = flightIdQueryCapture.getValue();

        Assertions.assertEquals(String.valueOf(flightId), capturedFlightIdQuery);
        Assertions.assertEquals("Latvia", actualFlightResponse.getFrom().getCountry());
        Assertions.assertEquals("Estonia", actualFlightResponse.getTo().getCountry());
        Assertions.assertEquals("2023-06-02 12:00", actualFlightResponse.getDepartureTime());
        Assertions.assertEquals(flightId, actualFlightResponse.getId());
    }

    @Test
    void searchFlights() {
        SearchedFlightsResponse searchedFlightsResponse = new SearchedFlightsResponse(1,1,new ArrayList<Flight>());
        LocalDateTime departure = LocalDateTime.of(2023, 06, 02, 12, 00);
        LocalDateTime arrival = LocalDateTime.of(2023, 06, 04, 12, 00);
        Flight expectedFlight = new Flight(1,
                "AirBaltic", departure, arrival, new Airport("RIX", "Latvia", "Riga" ), new Airport("EENA", "Estonia", "Narva"));
        searchedFlightsResponse.setItems(Arrays.asList(expectedFlight));

        SearchFlightRequest searchFlightRequest = new SearchFlightRequest("RIX", "EENA", "2023-06-02");

        Mockito.when(customerFlightsServiceInMemory.searchFlights(searchFlightRequest)).thenReturn(searchedFlightsResponse);

        SearchedFlightsResponse actualResult = customerFlightsController.searchFlights(searchFlightRequest);
        Flight actualFlight = (Flight)actualResult.getItems().get(0);

        Mockito.verify(customerFlightsServiceInMemory).searchFlights(searchFlightCapture.capture());
        SearchFlightRequest capturedSearchFlightRequest = searchFlightCapture.getValue();

        Assertions.assertEquals(searchFlightRequest, capturedSearchFlightRequest);

        Assertions.assertEquals(1, actualResult.getPage());
        Assertions.assertEquals(1, actualResult.getTotalItems());
        Assertions.assertEquals(1, actualResult.getItems().size());
        Assertions.assertEquals(expectedFlight, actualResult.getItems().get(0));

        Assertions.assertEquals("Latvia", actualFlight.getFrom().getCountry());
        Assertions.assertEquals("Estonia", actualFlight.getTo().getCountry());
        Assertions.assertEquals("2023-06-02", actualFlight.getDepartureTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
}