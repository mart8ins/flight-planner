package io.codelex.flightplanner.flights.customer;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/")
public class CustomerFlightsController {

    @GetMapping("airports") // receives query parameter
    public String searchAirport(@RequestParam String airport){
        return "Airport search query "+ airport;
    }

    @GetMapping("flights/{flightId}") // receives path variable
    public String searchFlightById(@PathVariable String flightId){
        return "Searched airport with id "+ flightId;
    }

    @PostMapping("flights/search") // receives body/object
    public String searchFlights(@RequestBody String flights){
        return "Searched flights with body, temporarily with string "+ flights;
    }
}
