package io.codelex.flightplanner.flights.testing;

import io.codelex.flightplanner.flights.testing.service.InMemoryTestingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("testing-api/")
public class TestingController {

    private InMemoryTestingService inMemoryTestingService;

    public TestingController(InMemoryTestingService inMemoryTestingService){
        this.inMemoryTestingService = inMemoryTestingService;
    }

    @PostMapping("clear")
    public void clearDatabase() {
        inMemoryTestingService.clearDatabase();
    }
}
