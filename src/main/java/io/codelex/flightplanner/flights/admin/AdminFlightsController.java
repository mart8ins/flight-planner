package io.codelex.flightplanner.flights.admin;

import io.codelex.flightplanner.flights.admin.request.FlightRequest;
import io.codelex.flightplanner.flights.admin.response.FlightResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin-api/")
public class AdminFlightsController {

    private AdminFlightsService adminFlightsService;

    public AdminFlightsController(AdminFlightsService adminFlightsService) {
        this.adminFlightsService = adminFlightsService;
    }

    @GetMapping("flights/{flightId}")
    public FlightResponse getFlightById(@PathVariable String flightId){
        return adminFlightsService.getFlightById(flightId);
    }

    @PutMapping("flights")
    @ResponseStatus(HttpStatus.CREATED)
    public FlightResponse saveFlight(@Valid @RequestBody FlightRequest flight){
        return adminFlightsService.saveFlight(flight);
    }

    @DeleteMapping("flights/{flightId}")
    public String deleteFlight(@PathVariable String flightId){
        return adminFlightsService.deleteFlight(flightId);
    }
}
