package io.codelex.flightplanner.flights.admin;

import io.codelex.flightplanner.flights.admin.service.AdminFlightsServiceInMemory;
import io.codelex.flightplanner.flights.repository.inMemory.FlightsRepositoryInMemory;
import io.codelex.flightplanner.flights.admin.domain.inMemory.AirportInMemory;
import io.codelex.flightplanner.flights.admin.domain.inMemory.FlightInMemory;
import io.codelex.flightplanner.flights.admin.request.FlightRequest;
import io.codelex.flightplanner.flights.admin.response.FlightResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class AdminFlightsServiceInMemoryTest {

    @Mock
    FlightsRepositoryInMemory flightsRepositoryInMemory;

    @InjectMocks
    AdminFlightsServiceInMemory adminFlightsServiceInMemory;

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
        FlightInMemory expectedFlightFromDBInMemory = new FlightInMemory(1,new AirportInMemory("Latvia", "Riga", "RIX"), new AirportInMemory("Estonia", "Narva", "EENA"),
                "AirBaltic", departure, arrival);
        Mockito.when(flightsRepositoryInMemory.getFlightById(String.valueOf(flightId))).thenReturn(expectedFlightFromDBInMemory);

        FlightResponse flightResponse = adminFlightsServiceInMemory.getFlightById(String.valueOf(flightId));
        Mockito.verify(flightsRepositoryInMemory).getFlightById(String.valueOf(flightId));

        Assertions.assertEquals("Latvia", flightResponse.getFrom().getCountry());
        Assertions.assertEquals("Estonia", flightResponse.getTo().getCountry());
        Assertions.assertEquals("2023-06-02 12:00", flightResponse.getDepartureTime());
        Assertions.assertEquals(flightId, flightResponse.getId());
    }

    @Test
    void saveFlight() {
        FlightRequest expectedFlightRequest = new FlightRequest(new AirportInMemory("Latvia", "Riga", "RIX"), new AirportInMemory("Estonia", "Narva", "EENA"),
                "AirBaltic", "2023-06-01-12-00", "2023-06-02-12-00");
        FlightResponse expectedFlightResponse = new FlightResponse(1,new AirportInMemory("Latvia", "Riga", "RIX"), new AirportInMemory("Estonia", "Narva", "EENA"),
                "AirBaltic", "2023-06-01 12:00", "2023-06-02 12:00");

        Mockito.when(flightsRepositoryInMemory.saveFlight(expectedFlightRequest)).thenReturn(expectedFlightResponse);
        FlightResponse flightResponseActual = adminFlightsServiceInMemory.saveFlight(expectedFlightRequest);

        Mockito.verify(flightsRepositoryInMemory).saveFlight(flightRequestCapture.capture());
        FlightRequest flightRequestCaptured = flightRequestCapture.getValue();

        Assertions.assertEquals(expectedFlightRequest, flightRequestCaptured);
        Assertions.assertEquals(expectedFlightResponse, flightResponseActual);
    }

    @Test
    void deleteFlight() {
        String flightIdToDelete = "1";
        String expectedMessageAfterDelete = "Flight with id: " + flightIdToDelete + " removed from database.";

        Mockito.when(flightsRepositoryInMemory.deleteFlight(flightIdToDelete)).thenReturn(expectedMessageAfterDelete);
        String receivedMessageAfterDeletion = adminFlightsServiceInMemory.deleteFlight(flightIdToDelete);

        Mockito.verify(flightsRepositoryInMemory).deleteFlight(deleteFlightIdCapture.capture());
        String capturedIdToDeleteFlight = deleteFlightIdCapture.getValue();

        Assertions.assertEquals(flightIdToDelete, capturedIdToDeleteFlight);
        Assertions.assertEquals(expectedMessageAfterDelete, receivedMessageAfterDeletion);
    }
}