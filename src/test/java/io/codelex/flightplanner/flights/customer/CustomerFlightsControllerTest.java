package io.codelex.flightplanner.flights.customer;

import io.codelex.flightplanner.flights.admin.domain.Airport;
import io.codelex.flightplanner.flights.admin.domain.Flight;
import io.codelex.flightplanner.flights.admin.response.FlightResponse;
import io.codelex.flightplanner.flights.customer.request.SearchFlightRequest;
import io.codelex.flightplanner.flights.customer.response.SearchedFlightsResponse;
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
    CustomerFlightsService customerFlightsService;

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

        Airport airport1 = new Airport("Latvia", "Riga", "RIX");
        List<Airport> expextedAirportList = List.of(airport1);

        Mockito.when(customerFlightsService.searchAirport(searchQuery)).thenReturn(expextedAirportList);

        List<Airport> actualAirportList = customerFlightsController.searchAirport(searchQuery);
        Mockito.verify(customerFlightsService).searchAirport(airportSearchQueryCapture.capture());
        String capturedSearchQuery = airportSearchQueryCapture.getValue();

        Assertions.assertEquals(searchQuery, capturedSearchQuery);
        Assertions.assertEquals(1, actualAirportList.size());
        Assertions.assertEquals("Latvia", actualAirportList.get(0).getCountry());
        Assertions.assertEquals("Riga", actualAirportList.get(0).getCity());
        Assertions.assertEquals("RIX", actualAirportList.get(0).getAirport());
    }

    @Test
    void getFlightById() {
        int flightId = 1;

        FlightResponse expectedFlightResponse = new FlightResponse(new Airport("Latvia", "Riga", "RIX"), new Airport("Estonia", "Narva", "EENA"),
                "AirBaltic", "2023-06-02-12-00", "2023-06-04-12-00", 1);

        Mockito.when(customerFlightsService.getFlightById(String.valueOf(flightId))).thenReturn(expectedFlightResponse);

        FlightResponse actualFlightResponse = customerFlightsController.getFlightById(String.valueOf(flightId));

        Mockito.verify(customerFlightsService).getFlightById(flightIdQueryCapture.capture());
        String capturedFlightIdQuery = flightIdQueryCapture.getValue();

        Assertions.assertEquals(String.valueOf(flightId), capturedFlightIdQuery);
        Assertions.assertEquals("Latvia", actualFlightResponse.getFrom().getCountry());
        Assertions.assertEquals("Estonia", actualFlightResponse.getTo().getCountry());
        Assertions.assertEquals("2023-06-02-12-00", actualFlightResponse.getDepartureTime());
        Assertions.assertEquals(flightId, actualFlightResponse.getId());
    }

    @Test
    void searchFlights() {
        SearchedFlightsResponse searchedFlightsResponse = new SearchedFlightsResponse(1,1,new ArrayList<Flight>());
        LocalDateTime departure = LocalDateTime.of(2023, 06, 02, 12, 00);
        LocalDateTime arrival = LocalDateTime.of(2023, 06, 04, 12, 00);
        Flight expectedFlight = new Flight(new Airport("Latvia", "Riga", "RIX"), new Airport("Estonia", "Narva", "EENA"),
                "AirBaltic", departure, arrival, 1);
        searchedFlightsResponse.setItems(Arrays.asList(expectedFlight));

        SearchFlightRequest searchFlightRequest = new SearchFlightRequest("RIX", "EENA", "2023-06-02");

        Mockito.when(customerFlightsService.searchFlights(searchFlightRequest)).thenReturn(searchedFlightsResponse);

        SearchedFlightsResponse actualResult = customerFlightsController.searchFlights(searchFlightRequest);
        Flight actualFlight = (Flight)actualResult.getItems().get(0);

        Mockito.verify(customerFlightsService).searchFlights(searchFlightCapture.capture());
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