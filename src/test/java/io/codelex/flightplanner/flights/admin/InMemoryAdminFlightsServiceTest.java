package io.codelex.flightplanner.flights.admin;

import io.codelex.flightplanner.flights.admin.service.InMemoryAdminFlightsService;
import io.codelex.flightplanner.flights.repository.InMemoryFlightsRepository;
import io.codelex.flightplanner.flights.admin.domain.Airport;
import io.codelex.flightplanner.flights.admin.domain.Flight;
import io.codelex.flightplanner.flights.admin.request.FlightRequest;
import io.codelex.flightplanner.flights.admin.response.FlightResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class InMemoryAdminFlightsServiceTest {

    @Mock
    InMemoryFlightsRepository inMemoryFlightsRepository;

    @InjectMocks
    InMemoryAdminFlightsService inMemoryAdminFlightsService;

    @Captor
    ArgumentCaptor<String> deleteFlightIdCapture;

    @Captor
    ArgumentCaptor<FlightRequest> flightRequestCapture;

    @Test
    void getFlightById() {
        int flightId = 1;

        // EXPECTED FLIGHT FROM DB WHAT IS RETURNED FROM REPOSITORY
        LocalDateTime departure = LocalDateTime.of(2023, 06, 02, 12, 00);
        LocalDateTime arrival = LocalDateTime.of(2023, 06, 04, 12, 00);
        Flight expectedFlightFromDB = new Flight(new Airport("Latvia", "Riga", "RIX"), new Airport("Estonia", "Narva", "EENA"),
                "AirBaltic", departure, arrival, 1);
        Mockito.when(inMemoryFlightsRepository.getFlightById(String.valueOf(flightId))).thenReturn(expectedFlightFromDB);

        FlightResponse flightResponse = inMemoryAdminFlightsService.getFlightById(String.valueOf(flightId));
        Mockito.verify(inMemoryFlightsRepository).getFlightById(String.valueOf(flightId));

        Assertions.assertEquals("Latvia", flightResponse.getFrom().getCountry());
        Assertions.assertEquals("Estonia", flightResponse.getTo().getCountry());
        Assertions.assertEquals("2023-06-02 12:00", flightResponse.getDepartureTime());
        Assertions.assertEquals(flightId, flightResponse.getId());
    }

    @Test
    void saveFlight() {
        FlightRequest expectedFlightRequest = new FlightRequest(new Airport("Latvia", "Riga", "RIX"), new Airport("Estonia", "Narva", "EENA"),
                "AirBaltic", "2023-06-01-12-00", "2023-06-02-12-00");
        FlightResponse expectedFlightResponse = new FlightResponse(new Airport("Latvia", "Riga", "RIX"), new Airport("Estonia", "Narva", "EENA"),
                "AirBaltic", "2023-06-01 12:00", "2023-06-02 12:00", 1);

        Mockito.when(inMemoryFlightsRepository.saveFlight(expectedFlightRequest)).thenReturn(expectedFlightResponse);
        FlightResponse flightResponseActual = inMemoryAdminFlightsService.saveFlight(expectedFlightRequest);

        Mockito.verify(inMemoryFlightsRepository).saveFlight(flightRequestCapture.capture());
        FlightRequest flightRequestCaptured = flightRequestCapture.getValue();

        Assertions.assertEquals(expectedFlightRequest, flightRequestCaptured);
        Assertions.assertEquals(expectedFlightResponse, flightResponseActual);
    }

    @Test
    void deleteFlight() {
        String flightIdToDelete = "1";
        String expectedMessageAfterDelete = "Flight with id: " + flightIdToDelete + " removed from database.";

        Mockito.when(inMemoryFlightsRepository.deleteFlight(flightIdToDelete)).thenReturn(expectedMessageAfterDelete);
        String receivedMessageAfterDeletion = inMemoryAdminFlightsService.deleteFlight(flightIdToDelete);

        Mockito.verify(inMemoryFlightsRepository).deleteFlight(deleteFlightIdCapture.capture());
        String capturedIdToDeleteFlight = deleteFlightIdCapture.getValue();

        Assertions.assertEquals(flightIdToDelete, capturedIdToDeleteFlight);
        Assertions.assertEquals(expectedMessageAfterDelete, receivedMessageAfterDeletion);
    }
}