package io.codelex.flightplanner.flights.customer;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/")
public class CustomerFlightsController {

    private CustomerFlightsService customerFlightsService;

    public CustomerFlightsController(CustomerFlightsService customerFlightsService) {
        this.customerFlightsService = customerFlightsService;
    }


    @GetMapping("airports") // receives query parameter
    public List<String> searchAirport(@RequestParam String airport){
        return customerFlightsService.getAirports(airport);
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
