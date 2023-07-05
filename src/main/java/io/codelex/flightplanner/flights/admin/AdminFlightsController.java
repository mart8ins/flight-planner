package io.codelex.flightplanner.flights.admin;

import io.codelex.flightplanner.flights.admin.request.AddFlightRequest;
import io.codelex.flightplanner.flights.admin.response.AddFlightResponse;
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
    public String getFlightById(@PathVariable String flightId){
        return adminFlightsService.getFlightById(flightId);
    }

    @PutMapping("flights")
    @ResponseStatus(HttpStatus.CREATED)
    public AddFlightResponse saveFlight(@Valid @RequestBody AddFlightRequest flight){
        return adminFlightsService.saveFlight(flight);
    }

    @DeleteMapping("flights/{flightId}")
    public String deleteFlight(@PathVariable String flightId){
        return adminFlightsService.deleteFlight(flightId);
    }
}
