package io.codelex.flightplanner.flights.admin;

import io.codelex.flightplanner.flights.admin.request.FlightRequest;
import io.codelex.flightplanner.flights.admin.response.FlightResponse;
import io.codelex.flightplanner.flights.admin.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin-api/")
public class AdminController {

    private AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("flights/{flightId}")
    public FlightResponse getFlightById(@PathVariable String flightId){
        return adminService.getFlightById(flightId);
    }

    @PutMapping("flights")
    @ResponseStatus(HttpStatus.CREATED)
    public FlightResponse saveFlight(@Valid @RequestBody FlightRequest flight){
        return adminService.saveFlight(flight);
    }

    @DeleteMapping("flights/{flightId}")
    public String deleteFlight(@PathVariable String flightId){
        return adminService.deleteFlight(flightId);
    }
}
