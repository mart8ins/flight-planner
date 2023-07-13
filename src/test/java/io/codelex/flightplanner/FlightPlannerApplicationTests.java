package io.codelex.flightplanner;

import io.codelex.flightplanner.flights.FlightsRepository;
import io.codelex.flightplanner.flights.admin.AdminFlightsController;
import io.codelex.flightplanner.flights.admin.AdminFlightsService;
import io.codelex.flightplanner.flights.admin.domain.Airport;
import io.codelex.flightplanner.flights.admin.domain.Flight;
import io.codelex.flightplanner.flights.admin.request.FlightRequest;
import io.codelex.flightplanner.flights.admin.response.FlightResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FlightPlannerApplicationTests {

    @Autowired
    FlightsRepository flightsRepository;

    @Autowired
    AdminFlightsService adminFlightsService;

    @Autowired
    AdminFlightsController adminFlightsController;

    @BeforeEach
    void clearRepository(){
        flightsRepository.clearDatabase();
    }

    @Test
    void AdminApiSaveReturnsSavedFlight() {
        Airport airport1 = new Airport("Latvia", "Riga", "RIX");
        Airport airport2 = new Airport("Latunia", "Oaua", "BIX");

        FlightRequest savedFlightRequest = new FlightRequest(airport1,airport2, "AirBaltic","2023-06-02 12:00","2023-06-04 12:00");
        FlightResponse expectedFlightResponse = new FlightResponse(airport1,airport2, "AirBaltic","2023-06-02 12:00","2023-06-04 12:00", 1);
        FlightResponse savedFlightResponse = adminFlightsController.saveFlight(savedFlightRequest);

        Assertions.assertEquals(expectedFlightResponse, savedFlightResponse);
    }

    @Test
    void AdminApiFindsFlightById() {
        Airport airport1 = new Airport("Latvia", "Riga", "RIX");
        Airport airport2 = new Airport("Latunia", "Oaua", "BIX");

        FlightRequest flightToSave = new FlightRequest(airport1,airport2, "AirBaltic","2023-06-02 12:00","2023-06-04 12:00");
        FlightResponse returnedFlightResponse = adminFlightsController.saveFlight(flightToSave);
        FlightResponse foundFlightByID = adminFlightsController.getFlightById(String.valueOf(returnedFlightResponse.getId()));

        Assertions.assertEquals(returnedFlightResponse, foundFlightByID);
    }

    @Test
    void AdminApiDeletesFlight() {
        Airport airport1 = new Airport("Latvia", "Riga", "RIX");
        Airport airport2 = new Airport("Latunia", "Oaua", "BIX");

        FlightRequest flightToSave = new FlightRequest(airport1,airport2, "AirBaltic","2023-06-02 12:00","2023-06-04 12:00");
        FlightResponse returnedFlightResponse = adminFlightsController.saveFlight(flightToSave);
        String deleteFlightResponse = adminFlightsController.deleteFlight(String.valueOf(returnedFlightResponse.getId()));

        String expectedStringResponse = "Flight with id: " + returnedFlightResponse.getId() + " removed from database.";

        Assertions.assertEquals(expectedStringResponse, deleteFlightResponse);
    }

}
