package io.codelex.flightplanner;

import io.codelex.flightplanner.flights.repository.inMemory.FlightsRepositoryInMemory;
import io.codelex.flightplanner.flights.admin.AdminFlightsController;
import io.codelex.flightplanner.flights.admin.domain.Airport;
import io.codelex.flightplanner.flights.admin.domain.Flight;
import io.codelex.flightplanner.flights.admin.request.FlightRequest;
import io.codelex.flightplanner.flights.admin.response.FlightResponse;
import io.codelex.flightplanner.flights.customer.CustomerFlightsController;
import io.codelex.flightplanner.flights.customer.request.SearchFlightRequest;
import io.codelex.flightplanner.flights.customer.response.SearchedFlightsResponse;
import io.codelex.flightplanner.flights.testing.TestingController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@SpringBootTest
class FlightPlannerApplicationTests {

    @Autowired
    FlightsRepositoryInMemory flightsRepositoryInMemory;

    @Autowired
    AdminFlightsController adminFlightsController;

    @Autowired
    CustomerFlightsController customerFlightsController;

    @Autowired
    TestingController testingController;

    @BeforeEach
    void clearRepository(){
        flightsRepositoryInMemory.clearDatabase();
    }

    @Test
    void AdminApiSavedAndReturnsSavedFlight() {
        Airport airport1 = new Airport("Latvia", "Riga", "RIX");
        Airport airport2 = new Airport("Latunia", "Oaua", "BIX");

        FlightRequest savedFlightRequest = new FlightRequest(airport1, airport2, "AirBaltic","2023-06-02 12:00","2023-06-04 12:00");
        FlightResponse expectedFlightResponse = new FlightResponse(1, airport1, airport2, "AirBaltic","2023-06-02 12:00","2023-06-04 12:00");
        FlightResponse savedFlightResponse = adminFlightsController.saveFlight(savedFlightRequest);

        Assertions.assertEquals(expectedFlightResponse, savedFlightResponse);
    }

    @Test
    void AdminApiFindsFlightById() {
        Airport airport1 = new Airport("Latvia", "Riga", "RIX");
        Airport airport2 = new Airport("Latunia", "Oaua", "BIX");

        FlightRequest flightToSave = new FlightRequest(airport1, airport2, "AirBaltic","2023-06-02 12:00","2023-06-04 12:00");
        FlightResponse returnedFlightResponse = adminFlightsController.saveFlight(flightToSave);
        FlightResponse foundFlightByID = adminFlightsController.getFlightById(String.valueOf(returnedFlightResponse.getId()));

        Assertions.assertEquals(returnedFlightResponse, foundFlightByID);
    }

    @Test
    void AdminApiDeletesFlight() {
        Airport airport1 = new Airport("Latvia", "Riga", "RIX");
        Airport airport2 = new Airport("Latunia", "Oaua", "BIX");

        FlightRequest flightToSave = new FlightRequest(airport1, airport2, "AirBaltic","2023-06-02 12:00","2023-06-04 12:00");
        FlightResponse returnedFlightResponse = adminFlightsController.saveFlight(flightToSave);
        String deleteFlightResponse = adminFlightsController.deleteFlight(String.valueOf(returnedFlightResponse.getId()));

        String expectedStringResponse = "Flight with id: " + returnedFlightResponse.getId() + " removed from database.";

        Assertions.assertEquals(expectedStringResponse, deleteFlightResponse);
    }

    @Test
    void CustomerApiSearchAirport() {
        Airport airport1 = new Airport("Latvia", "Riga", "RIX");
        Airport airport2 = new Airport("Latunia", "Oaua", "BIX");

        FlightRequest flightToSave1 = new FlightRequest(airport1, airport2, "AirBaltic","2023-06-02 12:00","2023-06-04 12:00");
        adminFlightsController.saveFlight(flightToSave1);

        List<Airport> foundAirportsCountryNamePart = customerFlightsController.searchAirport("LAT");
        List<Airport> foundAirportsAirport = customerFlightsController.searchAirport("iX");

        List<Airport> foundAirportsSpecificAirport = customerFlightsController.searchAirport("riga");

        Assertions.assertEquals(2, foundAirportsCountryNamePart.size());
        Assertions.assertEquals(2, foundAirportsAirport.size());
        Assertions.assertEquals(1, foundAirportsSpecificAirport.size());
    }

    @Test
    void CustomerApiFindFlightById(){
        Airport airport1 = new Airport("Latvia", "Riga", "RIX");
        Airport airport2 = new Airport("Latunia", "Oaua", "BIX");

        FlightRequest flightToSave = new FlightRequest(airport1, airport2, "AirBaltic","2023-06-02 12:00","2023-06-04 12:00");
        FlightResponse returnedFlightResponse = adminFlightsController.saveFlight(flightToSave);
        FlightResponse foundFlightByID = customerFlightsController.getFlightById(String.valueOf(returnedFlightResponse.getId()));

        Assertions.assertEquals(returnedFlightResponse, foundFlightByID);
    }

    @Test
    void CustomerApiSearchFlights(){
        SearchFlightRequest searchFlightRequest = new SearchFlightRequest("RIX", "BIX", "2023-06-02");
        Airport airport1 = new Airport("Latvia", "Riga", "RIX");
        Airport airport2 = new Airport("Latunia", "Oaua", "BIX");
        Airport airport3 = new Airport("Sweden", "Oslo", "OSL");

        FlightRequest flightToSave1 = new FlightRequest(airport1, airport2, "AirBaltic","2023-06-02 12:00","2023-06-04 12:00");
        FlightRequest flightToSave2 = new FlightRequest(airport3, airport2, "SwedenBaltic","2023-06-03 12:00","2023-06-05 12:00");
        FlightResponse flightResponse = adminFlightsController.saveFlight(flightToSave1);
        adminFlightsController.saveFlight(flightToSave2);

        SearchedFlightsResponse<Flight> searchedFlightsResponse = customerFlightsController.searchFlights(searchFlightRequest);
        Assertions.assertEquals(0,searchedFlightsResponse.getPage());
        Assertions.assertEquals(1,searchedFlightsResponse.getTotalItems());
        Assertions.assertEquals(flightResponse.getId(),searchedFlightsResponse.getItems().get(0).getId());
    }

    @Test
    void TestingApiClearsDatabase(){
        Airport airport1 = new Airport("Latvia", "Riga", "RIX");
        Airport airport2 = new Airport("Latunia", "Oaua", "BIX");

        FlightRequest savedFlightRequest = new FlightRequest(airport1, airport2, "AirBaltic","2023-06-02 12:00","2023-06-04 12:00");
        FlightResponse savedFlightResponse = adminFlightsController.saveFlight(savedFlightRequest);

        testingController.clearDatabase();

        List<Airport> returnedAirportInMemories = customerFlightsController.searchAirport("LAT");

        Assertions.assertEquals(0, returnedAirportInMemories.size());
        Assertions.assertThrows(ResponseStatusException.class, ()-> adminFlightsController.getFlightById(String.valueOf(savedFlightResponse.getId())));
    }

}
