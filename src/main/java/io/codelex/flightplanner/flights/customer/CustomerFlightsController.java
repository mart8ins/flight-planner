package io.codelex.flightplanner.flights.customer;

import io.codelex.flightplanner.flights.admin.domain.Airport;
import io.codelex.flightplanner.flights.admin.domain.Flight;
import io.codelex.flightplanner.flights.admin.response.AddFlightResponse;
import io.codelex.flightplanner.flights.customer.request.SearchFlightRequest;
import io.codelex.flightplanner.flights.customer.response.SearchedFlightsResponse;
import jakarta.validation.Valid;
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
    public @ResponseBody SearchedFlightsResponse<Flight> searchFlights(@Valid @RequestBody SearchFlightRequest flight){
        return customerFlightsService.searchFlights(flight);
    }
}
