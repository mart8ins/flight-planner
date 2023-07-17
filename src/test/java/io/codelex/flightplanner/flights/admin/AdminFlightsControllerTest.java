package io.codelex.flightplanner.flights.admin;

import io.codelex.flightplanner.flights.admin.domain.Airport;
import io.codelex.flightplanner.flights.admin.request.FlightRequest;
import io.codelex.flightplanner.flights.admin.response.FlightResponse;
import io.codelex.flightplanner.flights.admin.service.InMemoryAdminFlightsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class AdminFlightsControllerTest {

    @Mock
    InMemoryAdminFlightsService inMemoryAdminFlightsService;

    @InjectMocks
    AdminFlightsController adminFlightsController;

    @Captor
    ArgumentCaptor<String> flightIdQueryCapture;

    @Captor
    ArgumentCaptor<String> deleteFlightIdCapture;

    @Captor
    ArgumentCaptor<FlightRequest> flightRequestCapture;



    @Test
    void getFlightById() {
        int flightId = 1;

        FlightResponse expectedFlightResponse = new FlightResponse(new Airport("Latvia", "Riga", "RIX"), new Airport("Estonia", "Narva", "EENA"),
                "AirBaltic", "2023-06-02 12:00", "2023-06-04 12:00", 1);

        Mockito.when(inMemoryAdminFlightsService.getFlightById(String.valueOf(flightId))).thenReturn(expectedFlightResponse);

        FlightResponse actualFlightResponse = adminFlightsController.getFlightById(String.valueOf(flightId));

        Mockito.verify(inMemoryAdminFlightsService).getFlightById(flightIdQueryCapture.capture());
        String capturedFlightIdQuery = flightIdQueryCapture.getValue();

        Assertions.assertEquals(String.valueOf(flightId), capturedFlightIdQuery);
        Assertions.assertEquals("Latvia", actualFlightResponse.getFrom().getCountry());
        Assertions.assertEquals("Estonia", actualFlightResponse.getTo().getCountry());
        Assertions.assertEquals("2023-06-02 12:00", actualFlightResponse.getDepartureTime());
        Assertions.assertEquals(flightId, actualFlightResponse.getId());
    }

    @Test
    void saveFlight() {
        FlightRequest expectedFlightRequest = new FlightRequest(new Airport("Latvia", "Riga", "RIX"), new Airport("Estonia", "Narva", "EENA"),
                "AirBaltic", "2023-06-01-12-00", "2023-06-02-12-00");
        FlightResponse expectedFlightResponse = new FlightResponse(new Airport("Latvia", "Riga", "RIX"), new Airport("Estonia", "Narva", "EENA"),
                "AirBaltic", "2023-06-01 12:00", "2023-06-02 12:00", 1);

        Mockito.when(inMemoryAdminFlightsService.saveFlight(expectedFlightRequest)).thenReturn(expectedFlightResponse);
        FlightResponse flightResponseActual = adminFlightsController.saveFlight(expectedFlightRequest);

        Mockito.verify(inMemoryAdminFlightsService).saveFlight(flightRequestCapture.capture());
        FlightRequest flightRequestCaptured = flightRequestCapture.getValue();

        Assertions.assertEquals(expectedFlightRequest, flightRequestCaptured);
        Assertions.assertEquals(expectedFlightResponse, flightResponseActual);
    }


    @Test
    void deleteFlight() {
        String flightIdToDelete = "1";
        String expectedMessageAfterDelete = "Flight with id: " + flightIdToDelete + " removed from database.";

        Mockito.when(inMemoryAdminFlightsService.deleteFlight(flightIdToDelete)).thenReturn(expectedMessageAfterDelete);
        String receivedMessageAfterDeletion = adminFlightsController.deleteFlight(flightIdToDelete);

        Mockito.verify(inMemoryAdminFlightsService).deleteFlight(deleteFlightIdCapture.capture());
        String capturedIdToDeleteFlight = deleteFlightIdCapture.getValue();

        Assertions.assertEquals(flightIdToDelete, capturedIdToDeleteFlight);
        Assertions.assertEquals(expectedMessageAfterDelete, receivedMessageAfterDeletion);
    }
}