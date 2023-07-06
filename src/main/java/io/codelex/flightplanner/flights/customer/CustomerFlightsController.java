package io.codelex.flightplanner.flights.customer;

import io.codelex.flightplanner.flights.admin.domain.Airport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/")
public class CustomerFlightsController {

    Logger logger = LoggerFactory.getLogger(CustomerFlightsController.class);

    private CustomerFlightsService customerFlightsService;

    public CustomerFlightsController(CustomerFlightsService customerFlightsService) {
        this.customerFlightsService = customerFlightsService;
    }


    @GetMapping("airports") // receives query parameter
    public List<Airport> searchAirport(@RequestParam String search){
        return customerFlightsService.searchAirport(search);
    }

    @GetMapping("flights/{flightId}") // receives path variable
    public String searchFlightById(@PathVariable String flightId){
        return "Searched airport with id "+ customerFlightsService.getFlightById(flightId);
    }

    @PostMapping("flights/search") // receives body/object
    public String searchFlights(@RequestBody String flights){
        return "Searched flights with body, temporarily with string "+ customerFlightsService.searchFlights(flights);
    }
}
