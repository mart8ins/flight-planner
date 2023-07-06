package io.codelex.flightplanner.flights.customer;

import io.codelex.flightplanner.flights.admin.domain.Airport;
import io.codelex.flightplanner.flights.admin.response.AddFlightResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/")
public class CustomerFlightsController {

    private CustomerFlightsService customerFlightsService;

    public CustomerFlightsController(CustomerFlightsService customerFlightsService) {
        this.customerFlightsService = customerFlightsService;
    }


    @GetMapping("airports")
    public List<Airport> searchAirport(@RequestParam String search){
        return customerFlightsService.searchAirport(search);
    }

    @GetMapping("flights/{flightId}")
    public AddFlightResponse getFlightById(@PathVariable String flightId){
        return customerFlightsService.getFlightById(flightId);
    }

    @PostMapping("flights/search") // receives body/object
    public String searchFlights(@RequestBody String flights){
        return "Searched flights with body, temporarily with string "+ customerFlightsService.searchFlights(flights);
    }
}
