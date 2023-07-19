package io.codelex.flightplanner.flights.customer;

import io.codelex.flightplanner.flights.admin.domain.inMemory.AirportInMemory;
import io.codelex.flightplanner.flights.admin.domain.inMemory.FlightInMemory;
import io.codelex.flightplanner.flights.admin.response.FlightResponse;
import io.codelex.flightplanner.flights.customer.request.SearchFlightRequest;
import io.codelex.flightplanner.flights.customer.response.SearchedFlightsResponse;
import io.codelex.flightplanner.flights.customer.service.CustomerFlightsService;
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
    public List<AirportInMemory> searchAirport(@RequestParam String search){
        return customerFlightsService.searchAirport(search);
    }

    @GetMapping("flights/{flightId}")
    public FlightResponse getFlightById(@PathVariable String flightId){
        return customerFlightsService.getFlightById(flightId);
    }

    @PostMapping("flights/search")
    public @ResponseBody SearchedFlightsResponse<FlightInMemory> searchFlights(@Valid @RequestBody SearchFlightRequest flight){
        return customerFlightsService.searchFlights(flight);
    }
}
