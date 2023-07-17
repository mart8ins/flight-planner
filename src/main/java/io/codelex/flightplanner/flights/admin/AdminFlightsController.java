package io.codelex.flightplanner.flights.admin;

import io.codelex.flightplanner.flights.admin.request.FlightRequest;
import io.codelex.flightplanner.flights.admin.response.FlightResponse;
import io.codelex.flightplanner.flights.admin.service.InMemoryAdminFlightsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin-api/")
public class AdminFlightsController {

    private InMemoryAdminFlightsService inMemoryAdminFlightsService;

    public AdminFlightsController(InMemoryAdminFlightsService inMemoryAdminFlightsService) {
        this.inMemoryAdminFlightsService = inMemoryAdminFlightsService;
    }

    @GetMapping("flights/{flightId}")
    public FlightResponse getFlightById(@PathVariable String flightId){
        return inMemoryAdminFlightsService.getFlightById(flightId);
    }

    @PutMapping("flights")
    @ResponseStatus(HttpStatus.CREATED)
    public FlightResponse saveFlight(@Valid @RequestBody FlightRequest flight){
        return inMemoryAdminFlightsService.saveFlight(flight);
    }

    @DeleteMapping("flights/{flightId}")
    public String deleteFlight(@PathVariable String flightId){
        return inMemoryAdminFlightsService.deleteFlight(flightId);
    }
}
