package io.codelex.flightplanner.flights.testing;

import io.codelex.flightplanner.flights.testing.service.TestingServiceInMemory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class TestingControllerTest {

    @Mock
    TestingServiceInMemory testingServiceInmemory;

    @InjectMocks
    TestingController testingController;

    @Test
    void clearDatabase() {
        testingController.clearDatabase();
        Mockito.verify(testingServiceInmemory).clearDatabase();
    }
}