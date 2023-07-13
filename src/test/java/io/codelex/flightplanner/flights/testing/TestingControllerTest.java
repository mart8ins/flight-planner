package io.codelex.flightplanner.flights.testing;

import io.codelex.flightplanner.flights.FlightsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class TestingControllerTest {

    @Mock
    TestingService testingService;

    @InjectMocks
    TestingController testingController;

    @Test
    void clearDatabase() {
        testingController.clearDatabase();
        Mockito.verify(testingService).clearDatabase();
    }
}