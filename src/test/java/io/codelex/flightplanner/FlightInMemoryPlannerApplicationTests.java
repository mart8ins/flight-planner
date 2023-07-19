package io.codelex.flightplanner;

import io.codelex.flightplanner.flights.repository.inMemory.FlightsRepositoryInMemory;
import io.codelex.flightplanner.flights.admin.AdminFlightsController;
import io.codelex.flightplanner.flights.admin.domain.inMemory.AirportInMemory;
import io.codelex.flightplanner.flights.admin.domain.inMemory.FlightInMemory;
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
class FlightInMemoryPlannerApplicationTests {

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
        AirportInMemory airportInMemory1 = new AirportInMemory("Latvia", "Riga", "RIX");
        AirportInMemory airportInMemory2 = new AirportInMemory("Latunia", "Oaua", "BIX");

        FlightRequest savedFlightRequest = new FlightRequest(airportInMemory1, airportInMemory2, "AirBaltic","2023-06-02 12:00","2023-06-04 12:00");
        FlightResponse expectedFlightResponse = new FlightResponse(1, airportInMemory1, airportInMemory2, "AirBaltic","2023-06-02 12:00","2023-06-04 12:00");
        FlightResponse savedFlightResponse = adminFlightsController.saveFlight(savedFlightRequest);

        Assertions.assertEquals(expectedFlightResponse, savedFlightResponse);
    }

    @Test
    void AdminApiFindsFlightById() {
        AirportInMemory airportInMemory1 = new AirportInMemory("Latvia", "Riga", "RIX");
        AirportInMemory airportInMemory2 = new AirportInMemory("Latunia", "Oaua", "BIX");

        FlightRequest flightToSave = new FlightRequest(airportInMemory1, airportInMemory2, "AirBaltic","2023-06-02 12:00","2023-06-04 12:00");
        FlightResponse returnedFlightResponse = adminFlightsController.saveFlight(flightToSave);
        FlightResponse foundFlightByID = adminFlightsController.getFlightById(String.valueOf(returnedFlightResponse.getId()));

        Assertions.assertEquals(returnedFlightResponse, foundFlightByID);
    }

    @Test
    void AdminApiDeletesFlight() {
        AirportInMemory airportInMemory1 = new AirportInMemory("Latvia", "Riga", "RIX");
        AirportInMemory airportInMemory2 = new AirportInMemory("Latunia", "Oaua", "BIX");

        FlightRequest flightToSave = new FlightRequest(airportInMemory1, airportInMemory2, "AirBaltic","2023-06-02 12:00","2023-06-04 12:00");
        FlightResponse returnedFlightResponse = adminFlightsController.saveFlight(flightToSave);
        String deleteFlightResponse = adminFlightsController.deleteFlight(String.valueOf(returnedFlightResponse.getId()));

        String expectedStringResponse = "Flight with id: " + returnedFlightResponse.getId() + " removed from database.";

        Assertions.assertEquals(expectedStringResponse, deleteFlightResponse);
    }

    @Test
    void CustomerApiSearchAirport() {
        AirportInMemory airportInMemory1 = new AirportInMemory("Latvia", "Riga", "RIX");
        AirportInMemory airportInMemory2 = new AirportInMemory("Latunia", "Oaua", "BIX");

        FlightRequest flightToSave1 = new FlightRequest(airportInMemory1, airportInMemory2, "AirBaltic","2023-06-02 12:00","2023-06-04 12:00");
        adminFlightsController.saveFlight(flightToSave1);

        List<AirportInMemory> foundAirportsCountryNamePart = customerFlightsController.searchAirport("LAT");
        List<AirportInMemory> foundAirportsAirportInMemory = customerFlightsController.searchAirport("iX");

        List<AirportInMemory> foundAirportsSpecificAirportInMemory = customerFlightsController.searchAirport("riga");

        Assertions.assertEquals(2, foundAirportsCountryNamePart.size());
        Assertions.assertEquals(2, foundAirportsAirportInMemory.size());
        Assertions.assertEquals(1, foundAirportsSpecificAirportInMemory.size());
    }

    @Test
    void CustomerApiFindFlightById(){
        AirportInMemory airportInMemory1 = new AirportInMemory("Latvia", "Riga", "RIX");
        AirportInMemory airportInMemory2 = new AirportInMemory("Latunia", "Oaua", "BIX");

        FlightRequest flightToSave = new FlightRequest(airportInMemory1, airportInMemory2, "AirBaltic","2023-06-02 12:00","2023-06-04 12:00");
        FlightResponse returnedFlightResponse = adminFlightsController.saveFlight(flightToSave);
        FlightResponse foundFlightByID = customerFlightsController.getFlightById(String.valueOf(returnedFlightResponse.getId()));

        Assertions.assertEquals(returnedFlightResponse, foundFlightByID);
    }

    @Test
    void CustomerApiSearchFlights(){
        SearchFlightRequest searchFlightRequest = new SearchFlightRequest("RIX", "BIX", "2023-06-02");
        AirportInMemory airportInMemory1 = new AirportInMemory("Latvia", "Riga", "RIX");
        AirportInMemory airportInMemory2 = new AirportInMemory("Latunia", "Oaua", "BIX");
        AirportInMemory airportInMemory3 = new AirportInMemory("Sweden", "Oslo", "OSL");

        FlightRequest flightToSave1 = new FlightRequest(airportInMemory1, airportInMemory2, "AirBaltic","2023-06-02 12:00","2023-06-04 12:00");
        FlightRequest flightToSave2 = new FlightRequest(airportInMemory3, airportInMemory2, "SwedenBaltic","2023-06-03 12:00","2023-06-05 12:00");
        FlightResponse flightResponse = adminFlightsController.saveFlight(flightToSave1);
        adminFlightsController.saveFlight(flightToSave2);

        SearchedFlightsResponse<FlightInMemory> searchedFlightsResponse = customerFlightsController.searchFlights(searchFlightRequest);
        Assertions.assertEquals(0,searchedFlightsResponse.getPage());
        Assertions.assertEquals(1,searchedFlightsResponse.getTotalItems());
        Assertions.assertEquals(flightResponse.getId(),searchedFlightsResponse.getItems().get(0).getId());
    }

    @Test
    void TestingApiClearsDatabase(){
        AirportInMemory airportInMemory1 = new AirportInMemory("Latvia", "Riga", "RIX");
        AirportInMemory airportInMemory2 = new AirportInMemory("Latunia", "Oaua", "BIX");

        FlightRequest savedFlightRequest = new FlightRequest(airportInMemory1, airportInMemory2, "AirBaltic","2023-06-02 12:00","2023-06-04 12:00");
        FlightResponse savedFlightResponse = adminFlightsController.saveFlight(savedFlightRequest);

        testingController.clearDatabase();

        List<AirportInMemory> returnedAirportInMemories = customerFlightsController.searchAirport("LAT");

        Assertions.assertEquals(0, returnedAirportInMemories.size());
        Assertions.assertThrows(ResponseStatusException.class, ()-> adminFlightsController.getFlightById(String.valueOf(savedFlightResponse.getId())));
    }

}
