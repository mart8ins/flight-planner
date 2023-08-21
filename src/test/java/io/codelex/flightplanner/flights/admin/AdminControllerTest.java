package io.codelex.flightplanner.flights.admin;

import io.codelex.flightplanner.flights.admin.domain.Airport;
import io.codelex.flightplanner.flights.admin.request.FlightRequest;
import io.codelex.flightplanner.flights.admin.response.FlightResponse;
import io.codelex.flightplanner.flights.admin.service.AdminServiceMemory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    AdminServiceMemory adminServiceMemory;

    @InjectMocks
    AdminController adminController;

    @Captor
    ArgumentCaptor<String> flightIdQueryCapture;

    @Captor
    ArgumentCaptor<String> deleteFlightIdCapture;

    @Captor
    ArgumentCaptor<FlightRequest> flightRequestCapture;



    @Test
    void getFlightById() {
        int flightId = 1;

        FlightResponse expectedFlightResponse = new FlightResponse(1,
                "AirBaltic", LocalDateTime.of(2023,6,2,12,0), LocalDateTime.of(2023,6,4,12,0), new Airport("RIX", "Latvia", "Riga" ), new Airport("EENA", "Estonia", "Narva"));

        Mockito.when(adminServiceMemory.getFlightById(String.valueOf(flightId))).thenReturn(expectedFlightResponse);

        FlightResponse actualFlightResponse = adminController.getFlightById(String.valueOf(flightId));

        Mockito.verify(adminServiceMemory).getFlightById(flightIdQueryCapture.capture());
        String capturedFlightIdQuery = flightIdQueryCapture.getValue();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        Assertions.assertEquals(String.valueOf(flightId), capturedFlightIdQuery);
        Assertions.assertEquals("Latvia", actualFlightResponse.getFrom().getCountry());
        Assertions.assertEquals("Estonia", actualFlightResponse.getTo().getCountry());
        Assertions.assertEquals("2023-06-02 12:00", actualFlightResponse.getDepartureTime().format(formatter));
        Assertions.assertEquals(flightId, actualFlightResponse.getId());
    }

    @Test
    void saveFlight() {
        FlightRequest expectedFlightRequest = new FlightRequest(
                "AirBaltic", LocalDateTime.of(2023,6,1,12,0), LocalDateTime.of(2023,6,2,12,0),new Airport("Latvia", "Riga", "RIX"), new Airport("Estonia", "Narva", "EENA"));
        FlightResponse expectedFlightResponse = new FlightResponse(1,
                "AirBaltic", LocalDateTime.of(2023,6,1,12,0), LocalDateTime.of(2023,6,2,12,0), new Airport("Latvia", "Riga", "RIX"), new Airport("Estonia", "Narva", "EENA"));

        Mockito.when(adminServiceMemory.saveFlight(expectedFlightRequest)).thenReturn(expectedFlightResponse);
        FlightResponse flightResponseActual = adminController.saveFlight(expectedFlightRequest);

        Mockito.verify(adminServiceMemory).saveFlight(flightRequestCapture.capture());
        FlightRequest flightRequestCaptured = flightRequestCapture.getValue();

        Assertions.assertEquals(expectedFlightRequest, flightRequestCaptured);
        Assertions.assertEquals(expectedFlightResponse, flightResponseActual);
    }


    @Test
    void deleteFlight() {
        String flightIdToDelete = "1";
        String expectedMessageAfterDelete = "Flight with id: " + flightIdToDelete + " removed from database.";

        Mockito.when(adminServiceMemory.deleteFlight(flightIdToDelete)).thenReturn(expectedMessageAfterDelete);
        String receivedMessageAfterDeletion = adminController.deleteFlight(flightIdToDelete);

        Mockito.verify(adminServiceMemory).deleteFlight(deleteFlightIdCapture.capture());
        String capturedIdToDeleteFlight = deleteFlightIdCapture.getValue();

        Assertions.assertEquals(flightIdToDelete, capturedIdToDeleteFlight);
        Assertions.assertEquals(expectedMessageAfterDelete, receivedMessageAfterDeletion);
    }
}