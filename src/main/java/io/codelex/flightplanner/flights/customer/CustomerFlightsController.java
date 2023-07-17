package io.codelex.flightplanner.flights.customer;

import io.codelex.flightplanner.flights.admin.domain.Airport;
import io.codelex.flightplanner.flights.admin.domain.Flight;
import io.codelex.flightplanner.flights.admin.response.FlightResponse;
import io.codelex.flightplanner.flights.customer.request.SearchFlightRequest;
import io.codelex.flightplanner.flights.customer.response.SearchedFlightsResponse;
import io.codelex.flightplanner.flights.customer.service.InMemoryCustomerFlightsService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/")
public class CustomerFlightsController {

    private InMemoryCustomerFlightsService inMemoryCustomerFlightsService;

    public CustomerFlightsController(InMemoryCustomerFlightsService inMemoryCustomerFlightsService) {
        this.inMemoryCustomerFlightsService = inMemoryCustomerFlightsService;
    }

    @GetMapping("airports")
    public List<Airport> searchAirport(@RequestParam String search){
        return inMemoryCustomerFlightsService.searchAirport(search);
    }

    @GetMapping("flights/{flightId}")
    public FlightResponse getFlightById(@PathVariable String flightId){
        return inMemoryCustomerFlightsService.getFlightById(flightId);
    }

    @PostMapping("flights/search")
    public @ResponseBody SearchedFlightsResponse<Flight> searchFlights(@Valid @RequestBody SearchFlightRequest flight){
        return inMemoryCustomerFlightsService.searchFlights(flight);
    }
}
