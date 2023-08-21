package io.codelex.flightplanner;

import io.codelex.flightplanner.flights.repository.memory.FlightsRepositoryMemory;
import io.codelex.flightplanner.flights.admin.AdminController;
import io.codelex.flightplanner.flights.admin.domain.Airport;
import io.codelex.flightplanner.flights.admin.domain.Flight;
import io.codelex.flightplanner.flights.admin.request.FlightRequest;
import io.codelex.flightplanner.flights.admin.response.FlightResponse;
import io.codelex.flightplanner.flights.customer.CustomerController;
import io.codelex.flightplanner.flights.customer.request.SearchFlightRequest;
import io.codelex.flightplanner.flights.customer.response.SearchedFlightsResponse;
import io.codelex.flightplanner.flights.testing.TestingController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class FlightPlannerApplicationTests {

    @Autowired
    FlightsRepositoryMemory flightsRepositoryMemory;

    @Autowired
    AdminController adminController;

    @Autowired
    CustomerController customerController;

    @Autowired
    TestingController testingController;

    @BeforeEach
    void clearRepository(){
        flightsRepositoryMemory.clearDatabase();
    }

    @Test
    void AdminApiSavedAndReturnsSavedFlight() {
        Airport airport1 = new Airport("RIX", "Latvia", "Riga");
        Airport airport2 = new Airport("BIX", "Latunia", "Oaua");

        FlightRequest savedFlightRequest = new FlightRequest("AirBaltic",LocalDateTime.of(2023,6,2,12,0),LocalDateTime.of(2023,6,4,12,0), airport1, airport2);
        FlightResponse expectedFlightResponse = new FlightResponse(1, "AirBaltic",LocalDateTime.of(2023,6,2,12,0),LocalDateTime.of(2023,6,4,12,0), airport1, airport2);
        FlightResponse savedFlightResponse = adminController.saveFlight(savedFlightRequest);

        Assertions.assertEquals(expectedFlightResponse, savedFlightResponse);
    }

    @Test
    void AdminApiFindsFlightById() {
        Airport airport1 = new Airport("RIX", "Latvia", "Riga");
        Airport airport2 = new Airport("BIX", "Latunia", "Oaua");

        FlightRequest flightToSave = new FlightRequest( "AirBaltic",LocalDateTime.of(2023,6,2,12,0),LocalDateTime.of(2023,6,4,12,0), airport1, airport2);
        FlightResponse returnedFlightResponse = adminController.saveFlight(flightToSave);
        FlightResponse foundFlightByID = adminController.getFlightById(String.valueOf(returnedFlightResponse.getId()));

        Assertions.assertEquals(returnedFlightResponse, foundFlightByID);
    }

    @Test
    void AdminApiDeletesFlight() {
        Airport airport1 = new Airport("RIX", "Latvia", "Riga");
        Airport airport2 = new Airport("BIX", "Latunia", "Oaua");

        FlightRequest flightToSave = new FlightRequest( "AirBaltic",LocalDateTime.of(2023,6,2,12,0),LocalDateTime.of(2023,6,4,12,0), airport1, airport2);

        FlightResponse returnedFlightResponse = adminController.saveFlight(flightToSave);
        String deleteFlightResponse = adminController.deleteFlight(String.valueOf(returnedFlightResponse.getId()));

        String expectedStringResponse = "Flight with id: " + returnedFlightResponse.getId() + " removed from database.";

        Assertions.assertEquals(expectedStringResponse, deleteFlightResponse);
    }

    @Test
    void CustomerApiSearchAirport() {
        Airport airport1 = new Airport("RIX", "Latvia", "Riga");
        Airport airport2 = new Airport("BIX", "Latunia", "Oaua");

        FlightRequest flightToSave1 = new FlightRequest( "AirBaltic",LocalDateTime.of(2023,6,2,12,0),LocalDateTime.of(2023,6,4,12,0), airport1, airport2);
        adminController.saveFlight(flightToSave1);

        List<Airport> foundAirportsCountryNamePart = customerController.searchAirport("LAT");
        List<Airport> foundAirportsAirport = customerController.searchAirport("iX");

        List<Airport> foundAirportsSpecificAirport = customerController.searchAirport("riga");

        Assertions.assertEquals(2, foundAirportsCountryNamePart.size());
        Assertions.assertEquals(2, foundAirportsAirport.size());
        Assertions.assertEquals(1, foundAirportsSpecificAirport.size());
    }

    @Test
    void CustomerApiFindFlightById(){
        Airport airport1 = new Airport("RIX", "Latvia", "Riga");
        Airport airport2 = new Airport("BIX", "Latunia", "Oaua");

        FlightRequest flightToSave = new FlightRequest( "AirBaltic",LocalDateTime.of(2023,6,2,12,0),LocalDateTime.of(2023,6,4,12,0), airport1, airport2);

        FlightResponse returnedFlightResponse = adminController.saveFlight(flightToSave);
        FlightResponse foundFlightByID = customerController.getFlightById(String.valueOf(returnedFlightResponse.getId()));

        Assertions.assertEquals(returnedFlightResponse, foundFlightByID);
    }

    @Test
    void CustomerApiSearchFlights(){
        SearchFlightRequest searchFlightRequest = new SearchFlightRequest("RIX", "BIX", LocalDate.of(2023,6,2));

        Airport airport1 = new Airport("RIX", "Latvia", "Riga");
        Airport airport2 = new Airport("BIX", "Latunia", "Oaua");
        Airport airport3 = new Airport("OSL", "Sweden", "Oslo");

        FlightRequest flightToSave1 = new FlightRequest( "AirBaltic", LocalDateTime.of(2023,6,2,12,0),LocalDateTime.of(2023,6,4,12,0), airport1, airport2);
        FlightRequest flightToSave2 = new FlightRequest("SwedenBaltic",LocalDateTime.of(2023,6,3,12,0),LocalDateTime.of(2023,6,5,12,0), airport3, airport2);

        FlightResponse flightResponse = adminController.saveFlight(flightToSave1);
        adminController.saveFlight(flightToSave2);

        SearchedFlightsResponse<Flight> searchedFlightsResponse = customerController.searchFlights(searchFlightRequest);
        Assertions.assertEquals(0,searchedFlightsResponse.getPage());
        Assertions.assertEquals(1,searchedFlightsResponse.getTotalItems());
        Assertions.assertEquals(flightResponse.getId(),searchedFlightsResponse.getItems().get(0).getId());
    }

    @Test
    void TestingApiClearsDatabase(){
        Airport airport1 = new Airport("RIX", "Latvia", "Riga");
        Airport airport2 = new Airport("BIX", "Latunia", "Oaua");

        FlightRequest savedFlightRequest = new FlightRequest("AirBaltic",LocalDateTime.of(2023,6,2,12,0),LocalDateTime.of(2023,6,4,12,0), airport1, airport2);
        FlightResponse savedFlightResponse = adminController.saveFlight(savedFlightRequest);

        testingController.clearDatabase();

        List<Airport> returnedAirportInMemory = customerController.searchAirport("LAT");

        Assertions.assertEquals(0, returnedAirportInMemory.size());
        Assertions.assertThrows(ResponseStatusException.class, ()-> adminController.getFlightById(String.valueOf(savedFlightResponse.getId())));
    }

}
