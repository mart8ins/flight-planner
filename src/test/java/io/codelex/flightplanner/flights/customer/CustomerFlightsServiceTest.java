package io.codelex.flightplanner.flights.customer;

import io.codelex.flightplanner.flights.FlightsRepository;
import io.codelex.flightplanner.flights.admin.domain.Airport;
import io.codelex.flightplanner.flights.admin.domain.Flight;
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
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomerFlightsServiceTest {

    @Mock
    FlightsRepository flightsRepository;

    @InjectMocks
    CustomerFlightsService customerFlightsService;

    @Test
    @DisplayName("Searched flights is found and returned.")
    void searchFlights() {
        // EXPECTED RETURNED VALUE
        SearchedFlightsResponse searchedFlightsResponse = new SearchedFlightsResponse(1,1,new ArrayList<Flight>());
        LocalDateTime departure = LocalDateTime.of(2023, 06, 01, 12, 00);
        LocalDateTime arrival = LocalDateTime.of(2023, 06, 02, 12, 00);
        Flight expectedFlight = new Flight(new Airport("Latvia", "Riga", "RIX"), new Airport("Estonia", "Narva", "EENA"),
                "AirBaltic", departure, arrival, 1);
        searchedFlightsResponse.setItems(Arrays.asList(expectedFlight));

        // ACTUAL FLIGHT DATA IN RESPONSE ITEMS LIST
        String countryFromExpected = expectedFlight.getFrom().getCountry();
        String countryToExpected = expectedFlight.getTo().getCountry();

        // SEARCH FLIGHT REQUEST
        SearchFlightRequest searchFlightRequest = new SearchFlightRequest("RIX", "EENA", "2023-06-01");

        Mockito.when(flightsRepository.searchFlights(searchFlightRequest)).thenReturn(searchedFlightsResponse);

        SearchedFlightsResponse actualResult = customerFlightsService.searchFlights(searchFlightRequest);
        Flight actualFlight = (Flight)actualResult.getItems().get(0);

        // CHECK RESPONSE OBJECT
        Assertions.assertEquals(searchedFlightsResponse.getPage(), actualResult.getPage());
        Assertions.assertEquals(searchedFlightsResponse.getTotalItems(), actualResult.getTotalItems());
        Assertions.assertEquals(searchedFlightsResponse.getItems().size(), actualResult.getItems().size());
        Assertions.assertEquals(searchedFlightsResponse.getItems().get(0), actualResult.getItems().get(0));
        // CHECK ACTUAL FLIGHT IN RESPONSE ITEMS OBJECT
        Assertions.assertEquals(countryFromExpected, actualFlight.getFrom().getCountry());
        Assertions.assertEquals(countryToExpected, actualFlight.getTo().getCountry());
    }
}