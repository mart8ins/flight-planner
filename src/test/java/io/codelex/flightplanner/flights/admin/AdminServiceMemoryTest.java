package io.codelex.flightplanner.flights.admin;

import io.codelex.flightplanner.flights.admin.service.AdminServiceMemory;
import io.codelex.flightplanner.flights.repository.memory.FlightsRepositoryMemory;
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
class AdminServiceMemoryTest {

    @Mock
    FlightsRepositoryMemory flightsRepositoryMemory;

    @InjectMocks
    AdminServiceMemory adminServiceMemory;

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
        Flight expectedFlightFromDBInMemory = new Flight(1,
                "AirBaltic", departure, arrival, new Airport("RIX", "Latvia", "Riga"), new Airport("EENA", "Estonia", "Narva"));
        Mockito.when(flightsRepositoryMemory.getFlightById(String.valueOf(flightId))).thenReturn(expectedFlightFromDBInMemory);

        FlightResponse flightResponse = adminServiceMemory.getFlightById(String.valueOf(flightId));
        Mockito.verify(flightsRepositoryMemory).getFlightById(String.valueOf(flightId));

        Assertions.assertEquals("Latvia", flightResponse.getFrom().getCountry());
        Assertions.assertEquals("Estonia", flightResponse.getTo().getCountry());
        Assertions.assertEquals("2023-06-02 12:00", flightResponse.getDepartureTime());
        Assertions.assertEquals(flightId, flightResponse.getId());
    }

    @Test
    void saveFlight() {
        FlightRequest expectedFlightRequest = new FlightRequest(
                "AirBaltic", "2023-06-01-12-00", "2023-06-02-12-00", new Airport("Latvia", "Riga", "RIX"), new Airport("Estonia", "Narva", "EENA"));
        FlightResponse expectedFlightResponse = new FlightResponse(1,
                "AirBaltic", "2023-06-01 12:00", "2023-06-02 12:00", new Airport("Latvia", "Riga", "RIX"), new Airport("Estonia", "Narva", "EENA"));

        Mockito.when(flightsRepositoryMemory.saveFlight(expectedFlightRequest)).thenReturn(expectedFlightResponse);
        FlightResponse flightResponseActual = adminServiceMemory.saveFlight(expectedFlightRequest);

        Mockito.verify(flightsRepositoryMemory).saveFlight(flightRequestCapture.capture());
        FlightRequest flightRequestCaptured = flightRequestCapture.getValue();

        Assertions.assertEquals(expectedFlightRequest, flightRequestCaptured);
        Assertions.assertEquals(expectedFlightResponse, flightResponseActual);
    }

    @Test
    void deleteFlight() {
        String flightIdToDelete = "1";
        String expectedMessageAfterDelete = "Flight with id: " + flightIdToDelete + " removed from database.";

        Mockito.when(flightsRepositoryMemory.deleteFlight(flightIdToDelete)).thenReturn(expectedMessageAfterDelete);
        String receivedMessageAfterDeletion = adminServiceMemory.deleteFlight(flightIdToDelete);

        Mockito.verify(flightsRepositoryMemory).deleteFlight(deleteFlightIdCapture.capture());
        String capturedIdToDeleteFlight = deleteFlightIdCapture.getValue();

        Assertions.assertEquals(flightIdToDelete, capturedIdToDeleteFlight);
        Assertions.assertEquals(expectedMessageAfterDelete, receivedMessageAfterDeletion);
    }
}