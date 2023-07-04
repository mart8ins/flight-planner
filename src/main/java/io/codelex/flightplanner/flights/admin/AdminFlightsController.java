package io.codelex.flightplanner.flights.admin;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin-api/")
public class AdminFlightsController {

    @GetMapping("flights/{flightId}")
    public String getFlight(@PathVariable String flightId){
        return "Search of flight id " + flightId;
    }

    @PostMapping("flights")
    public String saveFlight(){
       return "Saved";
    }

    @DeleteMapping("flights/{flightId}")
    public String deleteFlight(@PathVariable String flightId){
        return "Deleted flight " + flightId;
    }
}
