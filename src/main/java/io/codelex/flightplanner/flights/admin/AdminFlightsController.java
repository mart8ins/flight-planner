package io.codelex.flightplanner.flights.admin;

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

    @PostMapping("flights")
    public String saveFlight(@RequestBody String flightId){
        return adminFlightsService.saveFlight(flightId);
    }

    @DeleteMapping("flights/{flightId}")
    public String deleteFlight(@PathVariable String flightId){
        return adminFlightsService.deleteFlight(flightId);
    }
}
