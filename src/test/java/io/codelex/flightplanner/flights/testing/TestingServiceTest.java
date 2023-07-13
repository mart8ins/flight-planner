package io.codelex.flightplanner.flights.testing;

import io.codelex.flightplanner.flights.FlightsRepository;
import io.codelex.flightplanner.flights.admin.domain.Airport;
import io.codelex.flightplanner.flights.admin.domain.Flight;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TestingServiceTest {

    @Mock
    FlightsRepository flightsRepository;

    @InjectMocks
    TestingService testingService;

    @Test
    void clearDatabase() {
        testingService.clearDatabase();
        Mockito.verify(flightsRepository).clearDatabase();
    }
}